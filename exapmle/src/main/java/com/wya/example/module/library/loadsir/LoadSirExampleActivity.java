package com.wya.example.module.library.loadsir;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

import com.weiyian.android.loadsir.callback.Callback;
import com.weiyian.android.loadsir.callback.ProgressCallback;
import com.weiyian.android.loadsir.core.LoadService;
import com.weiyian.android.loadsir.core.LoadSir;
import com.weiyian.android.loadsir.core.Transport;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *  @author : xdl
 *  @time   : 2019-07-18
 *  @describe  : 优雅的加载动画
 */
public class LoadSirExampleActivity extends BaseActivity {

    private LoadService mLoadService;

    /**
     * 获取布局文件id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_load_sir_example;
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView() {
        setTitle("loadSir");

        View content = getContent();

        mLoadService = LoadSir.Companion.getDefault().register(content, new Callback.OnReloadListener() {
            @Override
            public void onReload(@NotNull View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mLoadService.showCallback(ProgressCallback.class);
                        SystemClock.sleep(2000);
                        mLoadService.showSuccess();
                    }
                }).start();

            }
        }).setCallBack(EmptyCallback.class, new Transport() {
            @Override
            public void order(@Nullable Context context, @Nullable View view) {

            }
        });
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadService.showCallback(EmptyCallback.class);
            }
        }, 2000);



    }
}
