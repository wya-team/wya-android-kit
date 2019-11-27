package com.weiyian.android.net.func;

import com.weiyian.android.net.exception.ApiException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * @author :
 */
public class RetryExceptionFunc implements Function<Observable<? extends Throwable>, Observable<?>> {
    
    private int count = 0;
    private long delay = 500;
    private long increaseDelay = 3000;
    
    public RetryExceptionFunc() {
    
    }
    
    public RetryExceptionFunc(int count, long delay) {
        this.count = count;
        this.delay = delay;
    }
    
    public RetryExceptionFunc(int count, long delay, long increaseDelay) {
        this.count = count;
        this.delay = delay;
        this.increaseDelay = increaseDelay;
    }
    
    @Override
    public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) throws Exception {
        return observable.zipWith(Observable.range(1, count + 1), (BiFunction<Throwable, Integer, Wrapper>) Wrapper::new).flatMap((Function<Wrapper, ObservableSource<?>>) wrapper -> {
            int errCode = 0;
            if (wrapper.throwable instanceof ApiException) {
                ApiException exception = (ApiException) wrapper.throwable;
                errCode = exception.getCode();
            }
            
            boolean isRetryFail = (wrapper.throwable instanceof ConnectException
                    || wrapper.throwable instanceof SocketTimeoutException
                    || errCode == ApiException.ERROR.INSTANCE.getNETWORD_ERROR()
                    || errCode == ApiException.ERROR.INSTANCE.getTIMEOUT_ERROR()
                    || wrapper.throwable instanceof SocketTimeoutException
                    || wrapper.throwable instanceof TimeoutException)
                    && wrapper.index < count + 1;
            if (isRetryFail) {
                // 如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                return Observable.timer(delay + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS);
            }
            return Observable.error(wrapper.throwable);
        });
    }
    
    private class Wrapper {
        
        private int index;
        private Throwable throwable;
        
        Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }
    
}
