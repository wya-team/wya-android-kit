package com.wya.example.module.uikit.imagepicker

import android.content.Context
import android.media.MediaMetadataRetriever
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.wya.example.R
import com.wya.uikit.advrecyclerview.draggable.DraggableItemAdapter
import com.wya.uikit.advrecyclerview.draggable.DraggableItemConstants
import com.wya.uikit.advrecyclerview.draggable.ItemDraggableRange
import com.wya.uikit.advrecyclerview.utils.AbstractDraggableItemViewHolder
import com.wya.uikit.imagepicker.SquareRelativeLayout
import java.util.*

/**
 * @author :
 */
class DraggableImagePickerAdapter(private val mContext: Context, private val images: List<String>, private val mProvider: AbstractDataProvider)
    : RecyclerView.Adapter<DraggableImagePickerAdapter.ImageViewHolder>(), DraggableItemAdapter<DraggableImagePickerAdapter.ImageViewHolder> {

    private lateinit var mOnItemClickListener: OnItemClickListener

    fun getImageList(): List<String> {
        val imageList = ArrayList<String>()
        for (i in 0 until mProvider.count) {
            val imageUrl = mProvider.getItem(i).imageUrl
            imageUrl?.let {
                imageList.add(it)
            }
        }
        return imageList
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return mProvider.getItem(position).id
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.picker_layout_item, viewGroup, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ImageViewHolder, position: Int) {
        val item = mProvider.getItem(position)

        val url = item.imageUrl
        val split = url!!.split("[.]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        // check is video TYPE
        when {
            isVideo(split[split.size - 1]) -> {
                val metadataRetriever = MediaMetadataRetriever()
                metadataRetriever.setDataSource(url)
                val duration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                viewHolder.videoMsg.visibility = View.VISIBLE
                viewHolder.videoDuration.text = dateFormate(duration)
                metadataRetriever.release()
            }
            else -> viewHolder.videoMsg.visibility = View.GONE
        }

        Glide.with(mContext).load(url).into(viewHolder.mImageView)
        viewHolder.mImageView.setOnClickListener {
            mOnItemClickListener.onItemClick(position)
        }

        viewHolder.delete.setOnClickListener {
            mOnItemClickListener.onDelete(position)
        }

        // drag
        val dragState = viewHolder.dragStateFlags
        when {
            dragState and DraggableItemConstants.STATE_FLAG_IS_UPDATED != 0 -> {
                val bgResId: Int = when {
                    dragState and DraggableItemConstants.STATE_FLAG_IS_ACTIVE != 0 -> R.drawable.bg_item_dragging_active_state
                    dragState and DraggableItemConstants.STATE_FLAG_DRAGGING != 0 -> R.drawable.bg_item_dragging_state
                    else -> R.drawable.bg_item_normal_state
                }

                viewHolder.mContainer.setBackgroundResource(bgResId)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return IMG
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    private fun dateFormate(duration: String): String {
        val time = java.lang.Long.parseLong(duration)
        var min = (time / 60000).toString()
        var sec = (time % 60000 / 1000).toString()
        min = when {
            min.length == 1 -> String.format("%s%s", "0", min)
            else -> min
        }
        sec = when {
            sec.length == 1 -> String.format("%s%s", "0", sec)
            else -> sec
        }
        return String.format("%s%s%s", min, ":", sec)
    }

    private fun isVideo(mediaType: String): Boolean {
        return TYPE.contains(mediaType.toUpperCase())
    }

    override fun onCheckCanStartDrag(holder: ImageViewHolder, position: Int, x: Int, y: Int): Boolean {
        return true
    }

    override fun onGetItemDraggableRange(holder: ImageViewHolder, position: Int): ItemDraggableRange? {
        return null
    }

    override fun onMoveItem(fromPosition: Int, toPosition: Int) {
        mProvider.moveItem(fromPosition, toPosition)
    }

    override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int): Boolean {
        return true
    }

    override fun onItemDragStarted(position: Int) {
        notifyDataSetChanged()
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(itemView: View) : AbstractDraggableItemViewHolder(itemView) {

        var mImageView: ImageView = itemView.findViewById(R.id.iv_add)
        var delete: LinearLayout = itemView.findViewById(R.id.delete)
        var videoMsg: LinearLayout = itemView.findViewById(R.id.video_msg)
        var videoDuration: TextView = itemView.findViewById(R.id.video_duration)
        var mContainer: SquareRelativeLayout = itemView.findViewById(R.id.container)

    }

    interface OnItemClickListener {

        /**
         * onDelete
         *
         * @param position :
         */
        fun onDelete(position: Int)

        /**
         * onItemClick
         *
         * @param position :
         */
        fun onItemClick(position: Int)

    }

    companion object {

        private const val IMG = 0
        private const val TYPE = "MPEG/MPG/DAT/AVI/MOV/ASF/WMV/NAVI/3GP/MKV/FLV/F4V/RMVB/WEBM/MP4"

    }

}
