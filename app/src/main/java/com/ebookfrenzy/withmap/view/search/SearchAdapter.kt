package com.ebookfrenzy.withmap.view.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ebookfrenzy.withmap.data.SearchLocationResult
import com.ebookfrenzy.withmap.databinding.RvItemSearchBinding
import com.ebookfrenzy.withmap.view.main.MainMapFragmentDirections
import com.ebookfrenzy.withmap.viewmodel.SearchViewModel

/**
 * Created By Yun Hyeok
 * on 9월 20, 2019
 */

class SearchAdapter : ListAdapter<SearchLocationResult, SearchAdapter.ViewHolder>(DiffCallback()) {


    private var viewModel: SearchViewModel? = null

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
            bind(location, createClickListener(location))
            itemView.tag = location
        }
    }

    private fun createClickListener(location: SearchLocationResult) = View.OnClickListener {
        viewModel?.setSelectedLocation(location)
        //Navigation.findNavController(it).popBackStack()
        Log.d("Malibin Debug", "clicked  ${location.name}")
        val direction = SearchFragmentDirections.actionSearchFragmentToMainMapFragment(location)
        Navigation.findNavController(it).navigate(direction)
    }

    fun setViewModel(viewModel: SearchViewModel) {
        this.viewModel = viewModel
    }

    class ViewHolder(private val binding: RvItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(location: SearchLocationResult, listener: View.OnClickListener) {
            binding.location = location
            binding.selectClick = listener
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