package com.wya.hardware.upload;

/**
 * @author :
 */
public interface IOssInfo {
    
    /**
     * setOSSAccessKeyId
     *
     * @param ossAccessKeyId :
     */
    void setOSSAccessKeyId(String ossAccessKeyId);
    
    /**
     * setAccessKeySecret
     *
     * @param accessKeySecret :
     */
    void setAccessKeySecret(String accessKeySecret);
    
    /**
     * setHost
     *
     * @param host :
     */
    void setHost(String host);
    
    /**
     * setPolicy
     *
     * @param policy :
     */
    void setPolicy(String policy);
    
    /**
     * setSignature
     *
     * @param signature :
     */
    void setSignature(String signature);
    
    /**
     * setExpire
     *
     * @param expire :
     */
    void setExpire(String expire);
    
    /**
     * setBucket
     *
     * @param bucket :
     */
    void setBucket(String bucket);
    
    /**
     * setDir
     *
     * @param dir :
     */
    void setDir(String dir);
    
    /**
     * setRegion
     *
     * @param region :
     */
    void setRegion(String region);
    
    /**
     * setKey
     *
     * @param key :
     */
    void setKey(String key);
    
    /**
     * setFile
     *
     * @param file :
     */
    void setFile(String file);
    
    /**
     * setResultUrl
     *
     * @param resultUrl :
     */
    void setResultUrl(String resultUrl);
    
    /**
     * setSuccessActionStatus
     *
     * @param successActionStatus :
     */
    void setSuccessActionStatus(String successActionStatus);
    
    /**
     * getDir
     *
     * @return :
     */
    String getDir();
    
    /**
     * getBucket
     *
     * @return :
     */
    String getBucket();
    
    /**
     * getHost
     *
     * @return :
     */
    String getHost();
    
    /**
     * getKey
     *
     * @return :
     */
    String getKey();
    
    /**
     * getFile
     *
     * @return :
     */
    String getFile();
    
    /**
     * getResultUrl
     *
     * @return :
     */
    String getResultUrl();
    
    /**
     * getPolicy
     *
     * @return :
     */
    String getPolicy();
    
    /**
     * getOSSAccessKeyId
     *
     * @return :
     */
    String getOSSAccessKeyId();
    
    /**
     * getSignature
     *
     * @return :
     */
    String getSignature();
}
