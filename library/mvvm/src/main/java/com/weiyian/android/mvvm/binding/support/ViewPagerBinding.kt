package com.weiyian.android.mvvm.binding.support

import android.databinding.BindingAdapter
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import arrow.core.toOption
import com.weiyian.android.mvvm.ext.arrow.whenEmpty
import com.weiyian.android.mvvm.functional.Consumer

@BindingAdapter(
        "bind_viewPager_fragmentManager",
        "bind_viewPager_fragments",
        "bind_viewPager_offScreenPageLimit", requireAll = false)
fun bindViewPagerAdapter(viewPager: ViewPager,
                         fragmentManager: FragmentManager,
                         fragments: List<Fragment>,
                         pageLimit: Int?) {
    viewPager.adapter
            .toOption()
            .whenEmpty {
                viewPager.adapter = ViewPagerAdapter(fragmentManager, fragments)
            }
    viewPager.offscreenPageLimit = pageLimit ?: DEFAULT_OFF_SCREEN_PAGE_LIMIT
}

@BindingAdapter(
        "bind_viewPager_onPageSelectedChanged",
        requireAll = false
)
fun bindOnPageChangeListener(viewPager: ViewPager,
                             onPageSelected: ViewPagerConsumer) =
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) = onPageSelected.accept(position)


            override fun onPageScrollStateChanged(state: Int) {

            }
        })

class ViewPagerAdapter(fragmentManager: FragmentManager,
                       private val fragments: List<Fragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(index: Int): Fragment = fragments[index]

    override fun getCount(): Int = fragments.size
}

const val DEFAULT_OFF_SCREEN_PAGE_LIMIT = 1

interface ViewPagerConsumer : Consumer<Int>