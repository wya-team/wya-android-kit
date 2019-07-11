package com.weiyian.android.net.request;

import android.util.Log;

import com.weiyian.android.net.callback.BaseCallBack;
import com.weiyian.android.net.model.ApiResult;
import com.weiyian.android.net.subsciber.CallBackSubsciber;
import com.weiyian.android.net.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author :
 */
@SuppressWarnings(value = {"unchecked", "deprecation"})
public class CustomBodyRequest extends BaseBodyRequest<CustomBodyRequest> {
    
    public CustomBodyRequest() {
        super("");
    }
    
    public <T> T create(final Class<T> service) {
        checkvalidate();
        return retrofit.create(service);
    }
    
    private void checkvalidate() {
        Utils.INSTANCE.checkNotNull(retrofit, "请先在调用build()才能使用");
    }
    
    public <T> Disposable execute(BaseCallBack<T> proxy) {
        return build().generateRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallBackSubsciber(context, proxy));
    }
    
    private static <T> Observable next(ApiResult<T> result) {
        if (null == result) {
            return Observable.empty();
        }
        return Observable.create((ObservableOnSubscribe) subscriber -> {
            try {
                subscriber.onNext(result);
                subscriber.onComplete();
            } catch (Exception e) {
                Log.e("TAG", "[CustomBodyRequest] [next] [e] = " + e.getMessage());
                subscriber.onError(e);
            }
        });
    }
    
}
