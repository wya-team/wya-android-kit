package com.weiyian.android.net.callback;

import android.util.Log;

import com.google.gson.internal.$Gson$Types;
import com.weiyian.android.net.model.ApiResult;
import com.weiyian.android.net.utils.Utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * @author :
 */
public abstract class BaseCallBackProxy<T extends ApiResult<R>, R> implements IType<T> {
    
    private BaseCallBack<R> mCallBack;
    
    public BaseCallBackProxy(BaseCallBack<R> callBack) {
        mCallBack = callBack;
    }
    
    public BaseCallBack getCallBack() {
        return mCallBack;
    }
    
    @Override
    public Type getType() {
        // CallBack代理方式，获取需要解析的Type
        Type typeArguments = null;
        if (mCallBack != null) {
            // 如果用户的信息是返回List需单独处理
            Type rawType = mCallBack.getRawType();
            if (List.class.isAssignableFrom(Utils.INSTANCE.getClass(rawType, 0)) || Map.class.isAssignableFrom(Utils.INSTANCE.getClass(rawType, 0))) {
                typeArguments = mCallBack.getType();
            } else {
                Type type = mCallBack.getType();
                typeArguments = Utils.INSTANCE.getClass(type, 0);
            }
        }
        if (typeArguments == null) {
            typeArguments = ResponseBody.class;
        }
        Type rawType = Utils.INSTANCE.findNeedType(getClass());
        if (rawType instanceof ParameterizedType) {
            rawType = ((ParameterizedType) rawType).getRawType();
        }
        
        Log.e("TAG", "[BaseCallBackProxy] [getType] rawType = " + rawType);
        return $Gson$Types.newParameterizedTypeWithOwner(null, rawType, typeArguments);
    }
    
}
