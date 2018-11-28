package com.wya.uikit.choicemenu;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.wya.uikit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/11/24
 * desc   : ChoiceMenu
 * version: 1.0
 */
public abstract class ChoiceMenu<T> extends PopupWindow {
	private Context mContext;
	private RecyclerView mRecyclerFirst, mRecyclerSecond;
	private List<T> data1;
	private List<List<T>> data2;
	private List<T> data2Show = new ArrayList<>();
	private int firstId, secondId;
	private ChoiceMenuAdapter<T> mAdapter;
	private ChoiceMenuAdapter<T> mAdapterSecond;
	private String TAG = "ChoiceMenu";
	private int selectPosition;


	public ChoiceMenu(Context context, List<T> list1, List<List<T>> list2, @LayoutRes int firstId,
					  @LayoutRes int secondId) {
		super(context);
		mContext = context;
		this.data1 = list1;
		this.data2 = list2;
		this.firstId = firstId;
		this.secondId = secondId;
		init();
	}

	public ChoiceMenu(Context context, List<T> list1, @LayoutRes int firstId) {
		super(context);
		mContext = context;
		this.data1 = list1;
		this.firstId = firstId;
		init();
	}

	/**
	 * init ChoiceMenu
	 */
	private void init() {
		setWidth(mContext.getResources().getDisplayMetrics().widthPixels);
		setHeight(mContext.getResources().getDisplayMetrics().heightPixels / 2);
		View view = LayoutInflater.from(mContext).inflate(R.layout.choice_layout, null);
		setContentView(view);
		mRecyclerFirst = view.findViewById(R.id.first_recycler);
		mRecyclerSecond = view.findViewById(R.id.second_recycler);
		initRecycler();
		setOutsideTouchable(true);
	}

	/**
	 * init recyclerView
	 * the first adapter has do something that you can implemented in setValueFirst
	 */
	private void initRecycler() {
		mRecyclerFirst.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
				.VERTICAL, false));
		mRecyclerSecond.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
				.VERTICAL, false));
		mAdapter = new ChoiceMenuAdapter<T>(data1, firstId) {
			@Override
			protected void convert(final ChoiceMenuViewHolder viewHolder, T item) {
				setValueFirst(viewHolder, item);
				viewHolder.itemView.setSelected(viewHolder.getLayoutPosition() == selectPosition);
				viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						selectPosition = viewHolder.getLayoutPosition();
						mAdapter.notifyDataSetChanged();
						if (data2 != null) {
							data2Show.clear();
							data2Show.addAll(data2.get(selectPosition));
							mAdapterSecond.notifyDataSetChanged();
						}
					}
				});
			}
		};
		mRecyclerFirst.setAdapter(mAdapter);
		if (data2 != null && secondId != 0) {
			data2Show.addAll(data2.get(0));
			mAdapterSecond = new ChoiceMenuAdapter<T>(data2Show, secondId) {
				@Override
				protected void convert(ChoiceMenuViewHolder viewHolder, T item) {
					setValueSecond(viewHolder, item);
				}
			};
			mRecyclerSecond.setAdapter(mAdapterSecond);
		} else {
			mRecyclerSecond.setVisibility(View.GONE);
		}
	}

	/**
	 * first recyclerView convert value
	 * @param helper viewHolder
	 * @param item value
	 */
	public abstract void setValueFirst(ChoiceMenuViewHolder helper, T item);

	/**
	 * second recyclerView convert value
	 * @param helper viewHolder
	 * @param item value
	 */
	public abstract void setValueSecond(ChoiceMenuViewHolder helper, T item);


	/**
	 * first recyclerView add lines
	 *
	 * @param colorId color id
	 * @param height  line height px
	 */
	public void addLine(@ColorRes int colorId, int height) {
		mRecyclerFirst.addItemDecoration(new ItemDecoration(mContext, colorId, height));
	}

	/**
	 * second recyclerView add lines
	 *
	 * @param colorId color id
	 * @param height  line height px
	 */
	public void addSecondLine(@ColorRes int colorId, int height) {
		mRecyclerSecond.addItemDecoration(new ItemDecoration(mContext, colorId, height));
	}

	/**
	 * update data
	 */
	public void notifyAdapterData() {
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
		}
		if (mAdapterSecond != null) {
			mAdapterSecond.notifyDataSetChanged();
		}
	}

}
