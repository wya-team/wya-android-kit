package com.weiyian.android.loadsir.core;

import com.weiyian.android.loadsir.callback.Callback;

/**
 * @author :
 */
public interface Convertor<T> {
    
    Class<? extends Callback> map(T t);
    
}
