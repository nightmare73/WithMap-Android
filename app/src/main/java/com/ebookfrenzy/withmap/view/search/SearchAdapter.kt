package com.ebookfrenzy.withmap.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ebookfrenzy.withmap.data.SearchLocationResult
import com.ebookfrenzy.withmap.databinding.RvItemSearchBinding

/**
 * Created By Yun Hyeok
 * on 9월 20, 2019
 */

class SearchAdapter : ListAdapter<SearchLocationResult, SearchAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemSearchBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = getItem(position)
        holder.apply {
            bind(location)
            itemView.tag = location
        }
    }


    class ViewHolder(private val binding: RvItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(location: SearchLocationResult) {
            binding.location = location
        }
    }

}

private class DiffCallback : DiffUtil.ItemCallback<SearchLocationResult>() {

    override fun areItemsTheSame(
        oldItem: SearchLocationResult,
        newItem: SearchLocationResult
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SearchLocationResult,
        newItem: SearchLocationResult
    ): Boolean {
        return oldItem == newItem
    }
}