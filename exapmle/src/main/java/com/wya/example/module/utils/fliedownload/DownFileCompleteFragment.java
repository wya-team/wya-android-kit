package com.wya.example.module.utils.fliedownload;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arialyy.aria.core.download.DownloadEntity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wya.example.R;
import com.wya.uikit.dialog.WYACustomDialog;
import com.wya.utils.utils.FileManagerUtil;
import com.wya.utils.utils.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.wya.example.module.utils.fliedownload.FileDownloadExampleActivity.FILE_IMG_DIR;

/**
 * @author : XuDonglin
 * @time : 2019-01-09
 * @Description : 下载完成
 */
public class DownFileCompleteFragment extends Fragment {


    @BindView(R.id.down_file_recycler)
    RecyclerView mDownFileRecycler;
    Unbinder unbinder;
    private View mView;
    private BaseQuickAdapter<DownloadEntity, BaseViewHolder> mAdapter;
    private List<DownloadEntity> mDownList = new ArrayList<>();
    private FileManagerUtil mFileManagerUtil = new FileManagerUtil();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_down_file_complete, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mDownList.clear();
            List<DownloadEntity> allCompleteTask = mFileManagerUtil.getDownloadReceiver()
                    .getAllCompleteTask();
            if (allCompleteTask != null) {
                mDownList.addAll(allCompleteTask);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

        initRecycler();
    }

    private void initRecycler() {
        mAdapter = new BaseQuickAdapter<DownloadEntity, BaseViewHolder>(R.layout
                .has_down_file_item, mDownList) {
            @Override
            protected void convert(BaseViewHolder helper, DownloadEntity item) {
                ImageView imageView = helper.getView(R.id.down_file_image);
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.color.dddddd)
                        .error(R.color.dddddd)
                        .centerCrop();
                File file = new File(FILE_IMG_DIR + "/" + "IMG_" + StringUtil.getSign(item.getKey
                        ()) +
                        ".jpg");
                Glide.with(getActivity()).load(file).apply(requestOptions).into(imageView);

                helper.setText(R.id.file_title, item.getFileName())
                        .setText(R.id.file_capacity, item.getConvertFileSize());
            }
        };
        mDownFileRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mDownFileRecycler.setAdapter(mAdapter);

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                WYACustomDialog wyaCustomDialog = new WYACustomDialog.Builder(getActivity())
                        .title("提示")
                        .message("是否删除视频")
                        .cancelText("取消")
                        .confirmText("删除").build();
                wyaCustomDialog.show();

                wyaCustomDialog.setNoClickListener(new WYACustomDialog.NoClickListener() {
                    @Override
                    public void onNoClick() {
                        wyaCustomDialog.dismiss();
                    }
                });

                wyaCustomDialog.setYesClickListener(new WYACustomDialog.YesClickListener() {
                    @Override
                    public void onYesClick() {
                        wyaCustomDialog.dismiss();
                        mFileManagerUtil.getDownloadReceiver().load(mDownList.get(position)
                                .getKey()).cancel(true);
                        mDownList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mFileManagerUtil.unRegister();
    }


}
