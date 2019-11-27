package com.weiyian.android.net.utils;

import com.weiyian.android.net.func.HandleFuc;
import com.weiyian.android.net.func.HttpResponseFunc;
import com.weiyian.android.net.model.ApiResult;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author :
 */
public class RxSchedulerUtil {
    
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) schedulersIo2MainTransformer;
    }
    
    public static final ObservableTransformer schedulersIo2MainTransformer = upstream -> upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    
    public static <T> ObservableTransformer<T, T> ioMain() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
    
    public static <T> ObservableTransformer<ApiResult<T>, T> scheduleToIOMain() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new HandleFuc<>())
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .onErrorResumeNext(new HttpResponseFunc<T>());
    }
    
    public static <T> ObservableTransformer<ApiResult<T>, T> schedulToMain() {
        return observable -> observable
                //.observeOn(AndroidSchedulers.mainThread())
                .map(new HandleFuc<>())
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .onErrorResumeNext(new HttpResponseFunc<T>());
    }
    
}
