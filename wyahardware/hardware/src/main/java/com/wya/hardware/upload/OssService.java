package com.wya.hardware.upload;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;

/**
 * @author :
 */
public class OssService {
    
    private OSS mOss;
    private String accessKeyId;
    private String bucketName;
    private String accessKeySecret;
    private String endpoint;
    private String policy;
    private String signature;
    private Context context;
    
    private ProgressListener mProgressCallback;
    
    public OssService(Context context, String accessKeyId, String accessKeySecret, String endpoint, String bucketName, String policy, String signature) {
        this.context = context;
        this.endpoint = endpoint;
        this.bucketName = bucketName;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        
        this.policy = policy;
        this.signature = signature;
    }
    
    public void initOSSClient() {
        if (TextUtils.isEmpty(accessKeyId) || TextUtils.isEmpty(accessKeySecret)) {
            return;
        }
        
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, signature);
        
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);
        conf.setSocketTimeout(15 * 1000);
        conf.setMaxConcurrentRequest(8);
        conf.setMaxErrorRetry(2);
        mOss = new OSSClient(context, endpoint, credentialProvider, conf);
    }
    
    public void initOSSClient(int connectionTimeout, int socketTimeout, int maxConcurrentRequest, int maxErrorRetry) {
        if (TextUtils.isEmpty(accessKeyId) || TextUtils.isEmpty(accessKeySecret)) {
            return;
        }
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(connectionTimeout);
        conf.setSocketTimeout(socketTimeout);
        conf.setMaxConcurrentRequest(maxConcurrentRequest);
        conf.setMaxErrorRetry(maxErrorRetry);
        if (null != credentialProvider) {
            mOss = new OSSClient(context, endpoint, credentialProvider, conf);
        }
    }
    
    public void startUpload(Context context, String fileName, String filePath, String resultUrl) {
        startUpload(context, fileName, filePath, "", null);
    }
    
    public void startUpload(Context context, String fileName, String path, String resultUrl, PostAfterInterface postAfter) {
        if (null == context) {
            return;
        }
        if (fileName == null || "".equals(fileName)) {
            Log.e("TAG", "[OssService] [startUpload] return , 文件名为空");
            if (null != postAfter) {
                postAfter.onPostAfter(0, "文件名为空", null);
            }
            return;
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, path);
        if (null == mOss) {
            postAfter.onPostAfter(0, "缺失必要参数", null);
            return;
        }
        if (path == null || "".equals(path)) {
            Log.e("TAG", "[OssService] [startUpload] return , 图片地址为空");
            if (null != postAfter) {
                postAfter.onPostAfter(0, "图片地址为空", null);
            }
            return;
        }
        
        putObjectRequest.setProgressCallback((request, currentSize, totalSize) -> {
            Log.e("TAG", "currentSize: " + currentSize + " totalSize: " + totalSize);
            double progress = currentSize * 1.0 / totalSize * 100.f;
            if (mProgressCallback != null) {
                mProgressCallback.onProgress(progress);
            }
        });
        
    }
    
    public void setProgressCallback(ProgressListener progressCallback) {
        this.mProgressCallback = progressCallback;
    }
    
}
