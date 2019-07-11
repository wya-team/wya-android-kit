package com.weiyian.android.net.request;

import com.weiyian.android.net.utils.RxSchedulerUtil;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author :
 */
@SuppressWarnings(value = {"unchecked", "deprecation"})
public class GetRequest extends BaseRequest<GetRequest> {
    
    public GetRequest(String url) {
        super(url);
    }
    
    @Override
    protected Observable<ResponseBody> generateRequest() {
        return apiService.get(url, params.urlParamsMap);
    }
    
    public Observable execute() {
        return build().toObservable(generateRequest());
    }
    
    private <T> Observable<T> toObservable(Observable observableOriginal) {
        return observableOriginal.compose(RxSchedulerUtil.applySchedulers());
    }
    
}
