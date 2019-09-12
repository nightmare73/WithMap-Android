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

