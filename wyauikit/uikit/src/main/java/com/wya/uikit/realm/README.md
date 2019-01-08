# Realm
## 功能说明
- 数据的增删改查
- 数据库存入的Model必须继承RealmObject
- RealmObject 注意事项
```
    1、添加 @RealmClass 修饰符来声明
    2、支持boolean、byte、short、int、long、float、double、String、Date、byte[]、RealmObject、RealmList<? extends RealmObject>，还支持 Boolean、Byte、Short、Integer、Long、Float 和 Double
    3、@primarykey：表示该字段是主键，一般使用过数据库的同学可能知道，primarykey 就是主键，使用 @primarykey 来标注字段类型必须是字符串（String）或整数（byte、short、int、long）以及它们的包装类型（Byte、Short、Integer、Long），还需要注意的是不可以使用多个主键
    4、@Required：表示该字段非空，在某些情况下，有些字段是不能为 null 的，使用 @Required 属性可以强行要求其属性不能为 null，只能用于 Boolean、Byte、Short、Integer、Long、Float、Double、String、byte[] 和 Date，在其他类型属性上使用 @Required 会导致编译失败
    5、@Ignore：表示忽略该字段，添加 @Ignore 标签后，存储数据时会忽略该字段
    6、@Index：添加搜索索引，为字段添加 @Index 标签，插入速度变慢，查询速度变快，支持索引 String、byte、short、int、long、boolean 和 Date字段
```

## 配置说明
- 在项目的build.gradle中添加代码如下
```
          buildscript {
                  dependencies {
                      ...
                      classpath "io.realm:realm-gradle-plugin:5.8.0"
                      ...
                  }
              }
              allprojects {
                  repositories {
                      ...
                      maven { url 'http://oss.jfrog.org/artifactory/oss-snapshot-local' }
                  }
              }   
```

## 使用说明
```
        /**
         * 初始化数据库
         */
        private void initRealm() {
            Realm.init(this);
            RealmConfiguration configuration = new RealmConfiguration.Builder()
                    .name(DB_NAME)//指定数据库的名称，如果不指定默认为 default
                    .schemaVersion(AppUtils.getVersionCode(this)) //指定数据库的版本号
                    // .deleteRealmIfMigrationNeeded()//声明版本冲突时自动删除原数据库
                    // .inMemory()//声明数据库只在内存中持久化
                    // .encryptionkey()//指定数据库的秘钥
                    .build();
            Realm.setDefaultConfiguration(configuration);
        }
 ```       
##  基本使用
- 增|改（已经存在就改，没有就增加）
```
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(realmObject);
            }
        });
```      
- 查     
``` 
        RealmResults<User> userList = realm.where(User.class).findAll();//查全部
        
        User user2 = mRealm.where(User.class).findFirst();  //查第一条
        
        RealmResults<User> userList = mRealm.where(User.class).equalTo("name", "qdj").findAll();  //条件查询
```         
- 删
```  
        //删除某条数据
        final RealmResults<User> userList = realm.where(User.class).findAll();//先查找到数据
        if (userList.size() > 0) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    userList.get(choosePosition).deleteFromRealm();
                }
            });
        }
        
        userList.deleteFirstFromRealm(); //删除user表的第一条数据  
        
        userList.deleteLastFromRealm();//删除user表的最后一条数据  
        
        userList.deleteAllFromRealm();//删除user表的全部数据  
```          

- activity的onCreate中获取实例         
```
   Realm realm = Realm.getDefaultInstance();
``` 

- 使用完后在 onDestory() 方法中进行关闭
```
   realm.close();   
``` 