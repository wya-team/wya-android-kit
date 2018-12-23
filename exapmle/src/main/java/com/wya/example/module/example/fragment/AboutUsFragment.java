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


public class AboutUsFragment extends Fragment {

    private View view;
    private TextView tv_version;
    private WYAInputItem git_hub_address;
    private WYAInputItem git_hub_contributor;
    private WYAInputItem git_hub_version;


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.about_us_fragment, null);
        initView();
        return view;
    }

    private void initView() {
        tv_version = view.findViewById(R.id.tv_version);
        git_hub_address = view.findViewById(R.id.git_hub_address);
        git_hub_contributor = view.findViewById(R.id.git_hub_contributor);
        git_hub_version = view.findViewById(R.id.git_hub_version);
        tv_version.setText(AppUtil.getVersionName(getActivity()));
        git_hub_address.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ReadmeActivity.class).putExtra("url","https://github.com/wya-team/wya-android-kit/blob/develop/README.md").putExtra("skip", true));
        });
        git_hub_contributor.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ReadmeActivity.class).putExtra("url","https://github.com/wya-team/wya-android-kit/blob/develop/CONTRIBUTING.md").putExtra("skip", true));
        });
        git_hub_version.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ReadmeActivity.class).putExtra("url","https://github.com/wya-team/wya-android-kit/blob/develop/CHANGELOG.md"));
        });

    }
}
