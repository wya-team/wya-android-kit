package com.wya.hardware.upload;

/**
 * @author :
 */
public class OssInfo implements IOssInfo {
    
    @Override
    public String getOSSAccessKeyId() {
        return OSSAccessKeyId;
    }
    
    @Override
    public void setOSSAccessKeyId(String OSSAccessKeyId) {
        this.OSSAccessKeyId = OSSAccessKeyId;
    }
    
    public String getAccessKeySecret() {
        return accessKeySecret;
    }
    
    @Override
    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
    
    @Override
    public String getHost() {
        return host;
    }
    
    @Override
    public void setHost(String host) {
        this.host = host;
    }
    
    @Override
    public String getPolicy() {
        return policy;
    }
    
    @Override
    public void setPolicy(String policy) {
        this.policy = policy;
    }
    
    @Override
    public String getSignature() {
        return signature;
    }
    
    @Override
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    public String getExpire() {
        return expire;
    }
    
    @Override
    public void setExpire(String expire) {
        this.expire = expire;
    }
    
    @Override
    public String getBucket() {
        return bucket;
    }
    
    @Override
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
    
    @Override
    public String getDir() {
        return dir;
    }
    
    @Override
    public void setDir(String dir) {
        this.dir = dir;
    }
    
    public String getRegion() {
        return region;
    }
    
    @Override
    public void setRegion(String region) {
        this.region = region;
    }
    
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
    
    @Override
    public String getKey() {
        return key;
    }
    
    @Override
    public void setKey(String key) {
        this.key = key;
    }
    
    @Override
    public String getFile() {
        return file;
    }
    
    @Override
    public void setFile(String file) {
        this.file = file;
    }
    
    @Override
    public String getResultUrl() {
        return resultUrl;
    }
    
    @Override
    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }
    
    public String getSuccess_action_status() {
        return success_action_status;
    }
    
    @Override
    public void setSuccessActionStatus(String success_action_status) {
        this.success_action_status = success_action_status;
    }
    
    private String success_action_status;
    
    @Override
    public String toString() {
        return "OssInfo{" +
                "OSSAccessKeyId='" + OSSAccessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", host='" + host + '\'' +
                ", policy='" + policy + '\'' +
                ", signature='" + signature + '\'' +
                ", expire='" + expire + '\'' +
                ", bucket='" + bucket + '\'' +
                ", dir='" + dir + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
