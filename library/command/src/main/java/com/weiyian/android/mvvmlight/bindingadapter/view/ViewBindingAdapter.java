package com.weiyian.android.mvvmlight.bindingadapter.view;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.weiyian.android.mvvmlight.command.ReplyCommand;
import com.weiyian.android.mvvmlight.command.ReplyCommand0;
import com.weiyian.android.mvvmlight.command.ResponseCommand;

/**
 * Created by kelin on 16-3-24.
 */
public final class ViewBindingAdapter {
    
    @BindingAdapter({"clickCommand"})
    public static void clickCommand(View view, final ReplyCommand0 clickCommand) {
        Log.e("TAG", "[ViewBindingAdapter] clickCommand = " + clickCommand);
        view.setOnClickListener(v -> {
            Log.e("TAG", "[ViewBindingAdapter] clickCommand click clickCommand = " + clickCommand);
            if (clickCommand != null) {
                clickCommand.execute();
            }
        });
    }
    
    @BindingAdapter({"requestFocus"})
    public static void requestFocusCommand(View view, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        } else {
            view.clearFocus();
        }
    }
    
    @BindingAdapter({"onFocusChangeCommand"})
    public static void onFocusChangeCommand(View view, final ReplyCommand<Boolean> onFocusChangeCommand) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (onFocusChangeCommand != null) {
                    onFocusChangeCommand.execute(hasFocus);
                }
            }
        });
    }
    
    @BindingAdapter({"onTouchCommand"})
    public static void onTouchCommand(View view, final ResponseCommand<MotionEvent, Boolean> onTouchCommand) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (onTouchCommand != null) {
                    return onTouchCommand.execute(event);
                }
                return false;
            }
        });
    }
}

