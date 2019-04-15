package com.wya.hardware.upload;

/**
 * @author :
 */
public class Builder {
    private String OSSAccessKeyId;
    
    private String accessKeySecret;
    
    private String host;
    
    private String policy;
    
    private String signature;
    
    private String expire;
    
    private String bucket;
    
    private String dir;
    
    private String region;
    
    private String key;
    
    private String file;
    
    private String resultUrl;
    
    public Builder() {
    
    }
    
    public Builder setOssAccessKeyId(String ossAccessKeyId) {
        this.OSSAccessKeyId = ossAccessKeyId;
        return this;
    }
    
    public Builder setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
        return this;
    }
    
    public Builder setHost(String host) {
        this.host = host;
        return this;
    }
    
    public Builder setPolicy(String policy) {
        this.policy = policy;
        return this;
    }
    
    public Builder setSignature(String signature) {
        this.signature = signature;
        return this;
    }
    
    public Builder setExpire(String expire) {
        this.expire = expire;
        return this;
    }
    
    public Builder setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }
    
    public Builder setDir(String dir) {
        this.dir = dir;
        return this;
    }
    
    public Builder setRegion(String region) {
        this.region = region;
        return this;
    }
    
    public Builder setKey(String key) {
        this.key = key;
        return this;
    }
    
    public Builder setFile(String file) {
        this.file = file;
        return this;
    }
    
    public Builder setResultFile(String resultUrl) {
        this.resultUrl = resultUrl;
        return this;
    }
    
    public OssInfo create() {
        OssInfo ossInfo = new OssInfo();
        ossInfo.setOSSAccessKeyId(OSSAccessKeyId);
        ossInfo.setAccessKeySecret(accessKeySecret);
        ossInfo.setHost(host);
        ossInfo.setPolicy(policy);
        ossInfo.setSignature(signature);
        ossInfo.setExpire(expire);
        ossInfo.setBucket(bucket);
        ossInfo.setDir(dir);
        ossInfo.setRegion(region);
        ossInfo.setKey(key);
        ossInfo.setFile(file);
        ossInfo.setResultUrl(resultUrl);
        
        return ossInfo;
    }
    
}
