package com.wya.hardware.upload.net;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author :
 */
public class BaseSp {
    
    private SharedPreferences mSharedPreferences;
    
    public BaseSp(Context context, String key) {
        if (null == context) {
            return;
        }
        mSharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
    }
    
    protected SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }
    
    protected Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }
    
    protected void putString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }
    
    protected String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }
    
    protected void putBoolean(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }
    
    protected boolean getBoolean(String key, boolean defVaule) {
        return mSharedPreferences.getBoolean(key, defVaule);
    }
    
    protected void putInt(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }
    
    protected int getInt(String key, int defVaule) {
        return mSharedPreferences.getInt(key, defVaule);
    }
    
    protected void putLong(String key, long value) {
        mSharedPreferences.edit().putLong(key, value).apply();
    }
    
    protected long getLong(String key, long defVaule) {
        return mSharedPreferences.getLong(key, defVaule);
    }
    
    protected void putStringSet(String key, Set<String> value) {
        mSharedPreferences.edit().putStringSet(key, value).apply();
    }
    
    protected Set<String> getStringSet(String key, Set<String> defValue) {
        return mSharedPreferences.getStringSet(key, defValue);
    }
    
    protected boolean contains(String key) {
        return mSharedPreferences.contains(key);
    }
    
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
    
    protected void remove(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }
    
    public void putHashMapData(String key, HashMap<String, String> datas) {
        JSONArray mJsonArray = new JSONArray();
        Iterator<Map.Entry<String, String>> iterator = datas.entrySet().iterator();
        JSONObject object = new JSONObject();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            try {
                object.put(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mJsonArray.put(object);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, mJsonArray.toString());
        editor.apply();
    }
    
    public HashMap<String, String> getHashMapData(String key) {
        HashMap<String, String> datas = new HashMap<>(16);
        String result = mSharedPreferences.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        datas.put(name, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return datas;
    }
    
}
