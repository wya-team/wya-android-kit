package com.wya.example.module.uikit.popupwindow;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.example.module.hardware.scan.CustomCaptureActivity;
import com.wya.hardware.scan.Intents;
import com.wya.uikit.dialog.WYACustomDialog;
import com.wya.uikit.popupwindow.WYAPopupWindow;
import com.wya.uikit.toolbar.StatusBarUtil;
import com.wya.utils.utils.ScreenUtil;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;

import static com.wya.example.module.example.fragment.ExampleFragment.EXTRA_URL;

/**
 * @date: 2019/1/8 9:56
 * @author: Chunjiang Mao
 * @classname: PopupWindowExampleActivity
 * @describe: PopupWindowExampleActivity
 */

public class PopupWindowExampleActivity extends BaseActivity {
    
    public static final int REQUEST_CODE_SCAN = 0X01;
    @BindView(R.id.tv_result)
    TextView tvResult;
    private WYAPopupWindow wyaPopupWindow;
    private String url;
    
    @Override
    protected void initView() {
        StatusBarUtil.setLightMode(this);
        setTitle("PopupWindow");
        url = getIntent().getStringExtra(EXTRA_URL);
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setPopupWindow();
    }
    
    private void setPopupWindow() {
        setSecondRightIconClickListener(view -> wyaPopupWindow.show(view, -100, 0));
        wyaPopupWindow = new WYAPopupWindow.Builder(PopupWindowExampleActivity.this).setLayoutRes(R.layout.popopwindow_custom_list, v -> {
            TableRow tabScan = v.findViewById(R.id.tab_scan);
            TableRow tabHelp = v.findViewById(R.id.tab_help);
            tabScan.setOnClickListener(v1 -> checkPermission());
            tabHelp.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showShort("链接地址复制成功");
                    StringUtil.copyString(PopupWindowExampleActivity.this, url);
                    return true;
                }
            });
            tabHelp.setOnClickListener(v12 -> startActivity(new Intent(PopupWindowExampleActivity.this, ReadmeActivity.class).putExtra(EXTRA_URL, url)));
        }).build();
        
    }
    
    private void startScan() {
        wyaPopupWindow.dismiss();
        Intent intent = new Intent(this, CustomCaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }
    
    /**
     * 申请相机权限
     */
    @SuppressLint("CheckResult")
    private void checkPermission() {
        new RxPermissions(this).request(Manifest.permission.CAMERA)
                .subscribe(aBoolean -> {
                            if (aBoolean) {
                                startScan();
                            } else {
                                showPermissionDialog();
                            }
                        }
                );
    }
    
    private void showPermissionDialog() {
        WYACustomDialog wyaCustomDialog = new WYACustomDialog.Builder(this)
                .cancelable(true)
                .cancelTouchout(true)
                .title("提示")
                .message("请获取相机权限进行扫码!")
                .cancelShow(false)
                .width(ScreenUtil.getScreenWidth(this) * 3 / 4)
                .build();
        wyaCustomDialog.setNoClickListener(() -> {
            wyaCustomDialog.dismiss();
        });
        wyaCustomDialog.setYesClickListener(() -> wyaCustomDialog.dismiss());
        wyaCustomDialog.show();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN:
                    String result = data.getStringExtra(Intents.Scan.RESULT);
                    tvResult.setText("解析结果：" + result);
                    break;
                default:
                    break;
            }
            
        }
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_popup_window_example;
    }
    
}
