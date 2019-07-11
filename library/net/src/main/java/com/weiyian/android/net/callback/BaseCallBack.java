package com.weiyian.android.net.callback;

import android.util.Log;

import com.weiyian.android.net.exception.ApiException;
import com.weiyian.android.net.utils.Utils;

import java.lang.reflect.Type;

/**
 * @author :
 */
public abstract class BaseCallBack<T> implements IType<T> {
    
    /**
     * onStart
     */
    public abstract void onStart();
    
    /**
     * onLoading
     */
    public abstract void onLoading();
    
    /**
     * onError
     *
     * @param e :
     */
    public abstract void onError(ApiException e);
    
    /**
     * onSuccess
     *
     * @param t :
     */
    public abstract void onSuccess(T t);
    
    /**
     * onData
     */
    public abstract void onData(Object data);
    
    /**
     * onDataEmpty
     */
    public abstract void onDataEmpty(String message);
    
    /**
     * onNetworkError
     */
    public abstract void onNetworkError();
    
    /**
     * onCompleted
     */
    public abstract void onCompleted();
    
    @Override
    public Type getType() {
        Log.e("TAG", "[BaseCallBack] [getType] = " + Utils.INSTANCE.findNeedClass(getClass()));
        // 获取需要解析的泛型T类型
        return Utils.INSTANCE.findNeedClass(getClass());
    }
    
    public Type getRawType() {
        // 获取需要解析的泛型T raw类型
        Log.e("TAG", "[BaseCallBack] [getType] = " + Utils.INSTANCE.findRawType(getClass()));
        return Utils.INSTANCE.findRawType(getClass());
    }
    
}
