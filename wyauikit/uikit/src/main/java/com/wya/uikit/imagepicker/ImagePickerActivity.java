package com.wya.uikit.imagepicker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wya.uikit.R;
import com.wya.uikit.choicemenu.ChoiceMenu;
import com.wya.uikit.choicemenu.ChoiceMenuViewHolder;
import com.wya.uikit.gallery.GalleryConfig;
import com.wya.uikit.gallery.GalleryCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class ImagePickerActivity extends AppCompatActivity implements View.OnClickListener,
                                                                      ImageGridAdapter.OnImageSelectedChangedListener {
    private ImageView picture_left_back;
    private TextView picture_title;
    private RecyclerView picture_recycler;
    private LinearLayout picture_bottom_layout;
    private TextView tv_preview;
    private TextView tv_commit;
    private RelativeLayout picker_title_layout;
    private ImageGridAdapter mGridAdapter;
    private ChoiceMenu<LocalMediaFolder> mChoiceMenu;
    private List<LocalMediaFolder> mFolders = new ArrayList<>();
    private Drawable mDrawableUp;
    private Drawable mDrawableDown;
    private boolean isDown;
    private String TAG = "ImagePickerActivity";
    private List<LocalMedia> mSelected = new ArrayList<>();
    private List<LocalMedia> mLocalMedia = new ArrayList<>();
    private int maxNum;
    private String imagePath;
    private LocalMediaFolder mCurrentFolder;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);
        setColor(getResources().getColor(R.color.black));
        int selfPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest
                .permission.READ_EXTERNAL_STORAGE);
        
        if (selfPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest
                    .permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            readLocalImage();
        }
        
        maxNum = getIntent().getIntExtra(PickerConfig.IMAGE_NUMBER, 1);
        
        initView();
        initChoiceMenu();
        
    }
    
    /**
     * 设置状态栏颜色
     */
    
    public void setColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View fakeStatusBarView = decorView.findViewById(R.id.statusbarutil_fake_status_bar_view);
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.getVisibility() == View.GONE) {
                    fakeStatusBarView.setVisibility(View.VISIBLE);
                }
                fakeStatusBarView.setBackgroundColor(color);
            }
            ViewGroup parent = findViewById(android.R.id.content);
            for (int i = 0, count = parent.getChildCount(); i < count; i++) {
                View childView = parent.getChildAt(i);
                if (childView instanceof ViewGroup) {
                    childView.setFitsSystemWindows(true);
                    ((ViewGroup) childView).setClipToPadding(true);
                }
            }
        }
    }
    
    /**
     * init folder menu
     */
    private void initChoiceMenu() {
        mChoiceMenu = new ChoiceMenu<LocalMediaFolder>(this, mFolders, R.layout
                .folder_choice_item) {
            @Override
            public void setValueFirst(final ChoiceMenuViewHolder helper, final LocalMediaFolder
                    item) {
                helper.setText(R.id.folder_name, item.getName())
                        .setText(R.id.folder_image_num, item.getImageNum() + "");
                ImageView imageView = helper.getView(R.id.folder_first_image);
                Glide.with(ImagePickerActivity.this).load(item.getFirstImagePath())
                        .into(imageView);
            }
            
            @Override
            public void setValueSecond(ChoiceMenuViewHolder helper, LocalMediaFolder item) {
            
            }
        };
        mChoiceMenu.setShadow(false);
        mChoiceMenu.setOutsideTouchable(false);
        mChoiceMenu.setOnFirstAdapterItemClickListener(
                new ChoiceMenu.OnFirstAdapterItemClickListener() {
                    @Override
                    public void onClick(int position, View v, ChoiceMenu menu) {
                        changeTitleImageAndStatus();
                        mCurrentFolder = mFolders.get(position);
                        picture_title.setText(mCurrentFolder.getName());
                        mGridAdapter.bindData(position == 0, mCurrentFolder.getImages());
                    }
                });
    }
    
    /**
     * init view
     */
    private void initView() {
        picture_left_back = findViewById(R.id.picture_left_back);
        picture_title = findViewById(R.id.picture_title);
        picture_recycler = findViewById(R.id.picture_recycler);
        picture_bottom_layout = findViewById(R.id.picture_bottom_layout);
        tv_preview = findViewById(R.id.tv_preview);
        tv_commit = findViewById(R.id.tv_commit);
        picker_title_layout = findViewById(R.id.picker_title_layout);
        
        picture_title.setOnClickListener(this);
        picture_left_back.setOnClickListener(this);
        tv_preview.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        
        initAdapter();
        
        mDrawableUp = getResources().getDrawable(R.drawable.icon_up);
        mDrawableDown = getResources().getDrawable(R.drawable.icon_down);
        mDrawableUp.setBounds(0, 0, mDrawableUp.getMinimumWidth(), mDrawableUp.getMinimumHeight());
        mDrawableDown.setBounds(0, 0, mDrawableDown.getMinimumWidth(), mDrawableDown
                .getMinimumHeight());
        
    }
    
    /**
     * init recyclerView adapter
     */
    private void initAdapter() {
        mGridAdapter = new ImageGridAdapter(this, this, maxNum);
        picture_recycler.setLayoutManager(new GridLayoutManager(this, 4));
        picture_recycler.addItemDecoration(new SpaceDecoration(4, (int) dp2px(3), false));
        picture_recycler.setAdapter(mGridAdapter);
        mGridAdapter.setImageClickListener(new ImageGridAdapter.OnImageClickListener() {
            @Override
            public void onClick(int position, List<LocalMedia> mImages, List<LocalMedia>
                    mImageSelected) {
                GalleryCreator.create(ImagePickerActivity.this).openPreviewImagePicker
                        (position, mImages, mImageSelected,  PickerConfig
                                .PICKER_GALLERY_RESULT, maxNum);
            }
        });
        
        mGridAdapter.setPhotoClickListener(new ImageGridAdapter.OnTakePhotoClickListener() {
            @Override
            public void onClick() {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest
                        .permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ImagePickerActivity.this, new
                            String[]{Manifest.permission.CAMERA}, 1001);
                } else {
                    takePhoto();
                }
                
            }
        });
    }
    
    /**
     * takePhoto
     * folder :/DCIM/UIkit/
     */
    private void takePhoto() {
        Uri uri;
        File file = new File(Environment.getExternalStorageDirectory(),
                "/DCIM/UIkit/" + "UIKit_" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        imagePath = file.getAbsolutePath();
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, PickerConfig.REQUEST_CAMERA);
    }
    
    /**
     * load images
     */
    private void readLocalImage() {
        LocalMediaLoader.create(this).LoadImage(new LocalMediaLoader.OnLoadImageListener() {
            @Override
            public void completed(List<LocalMediaFolder> localMediaFolders) {
                if (localMediaFolders.size() > 0) {
                    LocalMediaFolder localMediaFolder = localMediaFolders.get(0);
                    //数据没有变化（拍照返回或直接返回）
                    if (mLocalMedia.size() != localMediaFolder.getImages().size()) {
                        mLocalMedia.clear();
                        mLocalMedia.addAll(localMediaFolder.getImages());
                        picture_title.setText(localMediaFolder.getName());
                        mGridAdapter.bindData(true, localMediaFolder.getImages());
                        mFolders.clear();
                        mFolders.addAll(localMediaFolders);
                        mChoiceMenu.notifyAdapterData();
                        mCurrentFolder = mFolders.get(0);
                    }
                    
                } else {
                    picture_title.setText("相册");
                }
            }
        });
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readLocalImage();
            } else {
                Toast.makeText(this, "读取内存卡权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 1001) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                Toast.makeText(this, "拍照权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    
    public float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources()
                .getDisplayMetrics());
    }
    
    @Override
    public void onClick(View v) {
        //show choice menu
        if (v.getId() == R.id.picture_title) {
            changeTitleImageAndStatus();
        }
        
        if (v.getId() == R.id.picture_left_back) {
            finish();
        }
        if (v.getId() == R.id.tv_commit) {
            if (mSelected.size() > 0) {
                
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                
                //                bundle.putSerializable(PickerConfig.IMAGE_SELECTED, (Serializable) mSelected);
                bundle.putStringArrayList(PickerConfig.IMAGE_SELECTED, returnImagePaths(mSelected));
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        if (v.getId() == R.id.tv_preview) {
            if (mSelected.size() > 0) {
                GalleryCreator.create(this).openPreviewImagePicker(0, mSelected, mSelected, PickerConfig.PICKER_GALLERY_RESULT, maxNum);
            }
        }
        
    }
    
    /**
     * change title image status
     */
    private void changeTitleImageAndStatus() {
        if (!isDown) {
            mChoiceMenu.showAsDropDown(picker_title_layout);
            picture_title.setCompoundDrawables(null, null, mDrawableUp, null);
        } else {
            picture_title.setCompoundDrawables(null, null, mDrawableDown, null);
            mChoiceMenu.dismiss();
        }
        isDown = !isDown;
    }
    
    /**
     * select image changed
     *
     * @param mSelectedImages selected images
     */
    @Override
    public void change(List<LocalMedia> mSelectedImages) {
        if (mSelectedImages.size() > 0) {
            tv_commit.setText("(" + mSelectedImages.size() + ")" + "确定");
            tv_commit.setEnabled(true);
            tv_preview.setEnabled(true);
            tv_commit.setTextColor(getResources().getColor(R.color.color_orange));
            tv_preview.setTextColor(getResources().getColor(R.color.color_orange));
        } else {
            tv_commit.setText("确定");
            tv_commit.setEnabled(false);
            tv_preview.setEnabled(false);
            tv_commit.setTextColor(getResources().getColor(R.color.color_666));
            tv_preview.setTextColor(getResources().getColor(R.color.color_666));
        }
        mSelected = mSelectedImages;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == PickerConfig.PICKER_GALLERY_RESULT) {
            if (resultCode == RESULT_CANCELED && data != null && data.hasExtra(GalleryConfig
                    .IMAGE_LIST_SELECTED)) {
                Bundle extras = data.getExtras();
                mSelected = (List<LocalMedia>) extras.getSerializable
                        (GalleryConfig.IMAGE_LIST_SELECTED);
                change(mSelected);
                mGridAdapter.notifySelectedData(mSelected);
            }
            
            if (resultCode == RESULT_OK && data != null && data.hasExtra(GalleryConfig
                    .IMAGE_LIST_SELECTED)) {
                Bundle extras = data.getExtras();
                mSelected = (List<LocalMedia>) extras.getSerializable
                        (GalleryConfig.IMAGE_LIST_SELECTED);
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                //                bundle.putSerializable(PickerConfig.IMAGE_SELECTED, (Serializable) mSelected);
                bundle.putStringArrayList(PickerConfig.IMAGE_SELECTED, returnImagePaths(mSelected));
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        
        if (requestCode == PickerConfig.REQUEST_CAMERA && resultCode == RESULT_OK) {
            final File file = new File(imagePath);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            
            LocalMedia localMedia = new LocalMedia(imagePath, "image/jpg");
            //add all folder
            LocalMediaFolder firstFolder = mFolders.get(0);
            List<LocalMedia> firstImages = firstFolder.getImages();
            firstImages.add(localMedia);
            firstFolder.setFirstImagePath(imagePath);
            firstFolder.setImageNum(firstFolder.getImageNum() + 1);
            
            //add or new folder's item
            LocalMediaFolder imageFolder = getImageFolder(imagePath, mFolders);
            List<LocalMedia> images = imageFolder.getImages();
            images.add(localMedia);
            imageFolder.setImageNum(imageFolder.getImageNum() + 1);
            
            //update
            mGridAdapter.bindData(true, firstImages);
            mChoiceMenu.notifyAdapterData();
            
        }
        
    }
    
    /**
     * create LocalMediaFolder to save LocalMedia
     *
     * @param path         image path
     * @param imageFolders folder list
     * @return
     */
    private LocalMediaFolder getImageFolder(String path, List<LocalMediaFolder> imageFolders) {
        File imageFile = new File(path);
        File folderFile = imageFile.getParentFile();
        for (LocalMediaFolder folder : imageFolders) {
            if (folder.getName().equals(folderFile.getName())) {
                return folder;
            }
        }
        //create new folder and set the first image's path
        LocalMediaFolder newFolder = new LocalMediaFolder();
        newFolder.setName(folderFile.getName());
        newFolder.setFirstImagePath(path);
        imageFolders.add(newFolder);
        return newFolder;
    }
    
    private ArrayList<String> returnImagePaths(List<LocalMedia> selected) {
        ArrayList<String> returnList = new ArrayList<>();
        for (int i = 0; i < selected.size(); i++) {
            returnList.add(selected.get(i).getPath());
        }
        return returnList;
    }
}
