package com.weiyian.android.mvvm.base.view.fragment

import android.arch.lifecycle.ViewModel
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weiyian.android.mvvm.BR

abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel> : InjectionFragment() {

    private var rootView: View? = null
    abstract val layoutId: Int
    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        rootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return rootView!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initBinding(view)
        initView()
    }

    open fun initBinding(rootView: View) {
        binding = DataBindingUtil.bind(rootView)!!
        with(binding) {
            setVariable(BR.viewModel, viewModel)
            lifecycleOwner = this@BaseFragment
        }
    }

    open fun initView() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rootView = null
        binding.unbind()
    }

    abstract fun initViewModel()

}
