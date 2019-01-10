package com.wya.example.module.uikit.segmentedcontrol;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wya.example.R;

/**
 *  @author : XuDonglin
 *  @time   : 2019-01-10
 *  @description     :
 */
public class ItemFragment extends Fragment {
    
    public static ItemFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    public ItemFragment() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int position = getArguments().getInt("position");
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        TextView textView = view.findViewById(R.id.msg);
        textView.setText("fragment " + position);
        return view;
    }
    
}
