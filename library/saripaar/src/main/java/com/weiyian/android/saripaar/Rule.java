package com.weiyian.android.saripaar;

import android.content.Context;

/**
 * @author :
 */
public abstract class Rule<VALIDATABLE> {
    
    /**
     * 序列/顺序
     */
    private final int mSequence;
    
    protected Rule(final int sequence) {
        mSequence = sequence;
    }
    
    public final int getSequence() {
        return mSequence;
    }
    
    public abstract boolean isValid(VALIDATABLE validatable);
    
    public abstract String getMessage(Context context);
    
}
