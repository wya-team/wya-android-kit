package com.wya.hardware.camera.util;

import android.content.Context;
import android.media.AudioManager;
 /**
  * 创建日期：2018/12/5 13:55
  * 作者： Mao Chunjiang
  * 文件名称：AudioUtil
  * 类说明：
  */

public class AudioUtil {
    public static void setAudioManage(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_DTMF, 0, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, 0, 0);
    }
}
