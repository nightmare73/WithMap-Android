package com.ebookfrenzy.withmap.view.pin

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentPinRegisterBinding
import com.ebookfrenzy.withmap.databinding.ItemPinRegisterPhotoBinding
import com.googry.googrybaserecyclerview.BaseRecyclerView
import kotlinx.android.synthetic.main.item_pin_register_photo.view.*

class PinRegisterPhotoAdapter: RecyclerView.Adapter<PinRegisterPhotoAdapter.Holder>(){

    private val uriList = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPinRegisterPhotoBinding.inflate(inflater, parent, false)

        return Holder(parent)
    }

    override fun getItemCount(): Int = uriList.size

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.onBind(uriList[position])

    inner class Holder(val parent : ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_pin_register_photo, parent, false)
    ){
        fun onBind(item : Uri?) {
            itemView.run{
                Glide.with(this@Holder.parent.context)
                    .load(item)
                    .into(iv_photo)
            }
        }
    }

    fun addUri(uri : Uri) {
        uriList.add(uri)
    }
}