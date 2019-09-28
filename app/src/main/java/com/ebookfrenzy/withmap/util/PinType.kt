package com.ebookfrenzy.withmap.util

import androidx.annotation.DrawableRes
import com.ebookfrenzy.withmap.R

/**
 * Created By Yun Hyeok
 * on 9월 27, 2019
 */

enum class PinType(val id: Int, @DrawableRes val resId: Int) {

    OBSTACLE(0, R.drawable.pin_hurdle_on),
    CURB(1, R.drawable.pin_dump_on),
    UNPAVED_ROAD(2, R.drawable.pin_unpaved_on),
    NARROW_ROAD(3, R.drawable.pin_narrow_on),
    TOILET(4, R.drawable.pin_toilet_on),
    RESTAURANT(5, R.drawable.pin_restaurant_on);


    companion object {
        fun findById(id: Int): PinType {
            return values().first { it.id == id }
        }
    }
}

