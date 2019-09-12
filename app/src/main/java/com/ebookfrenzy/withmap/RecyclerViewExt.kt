package com.ebookfrenzy.withmap

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.googry.googrybaserecyclerview.BaseRecyclerView

@Suppress("UNCHECKED_CAST")
@BindingAdapter("replaceAll")
fun RecyclerView.replaceAll(list: List<Any>?) {
    (this.adapter as? BaseRecyclerView.Adapter<Any, *>)?.run {
        replaceAll(list)
        notifyDataSetChanged()
    }
}

@BindingAdapter("typeImage")
fun ImageView.typeImage(i : Int) {
    when(i) {
        1 -> this.setImageResource(R.drawable.group)
        2 -> this.setImageResource(R.drawable.group_2)
        3 -> this.setImageResource(R.drawable.group_3)
    }
}

@BindingAdapter("typeImage_my_register")
fun ImageView.typeImageMyRegister(i : Int) {
    when(i) {
        1 -> this.setImageResource(R.drawable.pin_hurdle_on)
        2 -> this.setImageResource(R.drawable.pin_dump_on)
        3 -> this.setImageResource(R.drawable.group_9)
        4 -> this.setImageResource(R.drawable.group_10)
        5 -> this.setImageResource(R.drawable.pin_toilet_on)
        6 -> this.setImageResource(R.drawable.pin_restaurant_on)
    }
}

