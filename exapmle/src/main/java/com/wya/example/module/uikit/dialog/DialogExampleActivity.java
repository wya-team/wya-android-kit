package com.wya.example.module.uikit.dialog;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.uikit.dialog.WYACustomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DialogExampleActivity extends BaseActivity {

    @BindView(R.id.radio_content_show)
    RadioButton radioContentShow;
    @BindView(R.id.radio_content_unshow)
    RadioButton radioContentUnshow;
    @BindView(R.id.radio_button_1)
    RadioButton radioButton1;
    @BindView(R.id.radio_button_2)
    RadioButton radioButton2;
    @BindView(R.id.radio_button_3)
    RadioButton radioButton3;
    @BindView(R.id.radio_edit_show)
    RadioButton radioEditShow;
    @BindView(R.id.radio_edit_unshow)
    RadioButton radioEditUnshow;
    @BindView(R.id.radio_list_show)
    RadioButton radioListShow;
    @BindView(R.id.radio_list_unshow)
    RadioButton radioListUnshow;
    @BindView(R.id.radio_canceled_on_touch)
    RadioButton radioCanceledOnTouch;
    @BindView(R.id.radio_uncanceled_on_touch)
    RadioButton radioUncanceledOnTouch;
    @BindView(R.id.radio_cancelable)
    RadioButton radioCancelable;
    @BindView(R.id.radio_un_cancelable)
    RadioButton radioUnCancelable;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.radio_title_show)
    RadioButton radioTitleShow;
    @BindView(R.id.radio_title_unshow)
    RadioButton radioTitleUnshow;

    private boolean canceledOnTouch = true;
    private boolean cancelable = true;
    private boolean list = false;
    private String title = "我是标题";
    private String content = "我是内容";
    private String cancel = "取消";
    private String sure = "确定";
    private boolean isShowButton = true;
    private String edit_text_str = "我是编辑框内容";
    private List<String> data = new ArrayList<>();


    @Override
    protected void initView() {
        initToolBarTitle("Dialog", 18,this.getResources().getColor(R.color.white), true);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_dialog_example;
    }

    /**
     * 是否保存
     */
    private WYACustomDialog wyaCustomDialog;

    private void showWYADialog() {
        wyaCustomDialog = new WYACustomDialog(this, canceledOnTouch, cancelable);
        wyaCustomDialog.show();
        wyaCustomDialog.setMessage(content);
        wyaCustomDialog.setTitle(title);
        if (isShowButton) {
            wyaCustomDialog.setCancleText(cancel);
            wyaCustomDialog.setConfirmText(sure);
        } else {
            wyaCustomDialog.setNoButton();
        }
        wyaCustomDialog.setRecyclerView(data, this);
        wyaCustomDialog.setEdit_text("我是提示内容", edit_text_str);
        wyaCustomDialog.setConfirmColor(R.drawable.btn_c00bfff_click_color);
        wyaCustomDialog.setCancelColor(R.drawable.btn_c333333_click_color);
        wyaCustomDialog.setYesOnclickListener(() -> wyaCustomDialog.dismiss());
        wyaCustomDialog.setNoOnclickListener(() -> {
            wyaCustomDialog.dismiss();
        });
        wyaCustomDialog.setListOnclickListener(position -> {
            Toast.makeText(DialogExampleActivity.this, position +"", Toast.LENGTH_SHORT).show();
            wyaCustomDialog.dismiss();
        });
    }

    @OnClick({R.id.radio_title_show, R.id.radio_title_unshow, R.id.radio_content_show, R.id.radio_content_unshow, R.id.radio_button_1, R.id.radio_button_2, R.id.radio_button_3, R.id.radio_edit_show, R.id.radio_edit_unshow, R.id.radio_list_show, R.id.radio_list_unshow, R.id.radio_canceled_on_touch, R.id.radio_uncanceled_on_touch, R.id.radio_cancelable, R.id.radio_un_cancelable, R.id.tv_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_title_show:
                title = "我是标题";
                break;
            case R.id.radio_title_unshow:
                title = "";
                break;
            case R.id.radio_content_show:
                content = "我是内容";
                break;
            case R.id.radio_content_unshow:
                content = "";
                break;
            case R.id.radio_button_1:
                isShowButton = false;
                cancel = "";
                sure = "";
                break;
            case R.id.radio_button_2:
                isShowButton = true;
                cancel = "";
                sure = "确定";
                break;
            case R.id.radio_button_3:
                isShowButton = true;
                cancel = "取消";
                sure = "确定";
                break;
            case R.id.radio_edit_show:
                edit_text_str = "我是编辑框内容";
                break;
            case R.id.radio_edit_unshow:
                edit_text_str = "";
                break;
            case R.id.radio_list_show:
                data.add("第一个");
                data.add("第二个");
                data.add("第三个");
                break;
            case R.id.radio_list_unshow:
                data.clear();
                break;
            case R.id.radio_canceled_on_touch:
                canceledOnTouch = true;
                break;
            case R.id.radio_uncanceled_on_touch:
                canceledOnTouch = false;
                break;
            case R.id.radio_cancelable:
                cancelable = true;
                break;
            case R.id.radio_un_cancelable:
                cancelable = false;
                break;
            case R.id.tv_show:
                showWYADialog();
                break;
        }
    }

}
