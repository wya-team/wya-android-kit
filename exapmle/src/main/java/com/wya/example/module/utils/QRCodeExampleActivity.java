package com.wya.example.module.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.utils.utils.QRCodeUtil;

public class QRCodeExampleActivity extends BaseActivity implements View.OnClickListener {

	private ImageView imageview;
	private String content = "12345678901234567890";
	private String path;
	@Override
	protected int getLayoutID() {
		return R.layout.activity_qrcode_example;
	}

	@Override
	protected void initView() {
		findViewById(R.id.crate_qr_image).setOnClickListener(this);
		findViewById(R.id.crate_line_image).setOnClickListener(this);
		imageview = findViewById(R.id.imageview);
		path = getDiskCachePath(this)+ "/test.jpg";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.crate_qr_image:
				if (QRCodeUtil.createQRImage(content, 600, 600, null, path )) {
					Bitmap bitmap = BitmapFactory.decodeFile(path);
					imageview.setImageBitmap(bitmap);
				}
				break;
			case R.id.crate_line_image:
				Bitmap barcode = QRCodeUtil.createBarcode(content, 600, 300);
				imageview.setImageBitmap(barcode);
				break;
		}
	}

	/**
	 * 获取cache路径
	 *
	 * @param context
	 * @return
	 */
	public  String getDiskCachePath(Context context) {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			return context.getExternalCacheDir().getPath();
		} else {
			return context.getCacheDir().getPath();
		}
	}
}
