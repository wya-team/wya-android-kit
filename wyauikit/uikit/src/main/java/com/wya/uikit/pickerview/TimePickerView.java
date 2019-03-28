package com.wya.uikit.pickerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.wya.uikit.pickerview.wheelview.listener.OnItemSelectedListener;
import com.wya.uikit.pickerview.wheelview.view.WheelView;

import java.util.Calendar;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description :
 */
public class TimePickerView extends LinearLayout {
    private static final int LMP = LayoutParams.MATCH_PARENT;
    private static final int LWC = LayoutParams.WRAP_CONTENT;
    private static final String TAG = "TimePickerView";
    private WheelView yearWheel;
    private WheelView monthWheel;
    private WheelView dayWheel;
    private WheelView hourWheel;
    private WheelView minWheel;
    private WheelView secWheel;
    private OnItemSelectedListener yearListener;
    private OnItemSelectedListener monthListener;
    private OnItemSelectedListener dayListener;
    private OnItemSelectedListener hourListener;
    private OnItemSelectedListener minListener;
    private OnItemSelectedListener secListener;
    private Context mContext;
    
    private Calendar startDate;
    private Calendar endDate;
    private Calendar selectDate;
    private boolean[] type = new boolean[]{true, true, true, true, true, true};
    private int yearIndex;
    private int monthIndex;
    private int dayIndex;
    private int hourIndex;
    private int minIndex;
    private int secIndex;
    private int hourSpace = 1;
    private int minuteSpace = 1;
    private int secondSpace = 1;
    private int mStartYear;
    private int mStartMonth;
    private int mStartDay;
    private int mStartHour;
    private int mStartMin;
    private int mStartSec;
    private int mEndYear;
    private int mEndMonth;
    private int mEndDay;
    private int mEndHour;
    private int mEndMin;
    private int mEndSec;
    private int mSelectYear;
    private int mSelectMonth;
    private int mSelectDay;
    private int mSelectHour;
    private int mSelectMin;
    private int mSelectSec;
    private boolean isShowType = false;
    
    public TimePickerView(Context context) {
        this(context, null);
    }
    
    public TimePickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public TimePickerView(Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOrientation(HORIZONTAL);
        initView();
        setWheelViewVisible();
    }
    
    private void initView() {
        yearWheel = new WheelView(mContext);
        LayoutParams layoutParams1 = new LayoutParams(LWC, LMP);
        layoutParams1.weight = 1;
        monthWheel = new WheelView(mContext);
        LayoutParams layoutParams2 = new LayoutParams(LWC, LMP);
        layoutParams2.weight = 1;
        dayWheel = new WheelView(mContext);
        LayoutParams layoutParams3 = new LayoutParams(LWC, LMP);
        layoutParams3.weight = 1;
        hourWheel = new WheelView(mContext);
        LayoutParams layoutParams4 = new LayoutParams(LWC, LMP);
        layoutParams4.weight = 1;
        minWheel = new WheelView(mContext);
        LayoutParams layoutParams5 = new LayoutParams(LWC, LMP);
        layoutParams5.weight = 1;
        secWheel = new WheelView(mContext);
        LayoutParams layoutParams6 = new LayoutParams(LWC, LMP);
        layoutParams6.weight = 1;
        
        yearWheel.setCyclic(false);
        monthWheel.setCyclic(false);
        dayWheel.setCyclic(false);
        hourWheel.setCyclic(false);
        minWheel.setCyclic(false);
        secWheel.setCyclic(false);
        
        addView(yearWheel, layoutParams1);
        addView(monthWheel, layoutParams2);
        addView(dayWheel, layoutParams3);
        addView(hourWheel, layoutParams4);
        addView(minWheel, layoutParams5);
        addView(secWheel, layoutParams6);
        
        selectDate = Calendar.getInstance();
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        
        startDate.set(startDate.get(Calendar.YEAR) - 100, 1, 1);
        endDate.set(endDate.get(Calendar.YEAR) + 100, 12, 31);
        setWheelViewAdapter();
    }
    
    public void setRangeTime(Calendar start, Calendar end) {
        startDate = start;
        endDate = end;
        setWheelViewAdapter();
    }
    
    public void setSelectDate(Calendar selectDate) {
        this.selectDate = selectDate;
    }
    
