package com.wya.example.module.utils.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.example.readme.ReadmeActivity;
import com.wya.utils.utils.QRCodeUtil;
import com.wya.utils.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QRCodeExampleActivity extends BaseActivity {
    
    @BindView(R.id.qr_code_edit)
    EditText mQrCodeEdit;
    @BindView(R.id.line_code_edit)
    EditText mLineCodeEdit;
    @BindView(R.id.imageview)
    ImageView mImageview;
    private String path;
    
    public static final String TAG = "QRCodeExampleActivity";
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode_example;
    }
    
    @Override
    protected void initView() {
        setTitle("生成二维码");
        String url = getIntent().getStringExtra("url");
        showSecondRightIcon(true);
        setSecondRightIcon(R.drawable.icon_help);
        setRightSecondIconClickListener(view -> {
            startActivity(new Intent(QRCodeExampleActivity.this, ReadmeActivity.class).putExtra("url", url));
        });
        setRightSecondIconLongClickListener(view -> {
            getWyaToast().showShort("链接地址复制成功");
            StringUtil.copyString(QRCodeExampleActivity.this, url);
        });
        ButterKnife.bind(this);
        path = getDiskCachePath(this) + "/test.jpg";
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        
        mLineCodeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 80) {
                    s = s.subSequence(0, 80);
                    mLineCodeEdit.setText(s);
                    mLineCodeEdit.setSelection(80);
                }
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            
            }
        });
    }
    
    /**
     * 获取cache路径
     *
     * @param context
     *
     * @return
     */
    public String getDiskCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }
    
    @OnClick({R.id.crate_qr_image, R.id.crate_line_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.crate_qr_image:
                String qr = mQrCodeEdit.getText().toString();
                if (TextUtils.isEmpty(qr)) {
                    return;
                }
                if (QRCodeUtil.createQRImage(qr, 600, 600, null, path)) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    mImageview.setImageBitmap(bitmap);
                }
                break;
            case R.id.crate_line_image:
                String line = mLineCodeEdit.getText().toString();
                if (TextUtils.isEmpty(line) || line.getBytes().length != line.length()) {
                    Toast.makeText(this, "请输入数字、英文字母或其他特殊字符", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (QRCodeUtil.createBarcode(line, 600, 300, path)) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    mImageview.setImageBitmap(bitmap);
                }
                break;
            default:
                break;
        }
    }
}
