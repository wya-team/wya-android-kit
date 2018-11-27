package com.wya.example.module.uikit.choicemenu;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.choicemenu.ChoiceMenu;
import com.wya.uikit.choicemenu.ChoiceMenuViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ChoiceMenuExampleActivity extends BaseActivity {


	private RelativeLayout mRelativeLayout;
	private Button showOne,showTwo;

	@Override
	protected int getLayoutID() {
		return R.layout.activity_choice_menu_example;
	}

	@Override
	protected void initView() {
		showOne = findViewById(R.id.show_one);
		showTwo = findViewById(R.id.show_two);
		List<String> data = new ArrayList<>();
		List<List<String>> data2 = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			data.add("first item" + i);
			List<String> list = new ArrayList<>();
			for (int j = 0; j < 10; j++) {
				list.add(i+"second item" + j);
			}
			data2.add(list);
		}

		mRelativeLayout = findViewById(R.id.title_bar_bg);
		ChoiceMenu<String> choiceMenu = new ChoiceMenu<String>(this,data,data2,R.layout
				.choice_menu_item,R.layout.choice_menu_item2) {


			@Override
			public void setValueFirst(ChoiceMenuViewHolder helper, String item) {
				helper.setText(R.id.menu_title, item);

			}

			@Override
			public void setValueSecond(ChoiceMenuViewHolder helper, String item) {
				helper.setText(R.id.menu_titles, item);
				helper.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(ChoiceMenuExampleActivity.this,item,Toast.LENGTH_SHORT).show();
					}
				});
			}
		};

		ChoiceMenu<String>choiceMenu1=new ChoiceMenu<String>(this,data,R.layout.choice_menu_item) {
			@Override
			public void setValueFirst(ChoiceMenuViewHolder helper, String item) {
				helper.setText(R.id.menu_title, item);
			}

			@Override
			public void setValueSecond(ChoiceMenuViewHolder helper, String item) {

			}
		};

		//设置高度
		choiceMenu.setHeight(getResources().getDisplayMetrics().heightPixels/2);
		//设置分割线
		choiceMenu.addLine(R.color.red,2);
		choiceMenu.addSecondLine(R.color.blue,2);

		showOne.setOnClickListener(v -> choiceMenu1.showAsDropDown(mRelativeLayout));
		showTwo.setOnClickListener(v -> choiceMenu.showAsDropDown(mRelativeLayout));

	}
}
