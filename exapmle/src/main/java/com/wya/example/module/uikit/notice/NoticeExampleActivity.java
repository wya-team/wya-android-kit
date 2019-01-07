package com.wya.example.module.uikit.notice;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.notice.switcher.SwitcherView;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;

/**
 * @author :
 */
public class NoticeExampleActivity extends BaseActivity {
    
    @BindView(R.id.vs_down2up)
    SwitcherView vsDown2Up;
    
    @BindView(R.id.vs_up2down)
    SwitcherView vsUp2Down;
    
    @BindView(R.id.ll_closable_switcher)
    LinearLayout llClosableSwitcher;
    
    @BindView(R.id.vs_left2right)
    SwitcherView vsLeft2Right;
    
    @BindView(R.id.vs_right2left)
    SwitcherView vsRight2Left;
    
    private String noticeText;
    
    public static void start(Activity activity) {
        if (null == activity) {
            return;
        }
        Intent intent = new Intent(activity, NoticeExampleActivity.class);
        activity.startActivity(intent);
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.layout_activity_notice_view;
    }
    
    @Override
    protected void initView() {
        setTitle("通告栏(notice)");
        String url = getIntent().getStringExtra("url");
        noticeText = getResources().getString(R.string.string_uikit_notice_text);
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(NoticeExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(NoticeExampleActivity.this, url);
        });
        showSwitcher();
    }
    
    private void showSwitcher() {
        
        // down2up
        vsDown2Up.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (null == nextView) {
                    return;
                }
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(noticeText);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (vsDown2Up.isSkipable()) {
                            Toast.makeText(NoticeExampleActivity.this, "onClick", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        vsDown2Up.inflate(R.layout.item_switch_view).startSwitcher();
        
        // up2down
        vsUp2Down.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (null == nextView) {
                    return;
                }
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(noticeText);
                llClosableSwitcher.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (vsUp2Down.isClosable()) {
                            vsUp2Down.pauseSwitcher();
                            llClosableSwitcher.setVisibility(View.GONE);
                        } else if (vsUp2Down.isSkipable()) {
                            Toast.makeText(NoticeExampleActivity.this, "onClick", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        vsUp2Down.inflate(R.layout.item_switch_view).startSwitcher();
        
        // left2right
        vsLeft2Right.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (null == nextView) {
                    return;
                }
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(noticeText);
                nextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (vsLeft2Right.isSkipable()) {
                            Toast.makeText(NoticeExampleActivity.this, "onClick", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        vsLeft2Right.inflate(R.layout.item_switch_view).startSwitcher();
        
        // right2left
        vsRight2Left.setSwitcheNextViewListener(new SwitcherView.SwitcherViewListener() {
            @Override
            public void onSwitch(View nextView, int index) {
                if (null == nextView) {
                    return;
                }
                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(noticeText);
            }
        });
        vsRight2Left.inflate(R.layout.item_switch_view).startSwitcher();
    }
    
}

