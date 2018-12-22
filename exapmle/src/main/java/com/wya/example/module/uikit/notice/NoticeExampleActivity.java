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

public class NoticeExampleActivity extends BaseActivity {

    private SwitcherView vsDown2Up, vsUp2Down, vsLeft2Right, vsRight2Left, vsCusAnim;

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
        });

        // up2down
        vsUp2Down = (SwitcherView) findViewById(R.id.vs_up2down);
        vsUp2Down.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (null == nextView) return;
                final String tostText = index + "--- 从上到下 ---";
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(tostText);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext().getApplicationContext(), tostText, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        vsUp2Down.inflate(R.layout.item_switch_view).startSwitcher();

        // down2up
        vsDown2Up = (SwitcherView) findViewById(R.id.vs_down2up);
        vsDown2Up.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (null == nextView) return;
                if (index == 3) vsDown2Up.pauseSwitcher();
                final String tostText = index + "--- 从下到上 ---";
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(tostText);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vsDown2Up.startSwitcher();
                        Toast.makeText(v.getContext().getApplicationContext(), tostText, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        vsDown2Up.inflate(R.layout.item_switch_view).startSwitcher();

        // left2right
        vsLeft2Right = (SwitcherView) findViewById(R.id.vs_left2right);
        vsLeft2Right.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (nextView == null) return;
                final String tostText = index + "--- 从左到右从左到右 ---";
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(tostText);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext().getApplicationContext(), tostText, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        vsLeft2Right.inflate(R.layout.item_switch_view).startSwitcher();

        // right2left
        vsRight2Left = (SwitcherView) findViewById(R.id.vs_right2left);
        vsRight2Left.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (nextView == null) return;
                final String tostText = index + "--- 从右到左 ---";
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(tostText);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext().getApplicationContext(), tostText, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        vsRight2Left.inflate(R.layout.item_switch_view).startSwitcher();

        vsCusAnim = (SwitcherView) findViewById(R.id.vs_cus_anim);
        vsCusAnim.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (nextView == null) return;
                final String tag = "自定义进出动画....";
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(tag);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext().getApplicationContext(), tag, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        vsCusAnim.setAnimation(null, null).inflate(R.layout.item_switch_view).startSwitcher();
    }

}

