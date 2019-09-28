package com.ebookfrenzy.withmap.view.pin.detail

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ebookfrenzy.withmap.util.PinType

/**
 * Created By Yun Hyeok
 * on 9월 28, 2019
 */

@BindingAdapter("bind_pin_icon")
fun bindingPinIcon(view: ImageView, pinType: Int) {
    val pinTypeRes = PinType.findById(pinType).resId
    view.setImageResource(pinTypeRes)
}
