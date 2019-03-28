package com.weiyian.android.hwpush.agent.common;

import java.io.Closeable;
import java.io.IOException;

/**
 * 工具类
 *
 * @author :
 */
public final class IOUtils {
    public static void close(Closeable object) {
        if (object != null) {
            try {
                object.close();
            } catch (IOException e) {
                HMSAgentLog.d("close fail");
            }
        }
    }
}