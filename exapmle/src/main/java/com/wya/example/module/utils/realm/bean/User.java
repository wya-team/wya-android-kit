package com.wya.example.module.utils.realm.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * 创建日期：2018/12/7 9:38
 * 作者： Mao Chunjiang
 * 文件名称： User
 * 类说明：用户对象，数据存储测试
 */
@RealmClass
public class User extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
