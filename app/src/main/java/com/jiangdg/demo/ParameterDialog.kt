package com.jiangdg.demo

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import com.google.android.material.slider.Slider
import com.jiangdg.ausbc.utils.bus.BusKey
import com.jiangdg.ausbc.utils.bus.EventBus
import com.jiangdg.demo.databinding.DialogParamBinding

class ParameterDialog(
    fragment: DemoFragment
) :
    Dialog(fragment.requireContext()), Slider.OnChangeListener {

    private val mFragment: DemoFragment = fragment
    private val mParamBindingView: DialogParamBinding = mFragment.mParamBindingView

    init {

    }

    private fun initListener() {
        mParamBindingView.gainSlider2.addOnChangeListener(this)
        EventBus.with<Float>(BusKey.KEY_GAIN_VALUE).observe(mFragment) {
            mFragment.setGain(it.toInt())
            val gain = mFragment.getGain()
            mParamBindingView.gainText2.text = "gain: $gain"
        }
    }

    private fun initData() {
        val gain = mFragment.getGain().toFloat()
        mParamBindingView.gainSlider2.value = gain
        mParamBindingView.gainText2.text = "gain: $gain"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mParamBindingView.root)

        initData()
        initListener()
    }

    @SuppressLint("RestrictedApi")
    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        when (slider) {
            mParamBindingView.gainSlider2 -> {
                EventBus.with<Float>(BusKey.KEY_GAIN_VALUE).postMessage(value)
            }
        }
    }
}