package com.weiyian.android.net.utils;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * <p>描述：http工具类</p>
 * 作者： zhouyou<br>
 * 日期： 2017/5/15 17:13 <br>
 * 版本： v1.0<br>
 *
 * @author :
 */
public class HttpUtil {
    public static final Charset UTF8 = Charset.forName("UTF-8");
    
    public static String createUrlFromParams(String url, Map<String, String> params) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            if (url.indexOf('&') > 0 || url.indexOf('?') > 0) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            for (Map.Entry<String, String> urlParams : params.entrySet()) {
                String urlValues = urlParams.getValue();
                // 对参数进行 utf-8 编码,防止头信息传中文
                sb.append(urlParams.getKey()).append("=").append(urlValues).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
