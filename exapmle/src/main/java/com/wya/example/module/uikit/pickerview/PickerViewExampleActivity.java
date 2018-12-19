package com.wya.example.module.uikit.pickerview;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.pickerview.CustomPickerView;
import com.wya.uikit.pickerview.CustomTimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PickerViewExampleActivity extends BaseActivity {

	private static final String TAG = "PickerViewExampleActivity";
	private Button picker_link, time_picker;
	private List<String> data1 = new ArrayList<>();
	private List<List<String>> data2 = new ArrayList<>();
	private List<List<List<String>>> data3 = new ArrayList<>();

	@Override
	protected int getLayoutID() {
		return R.layout.activity_picker_view_example;
	}


	@Override
	protected void initView() {
		for (int i = 0; i < 3; i++) {
			data1.add("data1=" + i);
			List<String> list = new ArrayList<>();
			List<List<String>> listList = new ArrayList<>();
			for (int j = 0; j < 3; j++) {
				list.add("data1=" + i + "data2=" + j);
				List<String> list1 = new ArrayList<>();
				for (int k = 0; k < 3; k++) {
					list1.add("data1=" + i + "data2=" + j + "data3=" + k);
				}
				listList.add(list1);
			}
			data2.add(list);
			data3.add(listList);
		}


		picker_link = (Button) findViewById(R.id.picker_link);
		time_picker = (Button) findViewById(R.id.time_picker);


		picker_link.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomPickerView customPickerView = new CustomPickerView(PickerViewExampleActivity
						.this, new CustomPickerView.OnChooseItemListener() {
					@Override
					public void itemSelected(int position1, int position2, int position3) {
						Toast.makeText(PickerViewExampleActivity.this,
								"position1" + position1 + "position2" + position2 + "position3" +
										"" + position3, Toast.LENGTH_SHORT).show();
					}
				});
				customPickerView.setData(data1, data2, data3);
				customPickerView.show();

			}
		});


		time_picker.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomTimePicker customTimePicker = new CustomTimePicker(PickerViewExampleActivity
						.this, new CustomTimePicker.OnTimePickerSelectedListener() {
					@Override
					public void selected(Date date) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String format = dateFormat.format(date);
						Toast.makeText(PickerViewExampleActivity.this, format, Toast.LENGTH_SHORT)
								.show();

					}
				});
				customTimePicker.setType(new boolean[]{true, true, true, false, false, false})
						.setSecondSpace(5)
						.show();
			}
		});


	}
}
