package com.wya.example.module.utils.fliedownload;

import android.content.Intent;
import android.os.Environment;
import android.os.StatFs;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Formatter;
import android.widget.TextView;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.uikit.tablayout.WYATabLayoutControl;
import com.wya.utils.utils.DataCleanUtil;
import com.wya.utils.utils.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : XuDonglin
 * @time : 2019-01-05
 * @Description : 文件下载
 */
public class FileDownloadExampleActivity extends BaseActivity implements IRomUpdateCallback {
    
    public static final String FILE_IMG_DIR = Environment.getExternalStorageDirectory().getPath()
            + "/WYADownLoad/IMG";
    public static final String FILE_VIDEO_DIR = Environment.getExternalStorageDirectory().getPath
            () + "/WYADownLoad/Video";
    @BindView(R.id.down_tab_layout)
    TabLayout mDownTabLayout;
    @BindView(R.id.down_viewpager)
    ViewPager mDownViewpager;
    @BindView(R.id.down_space)
    TextView mDownSpace;
    @BindView(R.id.free_space)
    TextView mFreeSpace;
    private String url = "http://221.228.226.5/14/z/w/y/y/zwyyobhyqvmwslabxyoaixvyubmekc/sh" +
            ".yinyuetai" +
            ".com/4599015ED06F94848EBF877EAAE13886.mp4";
    private String filepath = "/testdownload.mp4";
    private String url2 = "https://video.pc6.com/v/1810/pyqxxjc3.mp4";
    private String filepath2 = "/sdcard/testdownload2.mp4";
    private String pic = "/storage/emulated/0/Pictures/Screenshots/pic.jpg";
    
    private List<Fragment> mFragmentList = new ArrayList<>();
    
    public static void updateRom() {
    
    }
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_file_download_example;
    }
    
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        
        setTitle("下载(util(FileManagerUtil)");
        
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(this, ReadmeActivity.class).putExtra("url", url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(this, url);
        });
        
        mFragmentList.add(new FileDownFragment());
        mFragmentList.add(new DownFileCompleteFragment());
        
        initSpace();
        initTabLayoutAndViewPager();
        
    }
    
    private void initTabLayoutAndViewPager() {
        WYATabLayoutControl.lineWidth(mDownTabLayout);
        mDownViewpager.setAdapter(new DownPagerAdapter(getSupportFragmentManager()));
        mDownTabLayout.setupWithViewPager(mDownViewpager);
        mDownTabLayout.getTabAt(0).setText("下载中");
        mDownTabLayout.getTabAt(1).setText("已完成");
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
    
}
