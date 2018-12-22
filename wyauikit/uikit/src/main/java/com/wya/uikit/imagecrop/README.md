# ImageCrop

## 功能说明
图片裁剪

## 用法说明
Crop类控制进入裁剪界面

方法|说明
---|---
Crop create(Activity/Fragment)|创建crop实例，参数为activity或fragment
setImagePath(Uri uri)|传入图片Uri
saveCropImagePath(String path)|设置裁剪图片保存路径
CropQuality(int quality)|设置图片压缩质量
forResult(int requestCode)|Result请求码

- 图片裁剪成功后返回码RESULT_OK,返回结果为data中String类型的key为path

````
Crop.create(ImagePickerExampleActivity.this)
		.setImagePath(uri)
		.saveCropImagePath(file.getParentFile().getPath() + "/test.jpg")
		.CropQuality(80)
		.forResult(1002);
````

