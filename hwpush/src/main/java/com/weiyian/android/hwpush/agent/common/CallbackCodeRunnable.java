package com.weiyian.android.hwpush.agent.common;

import com.weiyian.android.hwpush.agent.common.handler.ICallbackCode;
/**
 * 回调线程
 */
public class CallbackCodeRunnable implements Runnable {
    
    private ICallbackCode handlerInner;
    private int rtnCodeInner;
    
    public CallbackCodeRunnable(ICallbackCode handler, int rtnCode) {
        handlerInner = handler;
        rtnCodeInner = rtnCode;
    }
    
    @Override
    public void run() {
        if (handlerInner != null) {
            handlerInner.onResult(rtnCodeInner);
        }
    }
}