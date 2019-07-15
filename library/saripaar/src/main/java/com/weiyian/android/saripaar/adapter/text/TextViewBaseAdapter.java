package com.weiyian.android.saripaar.adapter.text;

import android.view.View;
import android.widget.TextView;

import com.weiyian.android.saripaar.adapter.ViewDataAdapter;

import java.lang.annotation.Annotation;

/**
 * A base class that implements the {@link #containsOptionalValue(View, Annotation)} method for concrete
 * {@link TextView} adapters.
 *
 * @author Ragunath Jawahar {@literal <rj@mobsandgeeks.com>}
 * @since 2.1.0
 */
abstract class TextViewBaseAdapter<DATA> implements ViewDataAdapter<TextView, DATA> {
    
    @Override
    public <T extends Annotation> boolean containsOptionalValue(final TextView textView, final T annotation) {
        return "".equals(textView.getText().toString());
    }
    
}
