# Upload

## 功能说明
上传文件至阿里云

## 用法说明

1. 获取必要参数（如 签名等）, 对应对象OssInfo

```json
{
    "OSSAccessKeyId": "LTAIAQhsjm4iRxso",
    "accessKeySecret": "7P41CtpvNaG2R4MUCSuHTU8ADgylUC",
    "host": "oss-cn-hangzhou.aliyuncs.com",
    "policy": "eyJleHBpcmF0aW9uIjoiMjAxOS0wNC0wM1QxNjowODowOVoiLCJjb25kaXRpb25zIjpbWyJjb250ZW50LWxlbmd0aC1yYW5nZSIsMCwxMDQ4NTc2MDAwXSxbInN0YXJ0cy13aXRoIiwiJGtleSIsIm9hMlwvMjAxOTA0MDNcLyJdXX0=",
    "signature": "sM0daXt3aG+DZHrlFDFaP2VvLQU=",
    "expire": 1554278889,
    "bucket": "wyatest",
    "dir": "oa2/20190403/", 
    "region": "oss-cn-hangzhou"
}
```
> dir // 文件路径 后面拼接时间戳

2. 调用UploadUtil
```
    UploadUtil.updload(Context context, OssInfo ossInfo, String fileName, String filePath, PostAfterInterface postAfter)
```
> fileName -- 文件名 eg "xxx.png"

> filePath -- 本地文件路径 eg /storage/emulated/0/Android/xxx.png