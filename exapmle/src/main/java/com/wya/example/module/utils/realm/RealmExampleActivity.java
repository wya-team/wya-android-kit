package com.wya.example.module.utils.realm;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wya.example.R;
import com.wya.example.base.BaseActivity;
import com.wya.example.module.utils.realm.adapter.RealmListAdapter;
import com.wya.example.module.utils.realm.bean.User;
import com.wya.utils.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmExampleActivity extends BaseActivity {

    @BindView(R.id.tv_db_name)
    TextView tvDbName;
    @BindView(R.id.tv_id)
    EditText tvId;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_age)
    EditText tvAge;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.tv_choose)
    TextView tvChoose;

    private RealmResults<User> userList;

    private Realm realm;

    private RealmListAdapter realmListAdapter;
    private List<String> data = new ArrayList<>();
    private int choosePosition;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_realm_example;
    }

    @Override
    protected void initView() {
        setToolBarTitle("Realm（增删改查）");

        initRealm();

        initRecycleView();
        query();
    }

    private void initRecycleView() {
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        realmListAdapter = new RealmListAdapter(this, R.layout.wya_realm_item, data);
        recycleView.setAdapter(realmListAdapter);
        //RecyclerView条目点击事件
        realmListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                choosePosition = position;
                tvName.setText(userList.get(position).getName());
                tvId.setText(userList.get(position).getId() + "");
                tvAge.setText(userList.get(position).getAge() + "");
                tvChoose.setText("选中：" + userList.get(position).getId() + "---" + userList.get(position).getName() + "---" + userList.get(position).getAge());
            }
        });
    }

    private void initRealm() {
        realm = Realm.getDefaultInstance();
        tvDbName.setText("数据库名称：" + realm.getConfiguration().getRealmFileName());
    }


    @OnClick({R.id.wya_button_add, R.id.wya_button_reduce, R.id.wya_button_update, R.id.wya_button_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wya_button_add:
                boolean isAdd = true;
                User user = new User();
                if (!"".equals(tvId.getText().toString())) {
                    user.setId(Integer.valueOf(tvId.getText().toString()).intValue());
                } else {
                    isAdd = false;
                }
                if (!"".equals(tvAge.getText().toString())) {
                    user.setAge(Integer.valueOf(tvAge.getText().toString()).intValue());
                } else {
                    isAdd = false;
                }
                if (!"".equals(tvName.getText().toString())) {
                    user.setName(tvName.getText().toString());
                } else {
                    isAdd = false;
                }
                if (isAdd) {
                    add(user);
                } else {
                    getWyaToast().showShort( "请输入正确的值");
                }
                break;
            case R.id.wya_button_reduce:
                delete();
                break;
            case R.id.wya_button_update:
                boolean isAdd2 = true;
                User user2 = new User();
                if (!"".equals(tvId.getText().toString())) {
                    user2.setId(Integer.valueOf(tvId.getText().toString()).intValue());
                } else {
                    isAdd2 = false;
                }
                if (!"".equals(tvAge.getText().toString())) {
                    user2.setAge(Integer.valueOf(tvAge.getText().toString()).intValue());
                } else {
                    isAdd2 = false;
                }
                if (!"".equals(tvName.getText().toString())) {
                    user2.setName(tvName.getText().toString());
                } else {
                    isAdd2 = false;
                }
                if (isAdd2) {
                    add(user2);
                } else {
                    getWyaToast().showShort( "请输入正确的值");
                }
                break;
            case R.id.wya_button_query:
                query();
                break;
            default:
                break;
        }
    }

    private void query() {
        userList = realm.where(User.class).findAll();
        userList = userList.sort("id");
        data = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            data.add(userList.get(i).getId() + "---" + userList.get(i).getName() + "---" + userList.get(i).getAge());
            LogUtil.e( userList.get(i).getId() + "---" + userList.get(i).getName() + "---" + userList.get(i).getAge());
        }
        realmListAdapter.setNewData(data);
    }

    private void delete() {
        //先查找到数据  
        final RealmResults<User> userList = realm.where(User.class).findAll();
        if(userList.size() > 0){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    userList.get(choosePosition).deleteFromRealm();
                    query();
                }
            });
        }
    }

    private void add(RealmObject realmObject) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(realmObject);
                query();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}
