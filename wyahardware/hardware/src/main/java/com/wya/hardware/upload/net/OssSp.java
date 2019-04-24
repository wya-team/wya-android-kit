package com.wya.hardware.upload.net;

import android.content.Context;
public class OssSp extends BaseSp {
    
    private static final String KEY_SP_OSS = "SP_OSS";
    
    private static OssSp INSTANCE;
    
    private OssSp(Context context) {
        super(context, KEY_SP_OSS);
    }
    
    public static OssSp get(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new OssSp(context);
        }
        return INSTANCE;
    }
    
    private static final String KEY_OSS_BUCKET = "OSS_BUCKET";
    private static final String KEY_OSS_HOST = "OSS_HOST";
    
    public String getBucket() {
        return getString(KEY_OSS_BUCKET, "");
    }
    
    public void setBucket(String bucket) {
        putString(KEY_OSS_BUCKET, bucket);
    }
    
    public String getHost() {
        return getString(KEY_OSS_HOST, "");
    }
    
    public void setHoset(String host) {
        putString(KEY_OSS_HOST, host);
    }
}
