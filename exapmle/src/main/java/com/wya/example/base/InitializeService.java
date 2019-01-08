package com.wya.example.base;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * @author :
 */
public class InitializeService extends IntentService {
    
    private static final String ACTION_APP_CREATE = "com.weiyian.kit.base.action.INIT";
    
    public InitializeService() {
        super("InitializeService");
    }
    
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }
    
    private void performInit() {
    
    }
    
    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_APP_CREATE);
        context.startService(intent);
    }
    
}
