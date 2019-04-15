package com.wya.hardware.upload;

/**
 * @author :
 */
public class OssInfo {
    
    public String getOSSAccessKeyId() {
        return OSSAccessKeyId;
    }
    
    public void setOSSAccessKeyId(String OSSAccessKeyId) {
        this.OSSAccessKeyId = OSSAccessKeyId;
    }
    
    public String getAccessKeySecret() {
        return accessKeySecret;
    }
    
    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
    
    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public String getPolicy() {
        return policy;
    }
    
    public void setPolicy(String policy) {
        this.policy = policy;
    }
    
    public String getSignature() {
        return signature;
    }
    
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    public String getExpire() {
        return expire;
    }
    
    public void setExpire(String expire) {
        this.expire = expire;
    }
    
    public String getBucket() {
        return bucket;
    }
    
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
    
    public String getDir() {
        return dir;
    }
    
    public void setDir(String dir) {
        this.dir = dir;
    }
    
    public String getRegion() {
        return region;
    }
    
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
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getFile() {
        return file;
    }
    
    public void setFile(String file) {
        this.file = file;
    }
    
    private String key;
    
    private String file;
    
    public String getResultUrl() {
        return resultUrl;
    }
    
    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }
    
    private String resultUrl;
    
    public String getSuccess_action_status() {
        return success_action_status;
    }
    
    public void setSuccess_action_status(String success_action_status) {
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
