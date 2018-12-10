# Gallery

## 功能说明
相册浏览

## 用法说明

使用第三方库：Glide加载图片

> implementation 'com.github.bumptech.glide:glide:4.8.0'
>
> annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'

GalleryCreator这个类控制进入相册预览界面

1. create方法中传入Activity或fragment创建GalleryCreator实例
2. `openPreviewGallery(int position, List<T> images)`方法是进入预览页面
3. `public <T> void openPreviewImagePicker(int position, List<T>images, List<T>imagesSelected, String field, int result, int max)`用于选择图片imagepicker


参数|说明
---|---
position|当前图片位置
images|图片list
imagesSelected|已选中的图片的list
field|图片路径的属性名，用于反射获取图片路径
result|请求码
max|最大可选图片数量

