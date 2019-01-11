package com.wya.example.module.utils.fliedownload;

import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.os.StatFs;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.tablayout.WYATabLayoutControl;
import com.wya.utils.utils.DataCleanUtil;
import com.wya.utils.utils.FileManagerUtil;
import com.wya.utils.utils.StringUtil;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wya.example.module.utils.fliedownload.FlieConfig.FILE_VIDEO_DIR;

/**
 * @author : XuDonglin
 * @time : 2019-01-05
 * @Description : 文件下载
 */
public class FileDownloadExampleActivity extends BaseActivity implements IRomUpdateCallback {
    
    @BindView(R.id.down_tab_layout)
    TabLayout mDownTabLayout;
    @BindView(R.id.down_viewpager)
    ViewPager mDownViewpager;
    @BindView(R.id.down_space)
    TextView mDownSpace;
    @BindView(R.id.free_space)
    TextView mFreeSpace;
    @BindView(R.id.choose_all_text)
    TextView mChooseAllText;
    @BindView(R.id.delete_file_text)
    TextView mDeleteFileText;
    @BindView(R.id.edit_layout)
    LinearLayout mEditLayout;
    private String url = "http://221.228.226.5/14/z/w/y/y/zwyyobhyqvmwslabxyoaixvyubmekc/sh" +
            ".yinyuetai" +
            ".com/4599015ED06F94848EBF877EAAE13886.mp4";
    private String url2 = "https://video.pc6.com/v/1810/pyqxxjc3.mp4";
    
    private List<Fragment> mFragmentList = new ArrayList<>();
    private boolean isShow = false;
    private List<String> mEditList = new ArrayList<>();
    private FileManagerUtil fileManagerUtil;
    private int selectState = -1;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_file_download_example;
    }
    
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        fileManagerUtil = new FileManagerUtil();
        
        setTitle("下载(util(FileManagerUtil)");
        
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setSecondRightIconClickListener(view -> {
            startActivity(new Intent(this, ReadmeActivity.class).putExtra("url", url));
        });
        setSecondRightIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(this, url);
        });

        setFirstRightText("管理");
        setFirstRightTextColor(Color.BLACK);
        showFirstRightText(true);
        setFirstRightTextClickListener(new FirstRightTextClickListener() {
            @Override
            public void rightFirstTextClick(View view) {
                changeEditState();
            }
        });
        
        mFragmentList.add(new FileDownFragment());
        mFragmentList.add(new DownFileCompleteFragment());
        
        initSpace();
        initTabLayoutAndViewPager();
        
    }

    private void changeEditState() {
        isShow = !isShow;
        setFirstRightText(!isShow ? "管理" : "取消");
        mEditLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.getId() != 0) {
                ((IManagerInterface) fragment).showEdit(isShow);
            }
        }
    }
    
    private void initTabLayoutAndViewPager() {
        WYATabLayoutControl.lineWidth(mDownTabLayout);
        mDownViewpager.setAdapter(new DownPagerAdapter(getSupportFragmentManager()));
        mDownTabLayout.setupWithViewPager(mDownViewpager);
        mDownTabLayout.getTabAt(0).setText("下载中");
        mDownTabLayout.getTabAt(1).setText("已完成");
        mDownViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                Log.i("test", "onPageScrolled: ");
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    
    private void initSpace() {
        mDownSpace.setText(DataCleanUtil.getFormatSize(DataCleanUtil.getFolderSize(new File
                (FILE_VIDEO_DIR))));
        mFreeSpace.setText(getSDAvailableSize());
    }
    
    private String getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(this, blockSize * availableBlocks);
    }
    
    @Override
    public void update() {
        initSpace();
    }

    @Override
    public void deleteItems(List<String> urls, int all) {
        mEditList = urls;
        mChooseAllText.setText(mEditList.size() == all ? "取消全选" : "全选");
        mDeleteFileText.setText(MessageFormat.format("删除({0})", mEditList.size()));
    }

    @OnClick({R.id.choose_all_text, R.id.delete_file_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_all_text:
                List<Fragment> fragments = getSupportFragmentManager().getFragments();
                selectState = selectState == 0 ? 1 : 0;
                mChooseAllText.setText(selectState == 0 ? "取消全选" : "全选");
                for (Fragment fragment : fragments) {
                    if (fragment.getId() != 0) {
                        ((IManagerInterface) fragment).selectAll(selectState);
                    }
                }
                break;
            case R.id.delete_file_text:
                if (mEditList.size() == 0) {
                    return;
                }
                for (String url : mEditList) {
                    fileManagerUtil.getDownloadReceiver().load(url).cancel(true);
                }
                changeEditState();
                update();
                break;
            default:
                break;
        }
    }
    
    class DownPagerAdapter extends FragmentPagerAdapter {
        
        public DownPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        
        @Override
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }
        
        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fileManagerUtil.unRegister();
    }
}
