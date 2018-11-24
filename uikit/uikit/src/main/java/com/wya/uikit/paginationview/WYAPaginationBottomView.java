package com.wya.uikit.paginationview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.uikit.R;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/23
 * desc   : pageView
 * version: 1.0
 */
public class WYAPaginationBottomView extends FrameLayout implements View.OnClickListener {
	private Context mContext;
	private Button mButtonLeft;
	private Button mButtonRight;
	private TextView mPageTextCurrent;
	private TextView mPageTextAll;
	private EditText mChangeEdit;
	private int allNum;
	private int currentPage;
	private String mLeftText;
	private String mRightText;
	private float mBtnWidth;
	private float mBtnHeight;
	private float mTextSize;
	private int mTextColor;
	private static final String TAG = "WYAPaginationBottomView";
	private int mSelectColor;
	private OnPageButtonClickListener mOnPageButtonClickListener;


	public WYAPaginationBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WYAPaginationBottomView(@NonNull Context context, @Nullable AttributeSet attrs, int
			defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable
				.WYAPaginationBottomView);
		mLeftText = typedArray.getString(R.styleable.WYAPaginationBottomView_leftText);
		mRightText = typedArray.getString(R.styleable.WYAPaginationBottomView_rightText);
		mBtnWidth = typedArray.getDimension(R.styleable.WYAPaginationBottomView_buttonWidth,
				dp2px(50));
		mBtnHeight = typedArray.getDimension(R.styleable
				.WYAPaginationBottomView_buttonHeight, dp2px(30));
		mTextSize = typedArray.getDimension(R.styleable.WYAPaginationBottomView_textSize,
				sp2px(13));
		mTextColor = typedArray.getColor(R.styleable.WYAPaginationBottomView_textColor, Color
				.BLACK);
		mSelectColor = typedArray.getColor(R.styleable.WYAPaginationBottomView_selectPageTextColor,
				Color
				.BLACK);

		typedArray.recycle();

		init();
	}


	private void init() {
		allNum = 10;
		currentPage = 1;

		View view = LayoutInflater.from(mContext).inflate(R.layout.pagination_bottom_view, null);
		addView(view);

		mButtonLeft = view.findViewById(R.id.pagination_btn_left);
		mButtonRight = view.findViewById(R.id.pagination_btn_right);
		mPageTextCurrent = view.findViewById(R.id.pagination_show_page_current);
		mPageTextAll = view.findViewById(R.id.pagination_show_page_all);
//		mChangeEdit = view.findViewById(R.id.pagination_change_page);
		mButtonLeft.setOnClickListener(this);
		mButtonRight.setOnClickListener(this);

		mButtonLeft.setText(TextUtils.isEmpty(mLeftText) ? "上一页" : mLeftText);
		mButtonRight.setText(TextUtils.isEmpty(mRightText) ? "下一页" : mRightText);
		mButtonLeft.setTextColor(mTextColor);
		mButtonRight.setTextColor(mTextColor);
		mPageTextCurrent.setTextColor(mSelectColor);
		mPageTextCurrent.setTextSize(px2sp(mTextSize));
		mPageTextAll.setTextColor(mTextColor);
		mPageTextAll.setTextSize(px2sp(mTextSize));
		mButtonLeft.setTextSize(px2sp(mTextSize));
		mButtonRight.setTextSize(px2sp(mTextSize));

		ViewGroup.LayoutParams layoutParams = mButtonLeft.getLayoutParams();
		layoutParams.width = (int) mBtnWidth;
		layoutParams.height = (int) mBtnHeight;
		mButtonLeft.setLayoutParams(layoutParams);
		mButtonRight.setLayoutParams(layoutParams);
	}

	/**
	 * set page all numbers
	 *
	 * @param num page numbers
	 */
	public void setAllNum(int num) {
		allNum = num;
		currentPage = 1;
		setCurrentPage();
	}

	@SuppressLint("DefaultLocale")
	private void setCurrentPage() {
		mPageTextCurrent.setText(String.format("%d", currentPage));
		mPageTextAll.setText(String.format("%d", allNum));
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.pagination_btn_left) {
			if (mOnPageButtonClickListener != null) {
				mOnPageButtonClickListener.onClick(1);
			}
			if (currentPage == 1) {
				Toast.makeText(mContext, "这是第一页，已经没有再前面一页了", Toast.LENGTH_SHORT).show();
			} else {
				currentPage--;
				setCurrentPage();
			}
		} else if (i == R.id.pagination_btn_right) {
			if (mOnPageButtonClickListener != null) {
				mOnPageButtonClickListener.onClick(2);
			}
			if (currentPage == allNum) {
				Toast.makeText(mContext, "这是最后一页，已经没有再下一页了", Toast.LENGTH_SHORT).show();
			} else {
				currentPage++;
				setCurrentPage();
			}

		}
	}


	/**
	 * remeasure group and child
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int childHeight = (int) (mBtnHeight + dp2px(20));
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		switch (heightMode) {
			case MeasureSpec.AT_MOST:
				heightSize = childHeight;
				break;
			case MeasureSpec.EXACTLY:
				if (heightSize < childHeight) {
					heightSize = childHeight;
				}
				break;
		}

		setMeasuredDimension(widthSize, heightSize);
		measureChildren(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightSize, heightMode));
	}


	/**
	 * dp2px
	 *
	 * @param dp
	 * @return
	 */

	public float dp2px(int dp) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources()
				.getDisplayMetrics());
	}

	/**
	 * px2sp
	 *
	 * @param pxValue
	 * @return
	 */
	private float px2sp(float pxValue) {
		return pxValue / getResources().getDisplayMetrics().scaledDensity + 0.5f;
	}

	private float sp2px(float spValue) {
		final float fontScale = getResources().getDisplayMetrics().scaledDensity;
		return spValue * fontScale + 0.5f;
	}

	public void setOnPageButtonClickListener(OnPageButtonClickListener onPageButtonClickListener) {
		mOnPageButtonClickListener = onPageButtonClickListener;
	}

	/**
	 * pageButton click Interface
	 *
	 */
	public interface OnPageButtonClickListener{
		/**
		 * click callback
		 * @param type 1:page up 2:page down
		 */
		void onClick(int type);
	}
}
