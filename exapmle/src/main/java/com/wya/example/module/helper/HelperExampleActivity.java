package com.wya.example.module.helper;

import android.content.pm.PackageManager;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.helper.WYAConstants;
import com.wya.utils.utils.AppUtil;
import com.wya.utils.utils.ColorUtil;
import com.wya.utils.utils.PhoneUtil;

import butterknife.BindView;

public class HelperExampleActivity extends BaseActivity {

    @BindView(R.id.tv_phone_info)
    TextView tvPhoneInfo;
    @BindView(R.id.tv_app_info)
    TextView tvAppInfo;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_helper;
    }

    @Override
    protected void initView() {
        setToolBarTitle("基本信息");
        try {
            String appPermissions = "";
            for (int i = 0; i < PhoneUtil.getInstance().getAppPermissions(getApplicationContext()).length; i++) {
                appPermissions = appPermissions + "     " + PhoneUtil.getInstance().getAppPermissions(getApplicationContext())[i] + "\n";
            }

            tvPhoneInfo.setText("手机系统版本号：" + PhoneUtil.getInstance().getSDKVersionNumber() + "\n"
                            + "手机型号：" + PhoneUtil.getInstance().getPhoneModel() + "\n"
                            + "手机宽度：" + PhoneUtil.getInstance().getPhoneWidth(getApplicationContext()) + "\n"
                            + "手机高度：" + PhoneUtil.getInstance().getPhoneHeight(getApplicationContext()) + "\n"
                            //需要动态获取READ_PHONE_STATE权限
//                    + "手机imei串号 ,GSM手机的 IMEI 和 CDMA手机的 MEID.：" + PhoneUtil.getInstance().getPhoneImei(getApplicationContext()) + "\n"
//                    + "手机sim卡号：" + PhoneUtil.getInstance().getPhoneSim(getApplicationContext()) + "\n"
//                    + "手机号：" + PhoneUtil.getInstance().getPhoneNum(getApplicationContext()) + "\n"
                            + "sd卡是否挂载：" + PhoneUtil.getInstance().isSDCardMount() + "\n"
                            + "sd卡剩余空间的大小：" + PhoneUtil.getInstance().getSDFreeSize() + "\n"
                            + "sd卡空间的总大小：" + PhoneUtil.getInstance().getSDAllSize() + "\n"
                            + "一个apk是否安装：" + PhoneUtil.getInstance().isApkInstalled(getApplicationContext(), AppUtil.getPackageName(getApplicationContext())) + "\n"
                            + "是否是平板：" + PhoneUtil.getInstance().isTablet(getApplicationContext()) + "\n"
                            + "应用权限 名称列表：" + appPermissions
                            + "是否是平板：" + PhoneUtil.getInstance().isTablet(getApplicationContext()) + "\n"
            );
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tvAppInfo.setText(
                "主题色：" + ColorUtil.int2Hex(WYAConstants.THEME_COLOR) + "\n"
                        + "是否打印日志：" + WYAConstants.IS_SHOW_LOG + "\n"
                        + "数据库名称：" + WYAConstants.DB_NAME + "\n"
                        + "是否Debug：" + WYAConstants.IS_DEBUG + "\n"
                        + "应用名称：" + AppUtil.getAppName(getApplicationContext()) + "\n"
                        + "包名：" + AppUtil.getPackageName(getApplicationContext()) + "\n"
                        + "当前应用的版本名称：" + AppUtil.getVersionName(getApplicationContext()) + "\n"
                        + "当前应用的版本号：" + AppUtil.getVersionCode(getApplicationContext())
        );
    }

}
