package com.ebookfrenzy.withmap

import android.net.Uri
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

