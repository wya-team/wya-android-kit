package com.wya.example.module.library.loadsir;

import com.weiyian.android.loadsir.callback.Callback;
import com.wya.example.R;

/**
 * @author : xdl
 * @time : 2019/07/18
 * @describe :
 */
public class EmptyCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.loadsir_empty_layout;
    }
}
