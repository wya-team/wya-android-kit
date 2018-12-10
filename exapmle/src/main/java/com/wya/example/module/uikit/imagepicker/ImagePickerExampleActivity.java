package com.wya.example.module.uikit.imagepicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wya.example.R;
import com.wya.uikit.imagecrop.Crop;
import com.wya.uikit.imagepicker.ImagePickerCreator;
import com.wya.uikit.imagepicker.LocalImage;
import com.wya.uikit.imagepicker.PickerConfig;
import com.wya.uikit.imagepicker.SpaceDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImagePickerExampleActivity extends AppCompatActivity {
	private RecyclerView mRecyclerView;
	private BaseQuickAdapter<LocalImage, BaseViewHolder> mAdapter;
	private List<LocalImage> mLocalImages = new ArrayList<>();
	private EditText input;
	private ImageView crop_image;

	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_picker_example);
		mRecyclerView = findViewById(R.id.recycler_view);
		input = findViewById(R.id.num_input);
		crop_image = findViewById(R.id.crop_image);

		findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ImagePickerCreator.create(ImagePickerExampleActivity.this)
						.maxImages(Integer.parseInt(TextUtils.isEmpty(input.getText().toString())
								? "1" : input.getText().toString()))
						.forResult(100);
			}
		});

		findViewById(R.id.crop).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ImagePickerCreator.create(ImagePickerExampleActivity.this)
						.maxImages(1)
						.forResult(1001);
			}
		});

		initRecycler();
	}


	private void initRecycler() {
		mAdapter = new BaseQuickAdapter<LocalImage, BaseViewHolder>(R.layout.picker_layout_item,
				mLocalImages) {
			@Override
			protected void convert(BaseViewHolder helper, LocalImage item) {
				ImageView imageView = helper.getView(R.id.image_test);
				Log.i("test", "convert: " + item.getPath());
				Glide.with(ImagePickerExampleActivity.this).load(item.getPath()).into(imageView);
			}
		};

		mRecyclerView.addItemDecoration(new SpaceDecoration(4, 3, false));
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
		mRecyclerView.setAdapter(mAdapter);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == RESULT_OK) {
			if (data != null && data.hasExtra(PickerConfig.IMAGE_SELECTED)) {
				Bundle extras = data.getExtras();
				mLocalImages.clear();
				mLocalImages.addAll((List<LocalImage>) extras.getSerializable
						(PickerConfig.IMAGE_SELECTED));
				Log.i("test", "onActivityResult: " + mLocalImages.size());
				mAdapter.notifyDataSetChanged();
			}
		}
		if (requestCode == 1001 && resultCode == RESULT_OK) {
			if (data != null && data.hasExtra(PickerConfig.IMAGE_SELECTED)) {
				Bundle extras = data.getExtras();
				List<LocalImage> list = (List<LocalImage>) extras.getSerializable(PickerConfig
						.IMAGE_SELECTED);
				String path = list.get(0).getPath();

				File file = new File(path);
				Uri uri;
				if (Build.VERSION.SDK_INT >= 24) {
					uri = FileProvider.getUriForFile(this, "com.wya.uikit.fileprovider", file);
				} else {
					uri = Uri.fromFile(file);
				}


				Crop.create(ImagePickerExampleActivity.this)
						.setImagePath(uri)
						.saveCropImagePath(file.getParentFile().getPath() + "/test.jpg")
						.CropQuality(80)
						.forResult(1002);
			}
		}
		if (requestCode == 1002 && resultCode == RESULT_OK) {
			String path = data.getStringExtra("path");
			File file = new File(path);
			Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
			crop_image.setImageBitmap(bitmap);
		}
		if (requestCode == 1002 && resultCode == RESULT_CANCELED) {
			Toast.makeText(this,"裁剪被取消了",Toast.LENGTH_SHORT).show();
		}

	}
}
