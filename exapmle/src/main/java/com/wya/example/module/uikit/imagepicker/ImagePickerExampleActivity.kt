package com.wya.example.module.uikit.imagepicker

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.NinePatchDrawable
import android.net.Uri
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.rxpermissions.RxPermissions
import com.wya.example.R
import com.wya.example.base.BaseActivity
import com.wya.example.module.example.fragment.ExampleFragment
import com.wya.example.module.example.readme.ReadmeActivity
import com.wya.example.module.hardware.camera.CameraExampleActivity
import com.wya.hardware.camera.WYACameraView
import com.wya.uikit.advrecyclerview.animator.DraggableItemAnimator
import com.wya.uikit.advrecyclerview.draggable.RecyclerViewDragDropManager
import com.wya.uikit.dialog.WYACustomDialog
import com.wya.uikit.gallery.GalleryCreator
import com.wya.uikit.imagecrop.Crop
import com.wya.uikit.imagepicker.ImagePickerCreator
import com.wya.uikit.imagepicker.PickerConfig
import com.wya.utils.utils.StringUtil
import java.io.File
import java.util.*

/**
 * @author : XuDonglin
 * @time : 2019-01-10
 * @description : 图片选择界面
 */
class ImagePickerExampleActivity : BaseActivity() {

    private var mRecyclerView: RecyclerView? = null
    private lateinit var mDialog: WYACustomDialog
    private var mInput: EditText? = null
    private var mCropCrop: ImageView? = null
    private var mCropSelect: ImageView? = null

    private var mMaxImages: Int = 0
    private val mAllList = ArrayList<String>()

    private lateinit var mDragDropManager: RecyclerViewDragDropManager
    private lateinit var mDraggableAdapter: DraggableImagePickerAdapter
    private lateinit var mHeaderFooterAdapter: HeaderFooterAdapter
    private lateinit var mDataProvider: ImagePickerDataProvider

    /**
     * 默认可以拍照和录制视频
     */
    private val state = WYACameraView.BUTTON_STATE_BOTH

    override fun getLayoutId(): Int {
        return R.layout.activity_image_picker_example
    }

    override fun initView() {
        initToolBar()

        mRecyclerView = findViewById(R.id.recycler_view) as RecyclerView
        mInput = findViewById(R.id.num_input) as EditText
        mCropCrop = findViewById(R.id.crop_crop) as ImageView
        mCropSelect = findViewById(R.id.crop_select) as ImageView
        findViewById(R.id.crop)?.setOnClickListener {
            ImagePickerCreator.create(this@ImagePickerExampleActivity)
                    .maxImages(1)
                    .forResult(CROP_PHOTO)
        }

        initRecycler()
    }

    private fun initToolBar() {
        setTitle("图片选择器(imagepicker)")
        val url = intent.getStringExtra(ExampleFragment.EXTRA_URL)
        showSecondRightIcon(true)
        setSecondRightIcon(R.drawable.icon_help)
        setSecondRightIconClickListener { startActivity(Intent(this@ImagePickerExampleActivity, ReadmeActivity::class.java).putExtra(ExampleFragment.EXTRA_URL, url)) }
        setSecondRightIconLongClickListener {
            showShort("链接地址复制成功")
            StringUtil.copyString(this@ImagePickerExampleActivity, url)
        }
    }

