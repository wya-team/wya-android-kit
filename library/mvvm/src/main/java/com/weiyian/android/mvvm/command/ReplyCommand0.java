package com.weiyian.android.mvvm.command;

import com.weiyian.android.mvvm.consumer.Consumer0;
import com.weiyian.android.mvvm.function.Function0;

/**
 * Created by kelin on 15-8-4.
 */
public class ReplyCommand0 {
    
    private Consumer0 execute0;
    
    private Function0<Boolean> canExecute0;
    
    public ReplyCommand0(Consumer0 execute) {
        this.execute0 = execute;
    }
    
    /**
     * @param execute     callback for event
     * @param canExecute0 if this function return true the action execute would be invoked! otherwise would't invoked!
     */
    public ReplyCommand0(Consumer0 execute, Function0<Boolean> canExecute0) {
        this.execute0 = execute;
        this.canExecute0 = canExecute0;
    }
    
    public void execute() {
        if (execute0 != null && canExecute0()) {
            try {
                execute0.accept();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    private boolean canExecute0() {
        if (canExecute0 == null) {
            return true;
        }
        try {
            return canExecute0.apply();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
