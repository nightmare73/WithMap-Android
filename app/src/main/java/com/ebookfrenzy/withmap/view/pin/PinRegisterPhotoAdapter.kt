package com.ebookfrenzy.withmap.view.pin

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentPinRegisterBinding
import com.googry.googrybaserecyclerview.BaseRecyclerView

class PinRegisterPhotoAdapter(val dataList : MutableList<Uri>) : BaseRecyclerView.Adapter<Uri, FragmentPinRegisterBinding>(
    layoutResId = R.layout.item_pin_register_photo
){

    inner class Holder(val parent : ViewParent) : Viewhol
}