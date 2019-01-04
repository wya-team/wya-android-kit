package com.wya.example.module.uikit.customitems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wya.example.R;
import com.wya.uikit.button.WYAButton;

import java.util.List;

 /**
  * @date: 2018/11/30 9:31
  * @author: Chunjiang Mao
  * @classname: CustomItemsAdapter
  * @describe: CustomItemsAdapter
  */

public class CustomItemsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
   private Context context;

   public CustomItemsAdapter(Context context, int layoutResId, @Nullable List<String> data) {
       super(layoutResId, data);
       this.context = context;
   }

   @SuppressLint("NewApi")
   @Override
   protected void convert(BaseViewHolder helper, String item) {
       ((WYAButton)helper.getView(R.id.wya_button)).setText(item);
       helper.addOnClickListener(R.id.wya_button);
//       ((WYAButton)helper.getView(R.id.wya_button)).setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//
//           }
//       });
   }
}
