package com.weiyian.android.saripaar.adapter.text;

import android.widget.TextView;

import com.weiyian.android.saripaar.exception.ConversionException;

/**
 * Adapter parses and returns a {@link java.lang.Double} from {@link android.widget.TextView}s or
 * its subclasses like {@link android.widget.EditText}s.
 *
 * @author Ragunath Jawahar {@literal <rj@mobsandgeeks.com>}
 * @since 2.0
 */
public class TextViewDoubleAdapter extends TextViewBaseAdapter<Double> {
    
    private static final String REGEX_DECIMAL = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
    
    @Override
    public Double getData(final TextView textView) throws ConversionException {
        String doubleString = textView.getText().toString().trim();
        if (!doubleString.matches(REGEX_DECIMAL)) {
            String message = String.format("Expected a floating point number, but was %s",
                    doubleString);
            throw new ConversionException(message);
        }
        
        return Double.parseDouble(doubleString);
    }
    
}
