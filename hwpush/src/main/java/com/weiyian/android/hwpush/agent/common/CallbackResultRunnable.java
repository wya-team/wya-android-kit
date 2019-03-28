package com.weiyian.android.hwpush.agent.common;

import com.weiyian.android.hwpush.agent.common.handler.ICallbackResult;
/**
 * 回调线程
 *
 * @author :
 */
public class CallbackResultRunnable<R> implements Runnable {
    
    private ICallbackResult<R> handlerInner;
    private int rtnCodeInner;
    private R resultInner;
    
    public CallbackResultRunnable(ICallbackResult<R> handler, int rtnCode, R payInfo) {
        handlerInner = handler;
        rtnCodeInner = rtnCode;
        resultInner = payInfo;
    }
    
    @Override
    public void run() {
        if (handlerInner != null) {
            handlerInner.onResult(rtnCodeInner, resultInner);
        }
    }
}