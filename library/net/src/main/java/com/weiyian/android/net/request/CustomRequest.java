package com.weiyian.android.net.request;

import com.google.gson.reflect.TypeToken;
import com.weiyian.android.net.callback.BaseCallBack;
import com.weiyian.android.net.callback.BaseCallBackProxy;
import com.weiyian.android.net.func.HandleFuc;
import com.weiyian.android.net.func.RetryExceptionFunc;
import com.weiyian.android.net.func.TransApiResultFunc;
import com.weiyian.android.net.model.ApiResult;
import com.weiyian.android.net.subsciber.CallBackSubsciber;
import com.weiyian.android.net.transformer.HandleErrorTransformer;
import com.weiyian.android.net.utils.RxSchedulerUtil;
import com.weiyian.android.net.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @author :
 */
@SuppressWarnings(value = {"unchecked", "deprecation"})
public class CustomRequest extends BaseRequest<CustomRequest> {
    
    public CustomRequest() {
        super("");
    }
    
    @Override
    public CustomRequest build() {
        return super.build();
    }
    
    /**
     * 创建api服务  可以支持自定义的api，默认使用BaseApiService,上层不用关心
     *
     * @param service 自定义的apiservice class
     */
    public <T> T create(final Class<T> service) {
        checkvalidate();
        return retrofit.create(service);
    }
    
    private void checkvalidate() {
        Utils.INSTANCE.checkNotNull(retrofit, "请先在调用build()才能使用");
    }
    
    /**
     * 调用call返回一个Observable<T>
     * 举例：如果你给的是一个Observable<ApiResult<AuthModel>> 那么返回的<T>是一个ApiResult<AuthModel>
     */
    public <T> Observable<T> call(Observable<T> observable) {
        checkvalidate();
        return observable.compose(RxSchedulerUtil.ioMain())
                .compose(new HandleErrorTransformer())
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay));
    }
    
    public <T> void call(Observable<T> observable, BaseCallBack<T> callBack) {
        call(observable, new CallBackSubsciber(context, callBack));
    }
    
    public <R> void call(Observable observable, Observer<R> subscriber) {
        observable.compose(RxSchedulerUtil.ioMain())
                .subscribe(subscriber);
    }
    
    /**
     * 调用call返回一个Observable,针对ApiResult的业务<T>
     * 举例：如果你给的是一个Observable<ApiResult<AuthModel>> 那么返回的<T>是AuthModel
     */
    public <T> Observable<T> apiCall(Observable<ApiResult<T>> observable) {
        checkvalidate();
        return observable
                .map(new HandleFuc<T>())
                .compose(RxSchedulerUtil.<T>ioMain())
                .compose(new HandleErrorTransformer<T>())
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay));
    }
    
    public <T> Disposable apiCall(Observable<T> observable, BaseCallBack<T> callBack) {
        return call(observable, new BaseCallBackProxy<ApiResult<T>, T>(callBack) {
        });
    }
    
    public <T> Disposable call(Observable<T> observable, BaseCallBackProxy<? extends ApiResult<T>, T> proxy) {
        Observable<ApiResult<T>> cacheobservable = build().toObservable(observable, proxy);
        return cacheobservable.subscribeWith(new CallBackSubsciber<ApiResult<T>>(context, proxy.getCallBack()));
    }
    
    @SuppressWarnings(value = {"unchecked", "deprecation"})
    private <T> Observable<ApiResult<T>> toObservable(Observable observable, BaseCallBackProxy<? extends ApiResult<T>, T> proxy) {
        return observable.map(new TransApiResultFunc(proxy != null ? proxy.getType() : new TypeToken<ResponseBody>() {
        }.getType()))
                .compose(isSyncRequest ? RxSchedulerUtil.schedulToMain() : RxSchedulerUtil.scheduleToIOMain())
                .retryWhen(new RetryExceptionFunc(retryCount, retryDelay, retryIncreaseDelay));
    }
    
    @Override
    protected Observable<ResponseBody> generateRequest() {
        return null;
    }
}
