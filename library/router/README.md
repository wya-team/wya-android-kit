# Router

## 使用

> `build.gradle`中添加

```groovy

apply plugin: 'com.weiyian.android'  // auto register plugin

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.getName())
    }
}

buildscript {
    repositories {
        maven { url 'https://raw.githubusercontent.com/wya-team/wya-android-kit/develop/aar/snapshot' }  // kit 库地址
    }

    dependencies {
        classpath 'com.weiyian.android:router-auto-register:1.0.2' // auto register
    }
}


dependencies{

    kapt 'com.weiyian.android:router-compiler:1.0.2' // kotlin 使用 kapt , java 使用 annotationProcessor
    implementation 'com.weiyian.android:router-api:1.0.2'
    implementation 'com.weiyian.android:router-annotation:1.0.2'

}

```



