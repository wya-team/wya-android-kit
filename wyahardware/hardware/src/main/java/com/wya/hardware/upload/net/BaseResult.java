package com.wya.hardware.upload.net;

/**
 * @author :
 */
public class BaseResult<T> {
    
    /**
     * -1 -- 登录失效，需要重新登录
     * 0 -- 提出错误
     * 1 --  数据成功
     */
    public int status;
    
    public String msg;
    
    public T data;
    
}
