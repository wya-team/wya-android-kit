package com.wya.hardware.upload;

import android.content.Context;
import android.util.Log;

import com.wya.hardware.upload.net.ResultApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author :
 */
public class Presenter {
    
    private ResultApi mResultApi = new ResultApi();
    
    public void upload(Context context, OssInfo ossInfo, PostAfterInterface postAfter) {
        if (null == ossInfo || null == context) {
            return;
        }
        mResultApi.upload(ossInfo, ossInfo.getKey(), ossInfo.getFile()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (null != postAfter) {
                    postAfter.onPostAfter(1, "upload success", ossInfo.getResultUrl());
                }
                Log.e("TAG", "upload result = " + ossInfo.getResultUrl());
            }
            
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("TAG", "upload error = " + t.getMessage());
                if (null != postAfter) {
                    postAfter.onPostAfter(0, "upload error", t.getMessage());
                }
            }
        });
        
    }
    
}
