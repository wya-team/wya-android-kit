package com.weiyian.android.net.request;

import com.weiyian.android.net.utils.RxSchedulerUtil;

import io.reactivex.Observable;

/**
 * @author :
 */
public class PostRequest extends BaseBodyRequest<PostRequest> {
    
    public PostRequest(String url) {
        super(url);
    }
    
    public Observable execute() {
        return build().toObservable(generateRequest());
    }
    
    /**
     * schedule
     *
     * @param observableOriginal :
     * @param <T>                :
     * @return :
     */
    private <T> Observable<T> toObservable(Observable observableOriginal) {
        return observableOriginal.compose(RxSchedulerUtil.applySchedulers());
    }
    
}
