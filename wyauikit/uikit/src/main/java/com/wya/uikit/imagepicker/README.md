# ImagePicker


## 功能说明
图片选择功能

## 用法说明
通过ImagePickerCreator类使用图片选择器

方法|说明
---|---
ImagePickerCreator(Activity/Fragment)|构造方法,参数可以传入fragment或Activity
maxImages(int num) |设置最大选取图片数量,默认是1
forResult(int requestCode)|结果回调onActivityResult requestCode

````
ImagePickerCreator.create(ImagePickerExampleActivity.this)
		.maxImages(1)
		.forResult(1001);
````

- onActivityResult中返回结果中获取选中的图片list,key是PickerConfig.IMAGE_SELECTED；value是Serializable类型的`List<LocalImage>`
