package com.wya.example.module.example.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.customitems.WYAInputItem;
import com.wya.utils.utils.AppUtil;
import com.wya.utils.utils.PhoneUtil;

import static com.wya.example.module.example.fragment.ExampleFragment.EXTRA_URL;

/**
 * @date: 2019/1/4 11:41
 * @author: Chunjiang Mao
 * @classname: AboutUsFragment
 * @describe: AboutUsFragment
 */

public class AboutUsFragment extends Fragment {

    private View view;
    private TextView tvVersion;
    private TextView tvSystemVersion;
    private TextView tvDevice;
    private WYAInputItem gitHubAddress;
    private WYAInputItem gitHubContributor;
    private WYAInputItem gitHubVersion;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.about_us_fragment, null);
        initView();
        return view;
    }

    private void initView() {
        tvVersion = view.findViewById(R.id.tv_version);
        gitHubAddress = view.findViewById(R.id.git_hub_address);
        gitHubContributor = view.findViewById(R.id.git_hub_contributor);
        gitHubVersion = view.findViewById(R.id.git_hub_version);
        tvDevice = view.findViewById(R.id.tv_device);
        tvSystemVersion = view.findViewById(R.id.tv_system_version);
        tvVersion.setText(AppUtil.getVersionName(getActivity()));
        tvDevice.setText("Device : " + PhoneUtil.getInstance().getMobileBrand() + " " + PhoneUtil.getInstance().getPhoneModel());
        tvSystemVersion.setText("System : " + PhoneUtil.getInstance().getSDKVersion());
        gitHubAddress.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ReadmeActivity.class).putExtra(EXTRA_URL, "https://github.com/wya-team/wya-android-kit/blob/develop/README.md").putExtra("skip", true));
        });
        gitHubContributor.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ReadmeActivity.class).putExtra(EXTRA_URL, "https://github.com/wya-team/wya-android-kit/blob/develop/CONTRIBUTING.md").putExtra("skip", true));
        });
        gitHubVersion.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ReadmeActivity.class).putExtra(EXTRA_URL, "https://github.com/wya-team/wya-android-kit/blob/develop/CHANGELOG.md"));
        });

    }
}
