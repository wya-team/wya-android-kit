package com.wya.uikit.banner;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wya.uikit.R;
import com.wya.uikit.paginationview.WYAPaginationDot;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : banner
 */
public class WYABanner<T> extends RelativeLayout {
    private static final int BANNER_ALL = 100;
    private static String TAG = "WYABanner";
    private Context mContext;
    private ViewPager mViewPager;
    private WYAPaginationDot mDot;
    private int num;
    private List<T> mData = new ArrayList<>();
    private int mBannerPosition;
    private long updateTime = 2000;
    private UpdateRun mUpdateRun;
    private boolean auto = true;
    private int itemId = R.layout.banner_default_item;
    private BaseBannerAdapter<T> mBaseBannerAdapter;
    
    public WYABanner(Context context) {
        this(context, null);
    }
    
    public WYABanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public WYABanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setGravity(Gravity.BOTTOM);
        init();
    }
    
    /**
     * init dotView
     */
    private void init() {
        mUpdateRun = new UpdateRun(this);
    
        mDot = new WYAPaginationDot(mContext, null);
        mDot.setPadding(0, 0, 0, (int) dp2px(5));
    
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    
        addView(mDot, -1, layoutParams);
        initViewpager();
    }
    
    /**
     * auto player circle
     */
    private void startAutoPlay() {
        cancelAutoPlay();
        if (auto) {
            postDelayed(mUpdateRun, updateTime);
        }
    }
    
    /**
     * auto play and change viewPager's item
     */
    private void autoChange() {
    
        mBannerPosition = mBannerPosition + 1;
        Log.i(TAG, "autoChange: " + mBannerPosition);
        if (mBannerPosition != BANNER_ALL - 1) {
            mViewPager.setCurrentItem(mBannerPosition);
        } else {
            mViewPager.setCurrentItem(num - 1, false);
        }
    }
    
    /**
     * intercept the event to start and stop
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (auto) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cancelAutoPlay();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    startAutoPlay();
                    break;
                default:
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    
    /**
     * init viewpager
     */
    private void initViewpager() {
        mViewPager = new ViewPager(mContext);
        mViewPager.setOffscreenPageLimit(2);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT);
        addView(mViewPager, 0, layoutParams);
        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter();
        mViewPager.setAdapter(bannerPagerAdapter);
        mViewPager.addOnPageChangeListener(bannerPagerAdapter);
    
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = widthSize / 2;
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightSize, heightMode));
    }
    
    /**
     * dp2px
     *
     * @param dp dp
     * @return px
     */
    
    private float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources()
                .getDisplayMetrics());
    }
    
    public WYABanner setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
        return this;
    }
    
    /**
     * cancel auto play
     */
    public void cancelAutoPlay() {
        if (mUpdateRun != null) {
            removeCallbacks(mUpdateRun);
        }
    }
    
    public WYABanner setDotVisible(boolean isVisible) {
        mDot.setVisibility(isVisible ? VISIBLE : INVISIBLE);
        return this;
    }
    
    public WYABanner autoPlay(boolean isAuto) {
        auto = isAuto;
        return this;
    }
    
    public WYABanner setDotBackgroundResource(@DrawableRes int source) {
        mDot.setDotBackgroundResource(source);
        return this;
    }
    
    public WYABanner setDotDark() {
        mDot.setDarkDefault();
        return this;
    }
    
    /**
     * scale style
     *
     * @param pageMargin  viewpager item margin
     * @param leftMargin  viewpager marginLeft
     * @param rightMargin viewpager marginRight
     * @return banner
     */
    public WYABanner setScale(int pageMargin, int leftMargin, int rightMargin) {
    
        setClipChildren(false);
        mViewPager.setClipChildren(false);
        mViewPager.setPageMargin(pageMargin);
        mViewPager.setClipChildren(false);
        mViewPager.setPageTransformer(true, new ScaleInTransformer());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.MATCH_PARENT);
        layoutParams.leftMargin = leftMargin;
        layoutParams.rightMargin = rightMargin;
        mViewPager.setLayoutParams(layoutParams);
        invalidate();
        return this;
    }
    
    public void setAdapter(BaseBannerAdapter<T> baseBannerAdapter) {
        mBaseBannerAdapter = baseBannerAdapter;
        mData = mBaseBannerAdapter.getData();
        itemId = mBaseBannerAdapter.getLayoutId();
        num = mData.size();
        mDot.setPointNumber(num);
        
        startAutoPlay();
    }
    
    private class UpdateRun implements Runnable {
        private WeakReference<WYABanner> mBannerWeakReference;
        
        public UpdateRun(WYABanner wyaBanner) {
            mBannerWeakReference = new WeakReference<>(wyaBanner);
        }
        
        @Override
        public void run() {
            WYABanner wyaBanner = mBannerWeakReference.get();
            
            if (wyaBanner != null) {
                autoChange();
                wyaBanner.startAutoPlay();
            }
        }
    }
    
    /**
     * banner adapter
     */
    private class BannerPagerAdapter extends PagerAdapter implements ViewPager
            .OnPageChangeListener {
        
        @Override
        public int getCount() {
            return BANNER_ALL;
        }
        
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
        
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            position %= num;
            View view = LayoutInflater.from(mContext).inflate(itemId, null);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(view, layoutParams);
            mBaseBannerAdapter.convert(view, position, mData.get(position));
            return view;
        }
        
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object
                object) {
            container.removeView((View) object);
        }
        
        @Override
        public void finishUpdate(@NonNull ViewGroup container) {
            int position = mViewPager.getCurrentItem();
            Log.i(TAG, "before: " + position);
            if (position == 0) {
                position = num;
                mViewPager.setCurrentItem(position, false);
            } else if (position == BANNER_ALL - 1) {
                position = num - 1;
                mViewPager.setCurrentItem(position, false);
            }
            Log.i(TAG, "after: " + position);
        }
        
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        
        }
        
        @Override
        public void onPageSelected(int position) {
            Log.i(TAG, "onPageSelected: " + position);
            mBannerPosition = position;
            position %= num;
            mDot.setCurrentItem(position);
        }
        
        @Override
        public void onPageScrollStateChanged(int i) {
        
        }
    }
    
}
