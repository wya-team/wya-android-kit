package com.wya.hardware.upload;

import android.content.Context;
import android.util.Log;

import com.wya.hardware.upload.net.BaseResult;
import com.wya.hardware.upload.net.BaseSubscriber;
import com.wya.hardware.upload.net.ResultApi;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author :
 */
public class Presenter {
    
    private ResultApi mResultApi = new ResultApi();
    
    public void upload(Context context, OssInfo ossInfo, String fileName, String filePath) {
        ext(mResultApi.upload(ossInfo, fileName, filePath), new BaseSubscriber<BaseResult>() {
            @Override
            public void onNext(BaseResult result) {
                if (null == result) {
                    return;
                }
                Log.e("ZCQ", "upload result = " + result.toString());
            }
            
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
            
        });
    }
    
    public static <T> void ext(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    
}
