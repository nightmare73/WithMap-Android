package com.ebookfrenzy.withmap.view.pin.detail

import android.view.View
import android.view.ViewGroup
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

@BindingAdapter("bind_info_type")
fun bindingInfoType(view: ViewGroup, pinType: Int) {
    when (PinType.findById(pinType)) {
        PinType.OBSTACLE,
        PinType.CURB,
        PinType.UNPAVED_ROAD,
        PinType.NARROW_ROAD -> {
            if (view.tag == "orange") {
                view.visibility = View.VISIBLE
                return
            }
            view.visibility = View.GONE
        }

        PinType.TOILET,
        PinType.RESTAURANT -> {
            if (view.tag == "blue") {
                view.visibility = View.VISIBLE
                return
            }
            view.visibility = View.GONE
        }
    }
}
