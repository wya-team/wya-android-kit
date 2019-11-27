package com.weiyian.android.fuzzy;

import java.util.Comparator;

/**
 * @author :
 */
public class LettersComparator<DATA extends IAZItem> implements Comparator<DATA> {
    
    @Override
    public int compare(DATA o1, DATA o2) {
        if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
            return 1;
        } else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
            return -1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
    
}
