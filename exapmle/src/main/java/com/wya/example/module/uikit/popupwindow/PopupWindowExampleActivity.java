package com.wya.example.module.uikit.popupwindow;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.example.module.hardware.scan.CustomCaptureActivity;
import com.wya.hardware.scan.Intents;
import com.wya.uikit.dialog.WYACustomDialog;
import com.wya.uikit.popupwindow.WYAPopupWindow;
import com.wya.uikit.toolbar.StatusBarUtil;
import com.wya.utils.utils.ScreenUtil;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;

public class PopupWindowExampleActivity extends BaseActivity {

    @BindView(R.id.tv_result)
    TextView tvResult;
    private WYAPopupWindow wyaPopupWindow;

    private String url;
    public static final int REQUEST_CODE_SCAN = 0X01;
    public final int REQUEST_CAMERA = 111;

    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);
        setToolBarTitle("PopupWindow");
        url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_notice, true);
        setPopupWindow();
    }

    private void setPopupWindow() {
        setRightImageAntherOnclickListener(view -> wyaPopupWindow.show(view, -100, 0));
        wyaPopupWindow = new WYAPopupWindow.Builder(PopupWindowExampleActivity.this).setLayoutRes(R.layout.popopwindow_custom_list, v -> {
            TableRow tabScan = v.findViewById(R.id.tab_scan);
            TableRow tabHelp = v.findViewById(R.id.tab_help);
            tabScan.setOnClickListener(v1 -> checkPermission());
            tabHelp.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    getWyaToast().showShort("链接地址复制成功");
                    StringUtil.copyString(PopupWindowExampleActivity.this, url);
                    return true;
                }
            });
            tabHelp.setOnClickListener(v12 -> startActivity(new Intent(PopupWindowExampleActivity.this, ReadmeActivity.class).putExtra("url", url)));
        }).build();
    }

    /**
     * 点击显示联系人按钮相应
     * <p>
     * 回调已经被定义好了
     */
    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 那么第二次申请的话，就要给用户说明为什么需要申请这个权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //不具有获取权限，需要进行权限申请
                requestCameraPermission();
            } else {
                startScan();
            }
        } else {
            startScan();
        }
    }

    private void startScan() {
        wyaPopupWindow.dismiss();
        Intent intent = new Intent(this, CustomCaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    /**
     * 申请相机权限
     */
    private void requestCameraPermission() {
        // 相机权限未被授予，需要申请！
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            // 如果访问了，但是没有被授予权限，则需要告诉用户，使用此权限的好处
            WYACustomDialog wyaCustomDialog = new WYACustomDialog.Builder(this)
                    .cancelable(true)
                    .cancelTouchout(true)
                    .title("提示")
                    .message("请获取相机权限进行扫码!")
                    .cancelShow(false)
                    .width(ScreenUtil.getScreenWidth(this) * 3 / 4)
                    .build();
            wyaCustomDialog.setNoOnclickListener(() -> {
                wyaCustomDialog.dismiss();
            });
            wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
            wyaCustomDialog.show();
        } else {
            // 第一次申请，就直接申请
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length >= 1) {
                //相机权限
                int cameraPermissionResult = grantResults[0];
                boolean cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (cameraPermissionGranted) {
                    startScan();
                } else {
                    // 如果访问了，但是没有被授予权限，则需要告诉用户，使用此权限的好处
                    WYACustomDialog wyaCustomDialog = new WYACustomDialog.Builder(this)
                            .cancelable(true)
                            .cancelTouchout(true)
                            .title("提示")
                            .message("请获取相机权限进行扫码!")
                            .cancelShow(false)
                            .width(ScreenUtil.getScreenWidth(this) * 3 / 4)
                            .build();
                    wyaCustomDialog.setNoOnclickListener(() -> {
                        wyaCustomDialog.dismiss();
                    });
                    wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
                    wyaCustomDialog.show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN:
                    String result = data.getStringExtra(Intents.Scan.RESULT);
                    tvResult.setText("解析结果：" + result);
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_popup_window_example;
    }

}