    private void setWheelViewAdapter() {
        if (startDate.getTimeInMillis() > endDate.getTimeInMillis()) {
            throw new IllegalArgumentException("startDate cannot bigger than endDate");
        }
        if (selectDate.getTimeInMillis() > endDate.getTimeInMillis()) {
            throw new IllegalArgumentException("selectDate cannot bigger than endDate");
    
        }
        if (selectDate.getTimeInMillis() < startDate.getTimeInMillis()) {
            throw new IllegalArgumentException("selectDate cannot smaller than startDate");
        }
        mStartYear = startDate.get(Calendar.YEAR);
        mStartMonth = startDate.get(Calendar.MONTH) + 1;
        mStartDay = startDate.get(Calendar.DAY_OF_MONTH);
        mStartHour = startDate.get(Calendar.HOUR);
        mStartMin = startDate.get(Calendar.MINUTE);
        mStartSec = startDate.get(Calendar.SECOND);
        
        mEndYear = endDate.get(Calendar.YEAR);
        mEndMonth = endDate.get(Calendar.MONTH) + 1;
        mEndDay = endDate.get(Calendar.DAY_OF_MONTH);
        mEndHour = endDate.get(Calendar.HOUR);
        mEndMin = endDate.get(Calendar.MINUTE);
        mEndSec = endDate.get(Calendar.SECOND);
        
        mSelectYear = selectDate.get(Calendar.YEAR);
        mSelectMonth = selectDate.get(Calendar.MONTH) + 1;
        mSelectDay = selectDate.get(Calendar.DAY_OF_MONTH);
        mSelectHour = selectDate.get(Calendar.HOUR);
        mSelectMin = selectDate.get(Calendar.MINUTE);
        mSelectSec = selectDate.get(Calendar.SECOND);
        
        yearWheel.setAdapter(new TimePickerAdapter(mStartYear, mEndYear, 1, isShowType ? "年" : ""));
        yearIndex = mSelectYear - mStartYear;
        yearWheel.setCurrentItem(yearIndex);
        
        int monthOfYearStart;
        int monthOfYearEnd;
        if (mStartYear == mEndYear) {
            monthOfYearStart = mStartMonth;
            monthOfYearEnd = mEndMonth;
            monthIndex = mSelectMonth - mStartMonth;
        } else if (mStartYear == mSelectYear) {
            monthOfYearStart = mStartMonth;
            monthOfYearEnd = 12;
            monthIndex = mSelectMonth - mStartMonth;
        } else if (mEndYear == mSelectYear) {
            monthOfYearStart = 1;
            monthOfYearEnd = mEndMonth;
            monthIndex = mSelectMonth - 1;
        } else {
            monthOfYearStart = 1;
            monthOfYearEnd = 12;
            monthIndex = mSelectMonth - 1;
        }
        monthWheel.setAdapter(new TimePickerAdapter(monthOfYearStart, monthOfYearEnd, 1,
                isShowType ? "月" : ""));
        monthWheel.setCurrentItem(monthIndex);
        
        int dayOfMonthStart;
        int dayOfMonthEnd;
        if (mStartYear == mSelectYear && mSelectYear == mEndYear) {
            if (mStartMonth == mEndMonth) {
                dayOfMonthStart = mStartDay;
                dayOfMonthEnd = mEndDay;
            } else if (mStartMonth == mSelectMonth) {
                dayOfMonthStart = mStartDay;
                dayOfMonthEnd = getDayOfMonth(mSelectMonth, mSelectYear);
            } else if (mEndMonth == mSelectMonth) {
                dayOfMonthStart = 1;
                dayOfMonthEnd = mEndDay;
            } else {
                dayOfMonthStart = 1;
                dayOfMonthEnd = getDayOfMonth(mSelectMonth, mSelectYear);
            }
        } else if (mStartYear == mSelectYear) {
            dayOfMonthEnd = getDayOfMonth(mSelectMonth, mSelectYear);
            if (mStartMonth == mSelectMonth) {
                dayOfMonthStart = mStartDay;
            } else {
                dayOfMonthStart = 1;
            }
        } else if (mSelectYear == mEndYear) {
            dayOfMonthStart = 1;
            if (mEndMonth == mSelectMonth) {
                dayOfMonthEnd = mEndDay;
            } else {
                dayOfMonthEnd = getDayOfMonth(mSelectMonth, mSelectYear);
            }
        } else {
            dayOfMonthStart = 1;
            dayOfMonthEnd = getDayOfMonth(mSelectMonth, mSelectYear);
        }
        
        dayWheel.setAdapter(new TimePickerAdapter(dayOfMonthStart, dayOfMonthEnd, 1,
                isShowType ? "日" : ""));
        dayIndex = mSelectDay - dayOfMonthStart;
        dayWheel.setCurrentItem(dayIndex);
        
        yearListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                yearIndex = index;
                int startMon;
                int endMon;
                if (index == 0) {
                    startMon = mStartMonth;
                    if (mStartYear == mEndYear) {
                        endMon = mEndMonth;
                    } else {
                        endMon = 12;
                    }
                } else if (mEndYear - mStartYear == index) {
                    endMon = mEndMonth;
                    if (mStartYear == mEndYear) {
                        startMon = mStartMonth;
                    } else {
                        startMon = 1;
                    }
                } else {
                    startMon = 1;
                    endMon = 12;
                }
    
                monthWheel.setAdapter(new TimePickerAdapter(startMon, endMon, 1,
                        isShowType ? "月" : ""));
                int sizeMon = Math.min((endMon - startMon), monthIndex);
                monthWheel.setCurrentItem(sizeMon);
                monthListener.onItemSelected(sizeMon);
    
            }
        };
        
        monthListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                monthIndex = index;
                int daystarts;
                int dayends;
                if (yearIndex == 0 && monthIndex == 0) {
                    daystarts = mStartDay;
                    if (mStartYear == mEndYear && mStartMonth == mEndMonth) {
                        dayends = mEndDay;
                    } else {
                        dayends = getDayOfMonth(mStartMonth, mStartYear);
                    }
                } else if (mEndYear - mStartYear == yearIndex && monthIndex + 1 == mEndMonth) {
                    dayends = mEndDay;
                    if (mStartYear == mEndYear && mStartMonth == mEndMonth) {
                        daystarts = mStartDay;
                    } else {
                        daystarts = 1;
                    }
                } else {
                    daystarts = 1;
                    dayends = getDayOfMonth(monthIndex + 1, yearIndex + mStartYear);
                    Log.i(TAG, "onItemSelected: ");
                }
                Log.i(TAG, "onItemSelected: " + monthIndex + " " + (yearIndex + mStartYear));
                dayWheel.setAdapter(new TimePickerAdapter(daystarts, dayends, 1, isShowType ? "日"
                        : ""));
                int sizeDays = dayends - daystarts;
                dayWheel.setCurrentItem(dayIndex > sizeDays ? sizeDays : dayIndex);
                dayListener.onItemSelected(dayIndex > sizeDays ? sizeDays : dayIndex);
            }
        };
        
        dayListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                dayIndex = index;
            }
        };
        
        yearWheel.setOnItemSelectedListener(yearListener);
        monthWheel.setOnItemSelectedListener(monthListener);
        dayWheel.setOnItemSelectedListener(dayListener);
        
        setHourMinSecAdapter();
        
    }
    
    private void setHourMinSecAdapter() {
        hourWheel.setAdapter(new TimePickerAdapter(0, 23, hourSpace, isShowType ? "时" : ""));
        hourIndex = selectDate.get(Calendar.HOUR_OF_DAY) / hourSpace;
        hourWheel.setCurrentItem(hourIndex);
        
        minWheel.setAdapter(new TimePickerAdapter(0, 59, minuteSpace, isShowType ? "分" : ""));
        minIndex = selectDate.get(Calendar.MINUTE) / minuteSpace;
        minWheel.setCurrentItem(minIndex);
        
        secWheel.setAdapter(new TimePickerAdapter(0, 59, secondSpace, isShowType ? "秒" : ""));
        secIndex = selectDate.get(Calendar.SECOND) / secondSpace;
        secWheel.setCurrentItem(secIndex);
        
        hourListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                hourIndex = index;
            }
        };
        minListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                minIndex = index;
            }
        };
        secListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                secIndex = index;
            }
        };
        hourWheel.setOnItemSelectedListener(hourListener);
        minWheel.setOnItemSelectedListener(minListener);
        secWheel.setOnItemSelectedListener(secListener);
    }
    
    public TimePickerView setType(boolean[] type) {
        this.type = type;
        setWheelViewVisible();
        return this;
    }
    
    private void setWheelViewVisible() {
        yearWheel.setVisibility(type[0] ? VISIBLE : GONE);
        monthWheel.setVisibility(type[1] ? VISIBLE : GONE);
        dayWheel.setVisibility(type[2] ? VISIBLE : GONE);
        hourWheel.setVisibility(type[3] ? VISIBLE : GONE);
        minWheel.setVisibility(type[4] ? VISIBLE : GONE);
        secWheel.setVisibility(type[5] ? VISIBLE : GONE);
    }
    
    private int getDayOfMonth(int month, int year) {
        int day = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = 30;
                break;
            case 2:
                if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)) {
                    day = 29;
                } else {
                    day = 28;
                }
                break;
            default:
                break;
        }
        return day;
    }
    
    public void setHourSpace(int space) {
        hourSpace = space;
        setHourMinSecAdapter();
    }
    
    public void setMinuteSpace(int space) {
        minuteSpace = space;
        setHourMinSecAdapter();
    }
    
    public void setSecondSpace(int space) {
        secondSpace = space;
        setHourMinSecAdapter();
    }
    
    public int getYear() {
       return  yearWheel.getCurrentItem() + mStartYear;
    }
    
    public int getMonth() {
        if (yearWheel.getCurrentItem() == 0) {
            return monthWheel.getCurrentItem() + mStartMonth;
        } else {
            return monthWheel.getCurrentItem() + 1;
        }
    }
    
    public int getDay() {
        if (yearWheel.getCurrentItem() == 0 && monthWheel.getCurrentItem() == 0) {
            return dayWheel.getCurrentItem() + mStartDay;
        } else {
            return dayWheel.getCurrentItem()+1;
        }
    }
    
    public int getHour() {
        return hourWheel.getCurrentItem()*hourSpace;
    }
    
    public int getMinute() {
        return minWheel.getCurrentItem()*minuteSpace;
    }
    
    public int getSecond() {
        return secWheel.getCurrentItem()*secondSpace;
    }
    
    public void setDividerColor(int color) {
        yearWheel.setDividerColor(color);
        monthWheel.setDividerColor(color);
        dayWheel.setDividerColor(color);
        hourWheel.setDividerColor(color);
        minWheel.setDividerColor(color);
        secWheel.setDividerColor(color);
    }
    
    public void setOuterTextColor(int color) {
        yearWheel.setTextColorOut(color);
        monthWheel.setTextColorOut(color);
        dayWheel.setTextColorOut(color);
        hourWheel.setTextColorOut(color);
        minWheel.setTextColorOut(color);
        secWheel.setTextColorOut(color);
    }
    
    public void setCenterTextColor(int color) {
        yearWheel.setTextColorCenter(color);
        monthWheel.setTextColorCenter(color);
        dayWheel.setTextColorCenter(color);
        hourWheel.setTextColorCenter(color);
        minWheel.setTextColorCenter(color);
        secWheel.setTextColorCenter(color);
    }
    
    public void setTextSize(float size) {
        yearWheel.setTextSize(size);
        monthWheel.setTextSize(size);
        dayWheel.setTextSize(size);
        hourWheel.setTextSize(size);
        minWheel.setTextSize(size);
        secWheel.setTextSize(size);
    }
    
    public boolean isShowType() {
        return isShowType;
    }
    
    public void setShowType(boolean showType) {
        isShowType = showType;
        setWheelViewAdapter();
    }

    public void setLineSpace(float lineSpace) {
        yearWheel.setLineSpacingMultiplier(lineSpace);
        monthWheel.setLineSpacingMultiplier(lineSpace);
        dayWheel.setLineSpacingMultiplier(lineSpace);
        hourWheel.setLineSpacingMultiplier(lineSpace);
        minWheel.setLineSpacingMultiplier(lineSpace);
        secWheel.setLineSpacingMultiplier(lineSpace);
    }

    public void setItemsVisible(int items){
        yearWheel.setItemsVisible(items);
        monthWheel.setItemsVisible(items);
        dayWheel.setItemsVisible(items);
        hourWheel.setItemsVisible(items);
        minWheel.setItemsVisible(items);
        secWheel.setItemsVisible(items);
    }

}
