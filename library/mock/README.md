# mock

  - 模拟接口 -- 测试时使用
     `xxx.json` xxx为接口名 -- TODO 可优化避免重复
     不使用时，可改为 `test-xxx.json` 或移除拦截器
     注意 : 调试使用，正式环境务必移除

  - 使用 -- 网络请求框架添加拦截器`interceptor`

     ```kotlin
      .addInterceptor(OkHttpMockInterceptor(getAndroidProvider(), 0))

     ```