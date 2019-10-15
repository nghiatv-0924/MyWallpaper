package com.sun.mywallpaper.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected abstract val layoutResource: Int
    protected abstract val viewModel: VM
    private lateinit var viewDataBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performDataBinding()
        setBindingVariables()
        initComponents()
        observeData()
    }

    protected open fun setBindingVariables() {
        viewModel.create()
    }

    protected abstract fun initComponents()

    protected abstract fun observeData()

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutResource)
    }
}
