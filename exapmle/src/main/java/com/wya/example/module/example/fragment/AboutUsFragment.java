package com.wya.example.module.example.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wya.example.R;


public class AboutUsFragment extends Fragment {

    private View view;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.about_us_fragment, null);
        initView();
        return view;
    }

    private void initView() {
    }
}
