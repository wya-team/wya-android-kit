package com.wya.hardware.upload.net;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author :
 */
public class BaseSubscriber<T> implements Observer<T> {
    
    private Disposable disposable;
    
    public BaseSubscriber() {
    }
    
    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }
    
    @Override
    public void onNext(T t) {
    }
    
    @Override
    public void onError(Throwable e) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
    
    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}