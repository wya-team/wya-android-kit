package com.wya.hardware.scan;

/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.wya.hardware.R;
import com.wya.hardware.scan.camera.CameraManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This activity opens the camera and does the actual scanning on a background thread. It draws a
 * viewfinder to help the user place the barcode correctly, shows feedback as the image processing
 * is happening, and then overlays the results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public class CaptureActivity extends Activity implements SurfaceHolder.Callback {

    public static final String KEY_RESULT = Intents.Scan.RESULT;

    private static final String TAG = CaptureActivity.class.getSimpleName();

//    private static final long DEFAULT_INTENT_RESULT_DURATION_MS = 1500L;
//    private static final long BULK_MODE_SCAN_DELAY_MS = 1000L;

    private static final int DEVIATION = 6;

    private static final String[] ZXING_URLS = { "http://zxing.appspot.com/scan", "zxing://scan/" };

//    private static final Collection<ResultMetadataType> DISPLAYABLE_METADATA_TYPES =
//            EnumSet.of(ResultMetadataType.ISSUE_NUMBER,
//                    ResultMetadataType.SUGGESTED_PRICE,
//                    ResultMetadataType.ERROR_CORRECTION_LEVEL,
//                    ResultMetadataType.POSSIBLE_COUNTRY);

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private Result savedResultToShow;
    private ViewfinderView viewfinderView;
    private Result lastResult;
    private boolean hasSurface;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType,?> decodeHints;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private AmbientLightManager ambientLightManager;

    /**
     * 是否支持缩放（变焦），默认支持
     */
    private boolean isZoom = true;
    private float oldDistance;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public BeepManager getBeepManager() {
        return beepManager;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(getLayoutId());

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        ambientLightManager = new AmbientLightManager(this);

//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public int getLayoutId(){
        return R.layout.capture;
    }

    public int getViewFinderViewId(){
        return R.id.viewfinder_view;
    }

    public int getPreviewViewId(){
        return R.id.preview_view;
    }

    /**
     * 扫码后声音与震动管理里入口
     * @return true表示播放和震动，是否播放与震动还得看{@link BeepManager}
     */
    public boolean isBeepSoundAndVibrate(){
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        cameraManager = new CameraManager(getApplication());
        viewfinderView = findViewById(getViewFinderViewId());
        viewfinderView.setCameraManager(cameraManager);

//        resultView = findViewById(R.id.result_view);
//        statusView =  findViewById(R.id.status_view);

        handler = null;
        lastResult = null;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        resetStatusView();


        beepManager.updatePrefs();
        ambientLightManager.start(cameraManager);

        inactivityTimer.onResume();

        Intent intent = getIntent();

        decodeFormats = null;
        characterSet = null;

        if (intent != null) {

            String action = intent.getAction();
            String dataString = intent.getDataString();

            if (Intents.Scan.ACTION.equals(action)) {

                // Scan the formats the intent requested, and return the result to the calling activity.
//                source = IntentSource.NATIVE_APP_INTENT;
                decodeFormats = DecodeFormatManager.parseDecodeFormats(intent);
                decodeHints = DecodeHintManager.parseDecodeHints(intent);

                if (intent.hasExtra(Intents.Scan.WIDTH) && intent.hasExtra(Intents.Scan.HEIGHT)) {
                    int width = intent.getIntExtra(Intents.Scan.WIDTH, 0);
                    int height = intent.getIntExtra(Intents.Scan.HEIGHT, 0);
                    if (width > 0 && height > 0) {
                        cameraManager.setManualFramingRect(width, height);
                    }
                }

                if (intent.hasExtra(Intents.Scan.CAMERA_ID)) {
                    int cameraId = intent.getIntExtra(Intents.Scan.CAMERA_ID, -1);
                    if (cameraId >= 0) {
                        cameraManager.setManualCameraId(cameraId);
                    }
                }

//                String customPromptMessage = intent.getStringExtra(Intents.Scan.PROMPT_MESSAGE);
//                if (customPromptMessage != null) {
//                    statusView.setText(customPromptMessage);
//                }

            } else if (dataString != null &&
                    dataString.contains("http://www.google") &&
                    dataString.contains("/m/products/scan")) {

                decodeFormats = DecodeFormatManager.PRODUCT_FORMATS;

            } else if (isZXingURL(dataString)) {

                // Scan formats requested in query string (all formats if none specified).
                // If a return URL is specified, send the results there. Otherwise, handle it ourselves.
//                source = IntentSource.ZXING_LINK;
//                sourceUrl = dataString;
                Uri inputUri = Uri.parse(dataString);
//                scanFromWebPageManager = new ScanFromWebPageManager(inputUri);
                decodeFormats = DecodeFormatManager.parseDecodeFormats(inputUri);
                // Allow a sub-set of the hints to be specified by the caller.
                decodeHints = DecodeHintManager.parseDecodeHints(inputUri);

            }

            characterSet = intent.getStringExtra(Intents.Scan.CHARACTER_SET);

        }

        SurfaceView surfaceView = findViewById(getPreviewViewId());
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the camera.
            surfaceHolder.addCallback(this);
        }
    }

    private static boolean isZXingURL(String dataString) {
        if (dataString == null) {
            return false;
        }
        for (String url : ZXING_URLS) {
            if (dataString.startsWith(url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        ambientLightManager.stop();
        beepManager.close();
        cameraManager.closeDriver();
        //historyManager = null; // Keep for onActivityResult
        if (!hasSurface) {
            SurfaceView surfaceView = findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
//                if (source == IntentSource.NATIVE_APP_INTENT) {
//                    setResult(RESULT_CANCELED);
//                    finish();
//                    return true;
//                }
//                if ((source == IntentSource.NONE || source == IntentSource.ZXING_LINK) && lastResult != null) {
//                    restartPreviewAfterDelay(0L);
//                    return true;
//                }
                break;
            case KeyEvent.KEYCODE_FOCUS:
            case KeyEvent.KEYCODE_CAMERA:
                // Handle these events so they don't launch the Camera app
                return true;
            // Use volume up/down to turn on light
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                cameraManager.setTorch(false);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                cameraManager.setTorch(true);
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
        // Bitmap isn't used yet -- will be used soon
        if (handler == null) {
            savedResultToShow = result;
        } else {
            if (result != null) {
                savedResultToShow = result;
            }
            if (savedResultToShow != null) {
                Message message = Message.obtain(handler, R.id.decode_succeeded, savedResultToShow);
                handler.sendMessage(message);
            }
            savedResultToShow = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // do nothing
    }

    /**
     * A valid barcode has been found, so give an indication of success and show the results.
     *
     * @param rawResult The contents of the barcode.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param barcode   A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();
        lastResult = rawResult;
        if(isBeepSoundAndVibrate()){
            beepManager.playBeepSoundAndVibrate();
        }
        String resultString = rawResult.getText();
        Intent intent = new Intent();
        intent.putExtra(KEY_RESULT,resultString);
        setResult(RESULT_OK,intent);
        finish();


//        ResultHandler resultHandler = ResultHandlerFactory.makeResultHandler(this, rawResult);

//        boolean fromLiveScan = barcode != null;
//        if (fromLiveScan) {
////            historyManager.addHistoryItem(rawResult, resultHandler);
//            // Then not from history, so beep/vibrate and we have an image to draw on
//            beepManager.playBeepSoundAndVibrate();
//            drawResultPoints(barcode, scaleFactor, rawResult);
//        }



//        switch (source) {
//            case NATIVE_APP_INTENT:
//            case PRODUCT_SEARCH_LINK:
//                handleDecodeExternally(rawResult, resultHandler, barcode);
//                break;
//            case ZXING_LINK:
//                if (scanFromWebPageManager == null || !scanFromWebPageManager.isScanFromWebPage()) {
//                    handleDecodeInternally(rawResult, resultHandler, barcode);
//                } else {
//                    handleDecodeExternally(rawResult, resultHandler, barcode);
//                }
//                break;
//            case NONE:
//                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//                if (fromLiveScan && prefs.getBoolean(PreferencesActivity.KEY_BULK_MODE, false)) {
//                    Toast.makeText(getApplicationContext(),
//                            getResources().getString(R.string.msg_bulk_mode_scanned) + " (" + rawResult.getText() + ')',
//                            Toast.LENGTH_SHORT).show();
//                    maybeSetClipboard(resultHandler);
//                    // Wait a moment or else it will scan the same barcode continuously about 3 times
//                    restartPreviewAfterDelay(BULK_MODE_SCAN_DELAY_MS);
//                } else {
//                    handleDecodeInternally(rawResult, resultHandler, barcode);
//                }
//                break;
//        }
    }

//    /**
//     * Superimpose a line for 1D or dots for 2D to highlight the key features of the barcode.
//     *
//     * @param barcode   A bitmap of the captured image.
//     * @param scaleFactor amount by which thumbnail was scaled
//     * @param rawResult The decoded results which contains the points to draw.
//     */
//    private void drawResultPoints(Bitmap barcode, float scaleFactor, Result rawResult) {
//        ResultPoint[] points = rawResult.getResultPoints();
//        if (points != null && points.length > 0) {
//            Canvas canvas = new Canvas(barcode);
//            Paint paint = new Paint();
//            paint.setColor(getResources().getColor(R.color.result_points));
//            if (points.length == 2) {
//                paint.setStrokeWidth(4.0f);
//                drawLine(canvas, paint, points[0], points[1], scaleFactor);
//            } else if (points.length == 4 &&
//                    (rawResult.getBarcodeFormat() == BarcodeFormat.UPC_A ||
//                            rawResult.getBarcodeFormat() == BarcodeFormat.EAN_13)) {
//                // Hacky special case -- draw two lines, for the barcode and metadata
//                drawLine(canvas, paint, points[0], points[1], scaleFactor);
//                drawLine(canvas, paint, points[2], points[3], scaleFactor);
//            } else {
//                paint.setStrokeWidth(10.0f);
//                for (ResultPoint point : points) {
//                    if (point != null) {
//                        canvas.drawPoint(scaleFactor * point.getX(), scaleFactor * point.getY(), paint);
//                    }
//                }
//            }
//        }
//    }
//
//    private static void drawLine(Canvas canvas, Paint paint, ResultPoint a, ResultPoint b, float scaleFactor) {
//        if (a != null && b != null) {
//            canvas.drawLine(scaleFactor * a.getX(),
//                    scaleFactor * a.getY(),
//                    scaleFactor * b.getX(),
//                    scaleFactor * b.getY(),
//                    paint);
//        }
//    }
//
//    // Put up our own UI for how to handle the decoded contents.
//    private void handleDecodeInternally(Result rawResult, ResultHandler resultHandler, Bitmap barcode) {
//
//        maybeSetClipboard(resultHandler);
//
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//
//        if (resultHandler.getDefaultButtonID() != null && prefs.getBoolean(PreferencesActivity.KEY_AUTO_OPEN_WEB, false)) {
//            resultHandler.handleButtonPress(resultHandler.getDefaultButtonID());
//            return;
//        }
//
//        statusView.setVisibility(View.GONE);
//        viewfinderView.setVisibility(View.GONE);
//        resultView.setVisibility(View.VISIBLE);
//
//        ImageView barcodeImageView = (ImageView) findViewById(R.id.barcode_image_view);
//        if (barcode == null) {
//            barcodeImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),
//                    R.drawable.launcher_icon));
//        } else {
//            barcodeImageView.setImageBitmap(barcode);
//        }
//
//        TextView formatTextView = (TextView) findViewById(R.id.format_text_view);
//        formatTextView.setText(rawResult.getBarcodeFormat().toString());
//
//        TextView typeTextView = (TextView) findViewById(R.id.type_text_view);
//        typeTextView.setText(resultHandler.getType().toString());
//
//        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
//        TextView timeTextView = (TextView) findViewById(R.id.time_text_view);
//        timeTextView.setText(formatter.format(rawResult.getTimestamp()));
//
//
//        TextView metaTextView = (TextView) findViewById(R.id.meta_text_view);
//        View metaTextViewLabel = findViewById(R.id.meta_text_view_label);
//        metaTextView.setVisibility(View.GONE);
//        metaTextViewLabel.setVisibility(View.GONE);
//        Map<ResultMetadataType,Object> metadata = rawResult.getResultMetadata();
//        if (metadata != null) {
//            StringBuilder metadataText = new StringBuilder(20);
//            for (Map.Entry<ResultMetadataType,Object> entry : metadata.entrySet()) {
//                if (DISPLAYABLE_METADATA_TYPES.contains(entry.getKey())) {
//                    metadataText.append(entry.getValue()).append('\n');
//                }
//            }
//            if (metadataText.length() > 0) {
//                metadataText.setLength(metadataText.length() - 1);
//                metaTextView.setText(metadataText);
//                metaTextView.setVisibility(View.VISIBLE);
//                metaTextViewLabel.setVisibility(View.VISIBLE);
//            }
//        }
//
//        CharSequence displayContents = resultHandler.getDisplayContents();
//        TextView contentsTextView = (TextView) findViewById(R.id.contents_text_view);
//        contentsTextView.setText(displayContents);
//        int scaledSize = Math.max(22, 32 - displayContents.length() / 4);
//        contentsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, scaledSize);
//
//        TextView supplementTextView = (TextView) findViewById(R.id.contents_supplement_text_view);
//        supplementTextView.setText("");
//        supplementTextView.setOnClickListener(null);
//        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
//                PreferencesActivity.KEY_SUPPLEMENTAL, true)) {
//            SupplementalInfoRetriever.maybeInvokeRetrieval(supplementTextView,
//                    resultHandler.getResult(),
//                    historyManager,
//                    this);
//        }
//
//        int buttonCount = resultHandler.getButtonCount();
//        ViewGroup buttonView = (ViewGroup) findViewById(R.id.result_button_view);
//        buttonView.requestFocus();
//        for (int x = 0; x < ResultHandler.MAX_BUTTON_COUNT; x++) {
//            TextView button = (TextView) buttonView.getChildAt(x);
//            if (x < buttonCount) {
//                button.setVisibility(View.VISIBLE);
//                button.setText(resultHandler.getButtonText(x));
//                button.setOnClickListener(new ResultButtonListener(resultHandler, x));
//            } else {
//                button.setVisibility(View.GONE);
//            }
//        }
//
//    }
//
//    // Briefly show the contents of the barcode, then handle the result outside Barcode Scanner.
//    private void handleDecodeExternally(Result rawResult, ResultHandler resultHandler, Bitmap barcode) {
//
//        if (barcode != null) {
//            viewfinderView.drawResultBitmap(barcode);
//        }
//
//        long resultDurationMS;
//        if (getIntent() == null) {
//            resultDurationMS = DEFAULT_INTENT_RESULT_DURATION_MS;
//        } else {
//            resultDurationMS = getIntent().getLongExtra(Intents.Scan.RESULT_DISPLAY_DURATION_MS,
//                    DEFAULT_INTENT_RESULT_DURATION_MS);
//        }
//
//        if (resultDurationMS > 0) {
//            String rawResultString = String.valueOf(rawResult);
//            if (rawResultString.length() > 32) {
//                rawResultString = rawResultString.substring(0, 32) + " ...";
//            }
//            statusView.setText(getString(resultHandler.getDisplayTitle()) + " : " + rawResultString);
//        }
//
//        maybeSetClipboard(resultHandler);
//
//        switch (source) {
//            case NATIVE_APP_INTENT:
//                // Hand back whatever action they requested - this can be changed to Intents.Scan.ACTION when
//                // the deprecated intent is retired.
//                Intent intent = new Intent(getIntent().getAction());
//                intent.addFlags(Intents.FLAG_NEW_DOC);
//                intent.putExtra(Intents.Scan.RESULT, rawResult.toString());
//                intent.putExtra(Intents.Scan.RESULT_FORMAT, rawResult.getBarcodeFormat().toString());
//                byte[] rawBytes = rawResult.getRawBytes();
//                if (rawBytes != null && rawBytes.length > 0) {
//                    intent.putExtra(Intents.Scan.RESULT_BYTES, rawBytes);
//                }
//                Map<ResultMetadataType, ?> metadata = rawResult.getResultMetadata();
//                if (metadata != null) {
//                    if (metadata.containsKey(ResultMetadataType.UPC_EAN_EXTENSION)) {
//                        intent.putExtra(Intents.Scan.RESULT_UPC_EAN_EXTENSION,
//                                metadata.get(ResultMetadataType.UPC_EAN_EXTENSION).toString());
//                    }
//                    Number orientation = (Number) metadata.get(ResultMetadataType.ORIENTATION);
//                    if (orientation != null) {
//                        intent.putExtra(Intents.Scan.RESULT_ORIENTATION, orientation.intValue());
//                    }
//                    String ecLevel = (String) metadata.get(ResultMetadataType.ERROR_CORRECTION_LEVEL);
//                    if (ecLevel != null) {
//                        intent.putExtra(Intents.Scan.RESULT_ERROR_CORRECTION_LEVEL, ecLevel);
//                    }
//                    @SuppressWarnings("unchecked")
//                    Iterable<byte[]> byteSegments = (Iterable<byte[]>) metadata.get(ResultMetadataType.BYTE_SEGMENTS);
//                    if (byteSegments != null) {
//                        int i = 0;
//                        for (byte[] byteSegment : byteSegments) {
//                            intent.putExtra(Intents.Scan.RESULT_BYTE_SEGMENTS_PREFIX + i, byteSegment);
//                            i++;
//                        }
//                    }
//                }
//                sendReplyMessage(R.id.return_scan_result, intent, resultDurationMS);
//                break;
//
//            case PRODUCT_SEARCH_LINK:
//                // Reformulate the URL which triggered us into a query, so that the request goes to the same
//                // TLD as the scan URL.
//                int end = sourceUrl.lastIndexOf("/scan");
//                String productReplyURL = sourceUrl.substring(0, end) + "?q=" +
//                        resultHandler.getDisplayContents() + "&source=zxing";
//                sendReplyMessage(R.id.launch_product_query, productReplyURL, resultDurationMS);
//                break;
//
//            case ZXING_LINK:
//                if (scanFromWebPageManager != null && scanFromWebPageManager.isScanFromWebPage()) {
//                    String linkReplyURL = scanFromWebPageManager.buildReplyURL(rawResult, resultHandler);
//                    scanFromWebPageManager = null;
//                    sendReplyMessage(R.id.launch_product_query, linkReplyURL, resultDurationMS);
//                }
//                break;
//        }
//    }
//
//    private void maybeSetClipboard(ResultHandler resultHandler) {
//        if (copyToClipboard && !resultHandler.areContentsSecure()) {
//            ClipboardInterface.setText(resultHandler.getDisplayContents(), this);
//        }
//    }
//
//    private void sendReplyMessage(int id, Object arg, long delayMS) {
//        if (handler != null) {
//            Message message = Message.obtain(handler, id, arg);
//            if (delayMS > 0L) {
//                handler.sendMessageDelayed(message, delayMS);
//            } else {
//                handler.sendMessage(message);
//            }
//        }
//    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
            decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
//            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
//            displayFrameworkBugMessageAndExit();
        }
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
        resetStatusView();
    }

    private void resetStatusView() {
        viewfinderView.setVisibility(View.VISIBLE);
        lastResult = null;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isZoom && cameraManager.isOpen()){
            Camera camera = cameraManager.getOpenCamera().getCamera();
            if(camera ==null){
                return super.onTouchEvent(event);
            }
            if (event.getPointerCount() == 1) {//单点触控，聚焦
//                focusOnTouch(event,camera);
            } else {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {//多点触控
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDistance = calcFingerSpacing(event);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float newDistance = calcFingerSpacing(event);

                        if (newDistance > oldDistance + DEVIATION) {//
                            handleZoom(true, camera);
                        } else if (newDistance < oldDistance - DEVIATION) {
                            handleZoom(false, camera);
                        }
                        oldDistance = newDistance;
                        break;
                    default:
                        break;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public boolean isZoom() {
        return isZoom;
    }

    public void setZoom(boolean zoom) {
        isZoom = zoom;
    }

    /**
     * 处理变焦缩放
     * @param isZoomIn
     * @param camera
     */
    private void handleZoom(boolean isZoomIn, Camera camera) {
        Camera.Parameters params = camera.getParameters();
        if (params.isZoomSupported()) {
            int maxZoom = params.getMaxZoom();
            int zoom = params.getZoom();
            if (isZoomIn && zoom < maxZoom) {
                zoom++;
            } else if (zoom > 0) {
                zoom--;
            }
            params.setZoom(zoom);
            camera.setParameters(params);
        } else {
            Log.i(TAG, "zoom not supported");
        }
    }

    /**
     * 聚焦
     * @param event
     * @param camera
     */
    public void focusOnTouch(MotionEvent event, Camera camera) {

        Camera.Parameters params = camera.getParameters();
        Camera.Size previewSize = params.getPreviewSize();

        Rect focusRect = calcTapArea(event.getRawX(), event.getRawY(), 1f,previewSize);
        Rect meteringRect = calcTapArea(event.getRawX(), event.getRawY(), 1.5f,previewSize);
        Camera.Parameters parameters = camera.getParameters();
        if (parameters.getMaxNumFocusAreas() > 0) {
            List<Camera.Area> focusAreas = new ArrayList<>();
            focusAreas.add(new Camera.Area(focusRect, 600));
            parameters.setFocusAreas(focusAreas);
        }

        if (parameters.getMaxNumMeteringAreas() > 0) {
            List<Camera.Area> meteringAreas = new ArrayList<>();
            meteringAreas.add(new Camera.Area(meteringRect, 600));
            parameters.setMeteringAreas(meteringAreas);
        }
        final String currentFocusMode = params.getFocusMode();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
        camera.setParameters(params);

        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                Camera.Parameters params = camera.getParameters();
                params.setFocusMode(currentFocusMode);
                camera.setParameters(params);
            }
        });

    }

    /**
     * 计算两指间距离
     * @param event
     * @return
     */
    private float calcFingerSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 计算对焦区域
     * @param x
     * @param y
     * @param coefficient
     * @param previewSize
     * @return
     */
    private Rect calcTapArea(float x, float y, float coefficient, Camera.Size previewSize) {
        float focusAreaSize = 200;
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();
        int centerX = (int) ((x / previewSize.width) * 2000 - 1000);
        int centerY = (int) ((y / previewSize.height) * 2000 - 1000);
        int left = clamp(centerX - (areaSize / 2), -1000, 1000);
        int top = clamp(centerY - (areaSize / 2), -1000, 1000);
        RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);
        return new Rect(Math.round(rectF.left), Math.round(rectF.top),
                Math.round(rectF.right), Math.round(rectF.bottom));
    }

    /**
     *
     * @param x
     * @param min
     * @param max
     * @return
     */
    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }
}