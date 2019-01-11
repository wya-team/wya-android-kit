package com.wya.example.module.utils.fliedownload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadTask;
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

import static com.wya.example.module.utils.fliedownload.FlieConfig.FILE_IMG_DIR;
import static com.wya.utils.utils.FileManagerUtil.TASK_CANCEL;

/**
 * @author : XuDonglin
 * @time : 2019-01-09
 * @Description : 下载完成
 */
public class DownFileCompleteFragment extends Fragment implements IManagerInterface {
    
    @BindView(R.id.down_file_recycler)
    RecyclerView mDownFileRecycler;
    Unbinder unbinder;
    private View mView;
    private BaseQuickAdapter<DownloadEntity, BaseViewHolder> mAdapter;
    private List<DownloadEntity> mDownList = new ArrayList<>();
    private FileManagerUtil mFileManagerUtil = new FileManagerUtil();
    private IRomUpdateCallback mCallback;
    private boolean edit = false;
    private int selectState = -1;
    private List<String> mDeleteList = new ArrayList<>();
    
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
            initData();
            initDown();
        }
    }

    private void initData() {
        mDownList.clear();
        List<DownloadEntity> allCompleteTask = mFileManagerUtil.getDownloadReceiver()
                .getAllCompleteTask();
        if (allCompleteTask != null) {
            mDownList.addAll(allCompleteTask);
        }
        mAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        mCallback = (FileDownloadExampleActivity) getActivity();
    }
    
    private void initView() {
        
        initRecycler();
    }

    private void initDown() {
        mFileManagerUtil.setOnDownLoaderListener(new FileManagerUtil.OnDownLoaderListener() {
            @Override
            public void onDownloadState(int state, DownloadTask task, Exception e) {
                for (int i = 0; i < mDownList.size(); i++) {
                    if (mDownList.get(i).getKey().equals(task.getKey())) {
                        switch (state) {

                            case TASK_CANCEL:
                                initData();
                                mAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                    }
                }

            }
        });
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

                helper.setGone(R.id.edit_check, edit);
                helper.setOnCheckedChangeListener(R.id.edit_check, new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            mDeleteList.add(item.getKey());
                        } else {
                            mDeleteList.remove(item.getKey());
                        }
                        mCallback.deleteItems(mDeleteList, mDownList.size());
                    }
                });
                switch (selectState) {
                    case 0:
                        helper.setChecked(R.id.edit_check, true);
                        break;
                    case 1:
                        helper.setChecked(R.id.edit_check, false);
                        break;
                    default:
                        break;
                }
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
                        mCallback.update();
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

    @Override
    public void showEdit(boolean isShow) {
        if (getUserVisibleHint()) {
            edit = isShow;
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void selectAll(int state) {
        if (getUserVisibleHint()) {
            selectState = state;
            mDeleteList.clear();
            mAdapter.notifyDataSetChanged();
        }
    }
}
