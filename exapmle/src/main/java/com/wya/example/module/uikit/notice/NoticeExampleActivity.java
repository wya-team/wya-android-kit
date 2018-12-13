package com.wya.example.module.uikit.notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.uikit.notice.switcher.SwitcherView;

public class NoticeExampleActivity extends AppCompatActivity {
    
    private SwitcherView vsDown2Up, vsUp2Down, vsLeft2Right, vsRight2Left, vsCusAnim;
    
    public static void start(Activity activity) {
        if (null == activity) {
            return;
        }
        Intent intent = new Intent(activity, NoticeExampleActivity.class);
        activity.startActivity(intent);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_notice_view);
        
        // up2down
        vsUp2Down = findViewById(R.id.vs_up2down);
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
        vsUp2Down.inflate(R.layout.item_switch_view).startAutoPlay();
        
        // down2up
        vsDown2Up = findViewById(R.id.vs_down2up);
        vsDown2Up.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (null == nextView) return;
                if (index == 3) vsDown2Up.pauseAutoPlay();
                final String tostText = index + "--- 从下到上 ---";
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(tostText);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vsDown2Up.startAutoPlay();
                        Toast.makeText(v.getContext().getApplicationContext(), tostText, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        vsDown2Up.inflate(R.layout.item_switch_view).startAutoPlay();
        
        // left2right
        vsLeft2Right = findViewById(R.id.vs_left2right);
        vsLeft2Right.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (nextView == null) return;
                final String tostText = index + "--- 从左到右从左到右长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长长的文案 ---";
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(tostText);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext().getApplicationContext(), tostText, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        vsLeft2Right.inflate(R.layout.item_switch_view).startAutoPlay();
        
        // right2left
        vsRight2Left = findViewById(R.id.vs_right2left);
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
        vsRight2Left.inflate(R.layout.item_switch_view).startAutoPlay();
        
        vsCusAnim = findViewById(R.id.vs_cus_anim);
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
        vsCusAnim.setAnimation(null, null).inflate(R.layout.item_switch_view).startAutoPlay();
    }
    
}

