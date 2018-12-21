package com.wya.example.module.uikit.badge;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.badge.Builder;
import com.wya.uikit.badge.DisplayUtil;
import com.wya.uikit.badge.IBadgeView;
import com.wya.uikit.toolbar.AndroidBugWorkaround;

import butterknife.BindView;
import butterknife.OnClick;

public class BadgeExampleActivity extends BaseActivity {
    
    @BindView(R.id.tv_dot)
    TextView tvDot;
    @BindView(R.id.tv_digital)
    TextView tvDigital;
    @BindView(R.id.ll_digital)
    LinearLayout llDigital;
    @BindView(R.id.tv_omit)
    TextView tvOmit;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.radio_omit)
    RadioButton radioOmit;
    @BindView(R.id.radio_not_omitted)
    RadioButton radioNotOmiited;
    
    @BindView(R.id.tv_isShow)
    TextView tvShow;
    @BindView(R.id.radio_show)
    RadioButton rbShow;
    @BindView(R.id.radio_hide)
    RadioButton rbHide;
    
    @BindView(R.id.tv_offset_x)
    TextView tvOffsetX;
    @BindView(R.id.tv_offset_y)
    TextView tvOffsetY;
    @BindView(R.id.sb_offset_x)
    SeekBar sbOffsetX;
    @BindView(R.id.sb_offset_y)
    SeekBar sbOffsetY;
    @BindView(R.id.tv_offset)
    TextView tvOffset;
    
    @BindView(R.id.edt_text)
    EditText edtText;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_badge_example;
    }
    
    private IBadgeView mBadgeOmit;
    private IBadgeView mBadgeShow;
    private IBadgeView mBadgeEdit;
    
    public static void start(Context context) {
        if (null != context) {
            context.startActivity(new Intent(context, BadgeExampleActivity.class));
        }
    }
    
    @Override
    protected void initView() {
        AndroidBugWorkaround.assistActivity(findViewById(android.R.id.content));
        initListener();
        showBadge(tvDot, 0);
        showBadge(tvDigital, 99);
        showBadge(llDigital, 9);
        showBadge(tvText, "NEW");
        showBadge(tvOmit, 999, false);
        radioOmit.setSelected(true);
        updateBadge(tvShow, 9, true);
        updateOffset(tvOffset, 9);
        
    }
    
    private void updateOffset(View view, int num) {
        if (null == view) {
            return;
        }
        mBadgeEdit = new Builder(this)
                .setOffset(0, 0)
                .setBadgeNum(num)
                .create();
        mBadgeEdit.bindToTarget(view);
    }
    
    private void initListener() {
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar == sbOffsetX || seekBar == sbOffsetY) {
                    int x = sbOffsetX.getProgress();
                    int y = sbOffsetY.getProgress();
                    tvOffsetX.setText("GravityOffsetX : " + x);
                    tvOffsetY.setText("GravityOffsetY : " + y);
                    mBadgeEdit.setOffset(x, y);
                }
            }
            
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            
            }
            
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            
            }
        };
        sbOffsetX.setOnSeekBarChangeListener(onSeekBarChangeListener);
        sbOffsetY.setOnSeekBarChangeListener(onSeekBarChangeListener);
        
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBadgeEdit.setBadgeText(s.toString());
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            
            }
        };
        edtText.addTextChangedListener(watcher);
    }
    
    @OnClick({R.id.radio_omit, R.id.radio_not_omitted, R.id.radio_show, R.id.radio_hide})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_omit:
                if (null != mBadgeOmit) {
                    mBadgeOmit.setOmitMode(true);
                }
                break;
            case R.id.radio_not_omitted:
                if (null != mBadgeOmit) {
                    mBadgeOmit.setOmitMode(false);
                }
                break;
            
            case R.id.radio_show:
                if (null != mBadgeShow) {
                    mBadgeShow.update(true);
                }
                break;
            
            case R.id.radio_hide:
                if (null != mBadgeShow) {
                    mBadgeShow.update(false);
                }
                break;
        }
    }
    
    public void showBadge(View view, int num) {
        showBadge(view, num, false);
    }
    
    public void updateBadge(View view, int num, boolean isShow) {
        if (view == null) {
            return;
        }
        mBadgeShow = new Builder(this)
                .setOffset(0, 0)
                .setBadgeNum(num)
                .isShow(isShow)
                .create();
        mBadgeShow.bindToTarget(view);
        
    }
    
    public void showBadge(View view, int num, boolean isOmitMode) {
        if (view == null) {
            return;
        }
        
        Builder builder = new Builder(this)
                .setOffset(0, 0)
                .setBadgeNum(num)
                .setOmitMode(isOmitMode);
        mBadgeOmit = builder.create();
        mBadgeOmit.bindToTarget(view);
    }
    
    public void showBadge(View view, String text) {
        if (view == null) {
            return;
        }
        
        IBadgeView badgeView = new Builder(this)
                .setOffset(0, 0)
                .setText(text)
                .setTextSize(DisplayUtil.sp2px(this, 10))
                .create();
        badgeView.bindToTarget(view);
    }
}

