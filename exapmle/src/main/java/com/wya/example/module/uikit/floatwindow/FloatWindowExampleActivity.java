package com.wya.example.module.uikit.floatwindow;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.floatwindow.FloatWindow;
import com.wya.uikit.floatwindow.IFloatWindow;
import com.wya.uikit.floatwindow.MoveType;
import com.wya.uikit.floatwindow.PermissionUtil;
import com.wya.uikit.floatwindow.Screen;
import com.wya.utils.utils.ScreenUtil;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wya.example.module.example.fragment.ExampleFragment.EXTRA_URL;

/**
 * @author : XuDonglin
 * @time : 2019-02-28
 * @description : 浮窗example
 */
public class FloatWindowExampleActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView mWebview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_float_window_example;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        initWebView();
    }

    private void initWebView() {
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.loadUrl("https://www.baidu.com");

        setTitle("浮窗(floatwindow)");
        String url = getIntent().getStringExtra(EXTRA_URL);
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(this, ReadmeActivity.class).putExtra(EXTRA_URL, url));
        });
        setSecondRightIconLongClickListener(view -> {
            showShort("链接地址复制成功");
            StringUtil.copyString(this, url);
        });
    }

    @Override
    public void onBackPressed() {

        if (PermissionUtil.hasPermission(this)) {
            IFloatWindow old = FloatWindow.get("old");
            if (old == null) {
                IFloatWindow cancel2 = FloatWindow.get("cancel2");
                if (cancel2 == null) {
                    FloatWindow
                            .with(getApplicationContext())
                            .setTag("cancel2")
                            .setView(R.layout.layout_window)
                            .setCancelParam2(320)
                            .setMoveType(MoveType.inactive, 0, 0)
                            .setDesktopShow(false)
                            .build();
                }
                IFloatWindow cancel = FloatWindow.get("cancel");
                if (cancel == null) {
                    FloatWindow
                            .with(getApplicationContext())
                            .setTag("cancel")
                            .setView(R.layout.layout_window)
                            .setCancelParam2(300)
                            .setMoveType(MoveType.inactive, 0, 0)
                            .setDesktopShow(false)
                            .build();
                }

                ImageView imageView = new ImageView(this);
                imageView.setBackgroundResource(R.drawable.floatwindow_icon);
//                Glide.with(getApplicationContext()).load("http://pic43.nipic.com/20140711/19187786_140828149528_2.jpg").into(imageView);

                FloatWindow
                        .with(getApplicationContext())
                        .setTag("old")
                        .setView(imageView)
                        .setMoveType(MoveType.slide, 0, 0)
                        .setWidth(60)
                        .setFilter(false, FloatWindowExampleActivity.class)
                        .setHeight(60)
                        .setX(Screen.width, 0.8f)
                        .setY(ScreenUtil.getScreenHeight(this) / 3)
                        .setParentHeight(ScreenUtil.getScreenHeight(this))
                        .setMoveStyle(300, new AccelerateInterpolator())
                        .setDesktopShow(false)
                        .build();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), FloatWindowExampleActivity.class));
                    }
                });
                finish();
            } else {
                finish();
            }
        } else {
            Toast.makeText(this, "没有浮窗权限", Toast.LENGTH_SHORT).show();
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
            }
            startActivity(localIntent);
        }

    }
}
