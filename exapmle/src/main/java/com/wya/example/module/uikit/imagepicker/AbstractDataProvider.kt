package com.wya.example.module.uikit.imagepicker

abstract class AbstractDataProvider {

    abstract val count: Int

    abstract fun getItem(index: Int): Data

    abstract fun moveItem(fromPosition: Int, toPosition: Int)

    abstract fun swapItem(fromPosition: Int, toPosition: Int)

    abstract fun removeItem(position: Int)

    abstract fun undoLastRemoval(): Int

    abstract class Data {

        abstract val id: Long

        abstract fun isSectionHeader(): Boolean

        abstract val viewType: Int

        abstract val text: String

        abstract var isPinned: Boolean

        abstract val imageUrl: String?

    }

}