    private fun initRecycler() {
        mDataProvider = ImagePickerDataProvider(mAllList)
        mDraggableAdapter = DraggableImagePickerAdapter(this, mAllList, mDataProvider)
        mDraggableAdapter.setOnItemClickListener(object : DraggableImagePickerAdapter.OnItemClickListener {
            override fun onDelete(position: Int) {
                mAllList.removeAt(position)
                mDataProvider.removeItem(position)
                mDraggableAdapter.notifyDataSetChanged()
                when {
                    mAllList.size == mMaxImages - 1 -> mHeaderFooterAdapter.addFooterItem()
                }
            }

            override fun onItemClick(position: Int) {
                val preview = ArrayList<String>()
                preview.addAll(mDraggableAdapter.getImageList())
                when {
                    TextUtils.isEmpty(preview[preview.size - 1]) -> preview.removeAt(preview.size - 1)
                }
                GalleryCreator.create(this@ImagePickerExampleActivity).openPreviewGallery(position, preview)
            }
        })

        val animator = DraggableItemAnimator()
        mRecyclerView?.itemAnimator = animator
        mRecyclerView?.layoutManager = GridLayoutManager(this, 3)

        mDragDropManager = RecyclerViewDragDropManager()
        mDragDropManager.setDraggingItemShadowDrawable(ContextCompat.getDrawable(this, R.drawable.material_shadow_z3) as NinePatchDrawable?)
        mDragDropManager.setInitiateOnLongPress(true)
        mDragDropManager.setInitiateOnMove(false)
        mDragDropManager.setLongPressTimeout(750)
        mDragDropManager.dragStartItemAnimationDuration = 250
        mDragDropManager.draggingItemAlpha = 0.8f
        mDragDropManager.draggingItemScale = 1.1f

        val wrappedAdapter = mDragDropManager.createWrappedAdapter(mDraggableAdapter)
        mHeaderFooterAdapter = HeaderFooterAdapter(wrappedAdapter, object : OnListItemClickMessageListener {
            override fun onItemClicked(message: String) {
                initDialog()
            }
        }
        )
        mRecyclerView?.adapter = mHeaderFooterAdapter
        mDragDropManager.attachRecyclerView(mRecyclerView!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PHOTO -> when (resultCode) {
                Activity.RESULT_OK ->
                    when {
                        data != null && data.hasExtra(PickerConfig.IMAGE_SELECTED) -> {
                            val extras = data.extras
                            val list = extras?.getStringArrayList(PickerConfig.IMAGE_SELECTED)
                            mAllList.addAll(list!!)
                            for (item in list) {
                                mDataProvider.addData(item)
                            }
                            mDraggableAdapter.notifyDataSetChanged()
                        }
                    }
            }
        }
        when {
            requestCode == CROP_PHOTO && resultCode == Activity.RESULT_OK ->
                when {
                    data != null && data.hasExtra(PickerConfig.IMAGE_SELECTED) -> {
                        val extras = data.extras
                        val list = extras?.getStringArrayList(PickerConfig.IMAGE_SELECTED)
                        val path = list!![0]
                        val bitmap = BitmapFactory.decodeFile(path)
                        mCropSelect?.setImageBitmap(bitmap)
                        val file = File(path)
                        val uri: Uri
                        uri = when {
                            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> FileProvider.getUriForFile(this, "com.wya.example.fileprovider", file)
                            else -> Uri.fromFile(file)
                        }

                        Crop.create(this@ImagePickerExampleActivity)
                                .setImagePath(uri)
                                .saveCropImagePath(file.parentFile.path + "/test.jpg")
                                .cropQuality(80)
                                .forResult(1002)
                    }
                }
        }
        when {
            requestCode == CROP && resultCode == Activity.RESULT_OK -> {
                val path = data?.getStringExtra("path")
                val file = File(path)
                val bitmap = BitmapFactory.decodeFile(file.path)
                mCropCrop?.setImageBitmap(bitmap)
            }
        }
        when {
            requestCode == CROP && resultCode == Activity.RESULT_CANCELED -> Toast.makeText(this, "裁剪被取消了", Toast.LENGTH_SHORT).show()
        }
        when (requestCode) {
            CAMERA -> {
                when (resultCode) {
                    TAKE_PHOTO -> {
                        Log.i("MCJ", "take photo")
                        val path = data!!.getStringExtra("path")
                        mAllList.add(path)
                        mDataProvider.addData(path)
                        mDraggableAdapter.notifyDataSetChanged()
                    }
                }

                when (resultCode) {
                    VIDEO -> {
                        Log.i("MCJ", "video")
                        val path = data!!.getStringExtra("path")
                        val url = data.getStringExtra("url")
                        mAllList.add(url)
                        mDataProvider.addData(path)
                        mDraggableAdapter.notifyDataSetChanged()
                    }
                }

                when (resultCode) {
                    NO_PERMISSIONS_CAMEAR -> {
                        mDraggableAdapter.notifyDataSetChanged()
                        Toast.makeText(this, "请检查相机权限~", Toast.LENGTH_SHORT).show()
                    }
                }

                when (resultCode) {
                    Activity.RESULT_CANCELED -> mDraggableAdapter.notifyDataSetChanged()
                }
            }
        }

        when (mMaxImages) {
            mAllList.size -> mHeaderFooterAdapter.removeFooterItem()
        }
    }

    /**
     * 弹窗选择按钮
     */
    private fun initDialog() {
        // RecyclerView条目点击事件
        mDialog = WYACustomDialog.Builder(this)
                .setLayoutId(R.layout.image_picker_choose) { v ->
                    val dialogCamera = v.findViewById<TextView>(R.id.dialog_camera)
                    val dialogPhoto = v.findViewById<TextView>(R.id.dialog_photo)
                    val dialogCancel = v.findViewById<TextView>(R.id.dialog_cancel)
                    dialogCamera.setOnClickListener {
                        mDialog.dismiss()
                        mMaxImages = Integer.parseInt(
                                when {
                                    TextUtils.isEmpty(mInput?.text.toString()) -> "1"
                                    else -> mInput?.text.toString()
                                })
                        checkPermissions()
                    }
                    dialogPhoto.setOnClickListener {
                        mDialog.dismiss()
                        mMaxImages = Integer.parseInt(
                                when {
                                    TextUtils.isEmpty(mInput?.text.toString()) -> "1"
                                    else -> mInput?.text.toString()
                                })
                        ImagePickerCreator.create(this@ImagePickerExampleActivity)
                                .maxImages(mMaxImages - mAllList.size)
                                .setMediaType(PickerConfig.MEDIA_DEFAULT)
                                .forResult(PHOTO)
                    }
                    dialogCancel.setOnClickListener { mDialog.dismiss() }
                }
                .cancelable(true)
                .gravity(Gravity.BOTTOM)
                .cancelTouchout(true)
                .build()

        mDialog.show()
    }

    @SuppressLint("CheckResult")
    private fun checkPermissions() {
        RxPermissions(this).request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        ).subscribe { aBoolean ->
            when {
                aBoolean -> startCamera()
                else -> Toast.makeText(this, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCamera() {
        val intent = Intent(this@ImagePickerExampleActivity, CameraExampleActivity::class.java)
        intent.putExtra("state", state)
        intent.putExtra("duration", 10 * 1000)
        startActivityForResult(intent, CAMERA)
    }

    companion object {

        const val CROP_PHOTO = 1001
        const val CROP = 1002
        const val PHOTO = 100
        const val TAKE_PHOTO = 101
        const val VIDEO = 102
        const val NO_PERMISSIONS_CAMEAR = 103
        const val CAMERA = 10001

    }
}
