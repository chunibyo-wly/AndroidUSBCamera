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

        mParamBindingView.brightnessSlider.addOnChangeListener(this)
        EventBus.with<Float>(BusKey.KEY_BRIGHTNESS_VALUE).observe(mFragment) {
            mFragment.setBrightness(it.toInt())
            val brightness = mFragment.getBrightness()
            mParamBindingView.brightnessText.text = "brightness: $brightness"
        }

        mParamBindingView.gammaSlider.addOnChangeListener(this)
        EventBus.with<Float>(BusKey.KEY_GAMMA_VALUE).observe(mFragment) {
            mFragment.setGamma(it.toInt())
            val gamma = mFragment.getGamma()
            mParamBindingView.gammaText.text = "gamma: $gamma"
        }

        mParamBindingView.autoWhiteBalanceCheckBox.setOnCheckedChangeListener { _, _ ->
            mFragment.setAutoWhiteBalance(mParamBindingView.autoWhiteBalanceCheckBox.isChecked)
        }

        mParamBindingView.whiteBalanceSlider.addOnChangeListener(this)
        EventBus.with<Float>(BusKey.KEY_WHITE_BALANCE_VALUE).observe(mFragment) {
            mFragment.setWhiteBalance(it.toInt())
            val whiteBalance = mFragment.getWhiteBalance()
            mParamBindingView.whiteBalanceText.text = "whiteBalance: $whiteBalance"
        }
    }

    private fun initData() {
        val gain = mFragment.getGain().toFloat()
        mParamBindingView.gainSlider2.value = gain
        mParamBindingView.gainText2.text = "gain: $gain"

        val brightness = mFragment.getBrightness().toFloat()
        mParamBindingView.brightnessSlider.value = brightness
        mParamBindingView.brightnessText.text = "brightness: $brightness"

        val gamma = mFragment.getGamma().toFloat()
        mParamBindingView.gammaSlider.value = gamma
        mParamBindingView.gammaText.text = "gamma: $gamma"

        mParamBindingView.autoWhiteBalanceCheckBox.isChecked = mFragment.getAutoWhiteBalance()

        val whiteBalance = mFragment.getWhiteBalance().toFloat()
        mParamBindingView.whiteBalanceSlider.value = whiteBalance
        mParamBindingView.whiteBalanceText.text = "whiteBalance: $whiteBalance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mParamBindingView.root)

        initData()
        initListener()
    }

    @SuppressLint("RestrictedApi")
    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        var busKey = ""
        when (slider) {
            mParamBindingView.gainSlider2 -> {
                busKey = BusKey.KEY_GAIN_VALUE
            }
            mParamBindingView.brightnessSlider -> {
                busKey = BusKey.KEY_BRIGHTNESS_VALUE
            }
            mParamBindingView.gammaSlider -> {
                busKey = BusKey.KEY_GAMMA_VALUE
            }
            mParamBindingView.whiteBalanceSlider -> {
                busKey = BusKey.KEY_WHITE_BALANCE_VALUE
            }
        }
        EventBus.with<Float>(busKey).postMessage(value)
    }
}