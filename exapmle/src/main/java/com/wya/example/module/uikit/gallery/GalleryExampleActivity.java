package com.wya.example.module.uikit.gallery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wya.example.R;
import com.wya.uikit.gallery.GalleryCreator;
import com.wya.uikit.imagepicker.SpaceDecoration;

import java.util.ArrayList;

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : 图片浏览
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class GalleryExampleActivity extends AppCompatActivity {
    
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    private static final String ORDER_BY = MediaStore.Files.FileColumns.DATE_MODIFIED;
    /**
     * 媒体文件数据库字段
     */
    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.WIDTH,
            MediaStore.MediaColumns.HEIGHT};
    /**
     * 图片
     */
    private static final String SELECTION = MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + " AND " + MediaStore.MediaColumns.SIZE + ">0";
    private RecyclerView imageRecycler;
    private TextView title;
    private BaseQuickAdapter<String, BaseViewHolder> mAdapter;
    private ArrayList<String> images = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_example);
        
        imageRecycler = findViewById(R.id.image_recycler);
        title = findViewById(R.id.title);
        title.setText("Galley");
        
        mAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.gallery_image_item,
                images) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                ImageView imageView = helper.getView(R.id.square_image);
                Glide.with(GalleryExampleActivity.this).load(item).into(imageView);
            }
        };
        
        imageRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        imageRecycler.setAdapter(mAdapter);
        imageRecycler.addItemDecoration(new SpaceDecoration(4, 3, false));
        
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GalleryCreator.create(GalleryExampleActivity.this).openPreviewGallery(position,
                        images);
            }
        });
        
        int selfPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        
        if (selfPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest
                    .permission.READ_EXTERNAL_STORAGE}, 1000);
        } else {
            getData();
        }
    }
    
    private void getData() {
        getSupportLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @SuppressLint("WrongThread")
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
                CursorLoader cursorLoader = new CursorLoader(getApplicationContext(), QUERY_URI,
                        PROJECTION, SELECTION, new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)}, ORDER_BY);
                return cursorLoader;
            }
            
            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
                images.clear();
                if (cursor != null) {
                    int count = cursor.getCount();
                    if (count > 0) {
                        cursor.moveToLast();
                        do {
                            String path = cursor.getString
                                    (cursor.getColumnIndexOrThrow(PROJECTION[1]));
                            
                            String pictureType = cursor.getString
                                    (cursor.getColumnIndexOrThrow(PROJECTION[2]));
                            
                            int w = cursor.getInt
                                    (cursor.getColumnIndexOrThrow(PROJECTION[3]));
                            
                            int h = cursor.getInt
                                    (cursor.getColumnIndexOrThrow(PROJECTION[4]));
                            images.add(path);
                            
                            Log.i("1111", "onLoadFinished: " + path);
                        } while (cursor.moveToPrevious());
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.i("111", "onLoadFinished: " + "失败");
                }
            }
            
            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            
            }
        });
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                
                getData();
                
            } else {
                Toast.makeText(this, "拒绝", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    
}
