package com.wya.example.module.uikit.pickerview;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.pickerview.CustomPickerView;
import com.wya.uikit.pickerview.CustomTimePicker;
import com.wya.utils.utils.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickerViewExampleActivity extends BaseActivity {

    private static final String TAG = "PickerViewExampleActivity";
    @BindView(R.id.ymdhms_text)
    TextView mYmdhmsText;
    @BindView(R.id.ymd_text_with)
    TextView mYmdhmsTextWith;
    @BindView(R.id.ymd_text)
    TextView mYmdText;
    @BindView(R.id.hms_text)
    TextView mHmsText;
    @BindView(R.id.hm_space_text)
    TextView mHmSpaceText;
    @BindView(R.id.address_text)
    TextView mAddressText;
    private List<String> data1 = new ArrayList<>();
    private List<List<String>> data2 = new ArrayList<>();
    private List<List<List<String>>> data3 = new ArrayList<>();
    private CustomTimePicker mCustomTimePicker;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picker_view_example;
    }


    @Override
    protected void initView() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        ButterKnife.bind(this);
        setTitle("日期选择(pickerview)");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(PickerViewExampleActivity.this, ReadmeActivity.class)
                    .putExtra("url", url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(PickerViewExampleActivity.this, url);
        });
        InputStream inputStream;
        String data = null;
        try {
            inputStream = getAssets().open("data.txt");
            int size = inputStream.available();
            byte[] bytes = new byte[size];
            int len = -1;
            inputStream.read(bytes);
            inputStream.close();
            data = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Bean bean = new Gson().fromJson(data, Bean.class);
        for (int i = 0; i < bean.getData().size(); i++) {
            data1.add(bean.getData().get(i).getName());
            List<String> list = new ArrayList<>();
            List<List<String>> listList = new ArrayList<>();
            for (int j = 0; j < bean.getData().get(i).getCity().size(); j++) {
                list.add(bean.getData().get(i).getCity().get(j).getName());
                List<String> list1 = new ArrayList<>();
                for (int k = 0; k < bean.getData().get(i).getCity().get(j).getArea().size(); k++) {
                    list1.add(bean.getData().get(i).getCity().get(j).getArea().get(k));
                }
                listList.add(list1);
            }
            data2.add(list);
            data3.add(listList);
        }

    }


    @OnClick({R.id.ymdhms_layout, R.id.ymdhms_layout_with, R.id.ymd_layout, R.id.hms_layout, R.id
            .hm_space_layout, R.id.address_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ymdhms_layout:
                mCustomTimePicker = new CustomTimePicker(PickerViewExampleActivity
                        .this, new CustomTimePicker.OnTimePickerSelectedListener() {
                    @Override
                    public void selected(Date date) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String format = dateFormat.format(date);
                        mYmdhmsText.setText(format);

                    }
                });
                mCustomTimePicker.setType(new boolean[]{true, true, true, true, true, true})
                        .show();
                break;
            case R.id.ymdhms_layout_with:
                mCustomTimePicker = new CustomTimePicker(PickerViewExampleActivity
                        .this, new CustomTimePicker.OnTimePickerSelectedListener() {
                    @Override
                    public void selected(Date date) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String format = dateFormat.format(date);
                        mYmdhmsTextWith.setText(format);

                    }
                });
                mCustomTimePicker.setType(new boolean[]{true, true, true, true, true, true})
                        .setShowType(true).show();
                break;
            case R.id.ymd_layout:
                mCustomTimePicker = new CustomTimePicker(PickerViewExampleActivity
                        .this, new CustomTimePicker.OnTimePickerSelectedListener() {
                    @Override
                    public void selected(Date date) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String format = dateFormat.format(date);
                        mYmdText.setText(format);

                    }
                });
                mCustomTimePicker.setType(new boolean[]{true, true, true, false, false, false})
                        .show();
                break;
            case R.id.hms_layout:
                mCustomTimePicker = new CustomTimePicker(PickerViewExampleActivity
                        .this, new CustomTimePicker.OnTimePickerSelectedListener() {
                    @Override
                    public void selected(Date date) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                        String format = dateFormat.format(date);
                        mHmsText.setText(format);

                    }
                });
                mCustomTimePicker.setType(new boolean[]{false, false, false, true, true, true})
                        .show();
                break;
            case R.id.hm_space_layout:
                CustomTimePicker customTimePicker = new CustomTimePicker(PickerViewExampleActivity
                        .this, new CustomTimePicker.OnTimePickerSelectedListener() {
                    @Override
                    public void selected(Date date) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                        String format = dateFormat.format(date);
                        mHmSpaceText.setText(format);

                    }
                });
                customTimePicker.setType(new boolean[]{false, false, false, true, true, false})
                        .setMinuteSpace(5)
                        .show();
                break;
            case R.id.address_layout:
                CustomPickerView customPickerView = new CustomPickerView(PickerViewExampleActivity
                        .this, new CustomPickerView.OnChooseItemListener() {
                    @Override
                    public void itemSelected(int position1, int position2, int position3) {
                        mAddressText.setText(data1.get(position1) + data2.get(position1).get
                                (position2) + data3.get(position1).get(position2).get(position3));
                    }
                });
                customPickerView.setData(data1, data2, data3);
                customPickerView
                        .setCycle(false)
                        .show();
                break;
            default:
                break;
        }


    }
}
