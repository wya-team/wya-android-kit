# hwpush

1. 主module下添加 `maven { url huaweiMavenUrl }`

> 结构

```
├── README.md
├── build.gradle
├── gradle.properties
├── hwpush.iml
├── proguard-rules.pro
└── src
    └── main
        ├── AndroidManifest.xml
        └── java
            └── com
                └── weiyian
                    └── android
                        └── hwpush
                            └── agent
                                ├── HMSAgent.java
                                ├── common
                                │   ├── ActivityManager.java
                                │   ├── ActivityMgr.java
                                │   ├── ApiClientMgr.java
                                │   ├── BaseAgentActivity.java
                                │   ├── BaseApiAgent.java
                                │   ├── CallbackCodeRunnable.java
                                │   ├── CallbackResultRunnable.java
                                │   ├── CheckUpdateApi.java
                                │   ├── EmptyConnectCallback.java
                                │   ├── HMSAgentActivity.java
                                │   ├── HMSAgentLog.java
                                │   ├── IActivityDestroyedCallback.java
                                │   ├── IActivityPauseCallback.java
                                │   ├── IActivityResumeCallback.java
                                │   ├── IClientConnectCallback.java
                                │   ├── INoProguard.java
                                │   ├── IOUtils.java
                                │   ├── StrUtils.java
                                │   ├── ThreadUtil.java
                                │   ├── UIUtils.java
                                │   └── handler
                                │       ├── CheckUpdateHandler.java
                                │       ├── ConnectHandler.java
                                │       ├── ICallbackCode.java
                                │       └── ICallbackResult.java
                                └── push
                                    ├── DeleteTokenApi.java
                                    ├── EnableReceiveNormalMsgApi.java
                                    ├── EnableReceiveNotifyMsgApi.java
                                    ├── GetPushStateApi.java
                                    ├── GetTokenApi.java
                                    ├── QueryAgreementApi.java
                                    └── handler
                                        ├── DeleteTokenHandler.java
                                        ├── EnableReceiveNormalMsgHandler.java
                                        ├── EnableReceiveNotifyMsgHandler.java
                                        ├── GetPushStateHandler.java
                                        ├── GetTokenHandler.java
                                        └── QueryAgreementHandler.java

```