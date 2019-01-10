/*
 * Copyright (C) 2018 Jenly Yu
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
package com.wya.hardware.scan.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.wya.hardware.scan.DecodeFormatManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class CodeUtils {

    private CodeUtils() {
        throw new AssertionError();
    }


    /**
     * 解析二维码图片
     *
     * @param bitmapPath
     * @return
     */
    public static String parseQRCode(String bitmapPath) {
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        return parseQRCode(bitmapPath, hints);
    }

    /**
     * 解析二维码图片
     *
     * @param bitmapPath
     * @param hints
     * @return
     */
    public static String parseQRCode(String bitmapPath, Map<DecodeHintType, ?> hints) {
        try {
            Result result = new QRCodeReader().decode(getBinaryBitmap(compressBitmap(bitmapPath)), hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 解析一维码/二维码图片
     *
     * @param bitmapPath
     * @return
     */
    public static String parseCode(String bitmapPath) {
        Map<DecodeHintType, Object> hints = new HashMap<>();
        //添加可以解析的编码类型
        Vector<BarcodeFormat> decodeFormats = new Vector<>();
        decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.AZTEC_FORMATS);
        decodeFormats.addAll(DecodeFormatManager.PDF417_FORMATS);

        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
        return parseCode(bitmapPath, hints);
    }

    /**
     * 解析一维码/二维码图片
     *
     * @param bitmapPath
     * @param hints      解析编码类型
     * @return
     */
    public static String parseCode(String bitmapPath, Map<DecodeHintType, Object> hints) {
        try {
            MultiFormatReader reader = new MultiFormatReader();
            reader.setHints(hints);
            Result result = reader.decodeWithState(getBinaryBitmap(compressBitmap(bitmapPath)));
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 压缩图片
     *
     * @param path
     * @return
     */
    private static Bitmap compressBitmap(String path) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        //获取原始图片大小
        newOpts.inJustDecodeBounds = true;
        // 此时返回bm为空
        BitmapFactory.decodeFile(path, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float width = 800f;
        float height = 480f;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        // be=1表示不缩放
        int be = 1;
        if (w > h && w > width) {
            // 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / width);
        } else if (w < h && h > height) {
            // 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / height);
        }
        if (be <= 0) {
            be = 1;
        }
        // 设置缩放比例
        newOpts.inSampleSize = be;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, newOpts);
    }

    /**
     * 获取二进制图片
     *
     * @param bitmap
     * @return
     */
    private static BinaryBitmap getBinaryBitmap(@NonNull Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        //得到二进制图片
        return new BinaryBitmap(new HybridBinarizer(source));
    }

}
