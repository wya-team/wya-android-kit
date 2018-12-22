package com.wya.example.module.uikit.notice;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.notice.switcher.SwitcherView;

import butterknife.BindView;

public class NoticeExampleActivity extends BaseActivity {
    
    @BindView(R.id.vs_up2down)
    SwitcherView vsUp2Down;
    
    @BindView(R.id.vs_down2up)
    SwitcherView vsDown2Up;
    
    @BindView(R.id.vs_left2right)
    SwitcherView vsLeft2Right;
    
    @BindView(R.id.vs_right2left)
    SwitcherView vsRight2Left;
    
    private String noticeText = "这里是具体的通知，这里是具体的通知，这里...";
    
    public static void start(Activity activity) {
        if (null == activity) {
            return;
        }
        Intent intent = new Intent(activity, NoticeExampleActivity.class);
        activity.startActivity(intent);
    }
    
    @Override
    protected int getLayoutID() {
        return R.layout.layout_activity_notice_view;
    }
    
    @Override
    protected void initView() {
        setToolBarTitle("通告栏(notice)");
        String url = getIntent().getStringExtra("url");
        initImgRightAnther(R.drawable.icon_help, true);
        setRightImageAntherOnclickListener(view -> {
            startActivity(new Intent(NoticeExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });        showSwitcher();
    }
    
    private void showSwitcher() {
        // up2down
        vsUp2Down.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (null == nextView) return;
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(noticeText);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext().getApplicationContext(), "click 1", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        vsUp2Down.inflate(R.layout.item_switch_view).startSwitcher();
        
        // down2up
        vsDown2Up.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (null == nextView) return;
                if (index == 3) vsDown2Up.pauseSwitcher();
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(noticeText);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vsDown2Up.startSwitcher();
                        Toast.makeText(v.getContext().getApplicationContext(), "click 2", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        vsDown2Up.inflate(R.layout.item_switch_view).startSwitcher();
        
        // left2right
        vsLeft2Right.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (nextView == null) return;
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(noticeText);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext().getApplicationContext(), "click 3", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        vsLeft2Right.inflate(R.layout.item_switch_view).startSwitcher();
        
        // right2left
        vsRight2Left.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (nextView == null) return;
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(noticeText);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext().getApplicationContext(), "click 4", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        vsRight2Left.inflate(R.layout.item_switch_view).startSwitcher();
        
    }
    
}

