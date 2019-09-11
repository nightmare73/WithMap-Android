package com.ebookfrenzy.withmap.view.pin


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.loader.content.CursorLoader
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ebookfrenzy.withmap.BR
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.FragmentPinRegisterBinding
import com.ebookfrenzy.withmap.databinding.ItemPinRegisterPhotoBinding
import com.ebookfrenzy.withmap.viewmodel.PinRegisterViewModel
import com.googry.googrybaserecyclerview.BaseRecyclerView
import kotlinx.android.synthetic.main.fragment_pin_register.*

/**
 * A simple [Fragment] subclass.
 */
class PinRegisterFragment(val f: Int) : Fragment() {

    val improveType = 2
    var isNew : Boolean = true

    private val REQUEST_CODE_SELECT_IMAGE = 1111
    private val MY_READ_STORAGE_REQUEST_CODE = 7777
    private val TAG = "PinRegisterFragment"

    var imageURI: String? = null
    var imageUris = mutableListOf<Uri>()

    private val pinRegisterPhotoAdapter = PinRegisterPhotoAdapter()
    val viewModel = PinRegisterViewModel()
    private lateinit var binding: FragmentPinRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        isNew = isNewOrImproved(f)
        binding = FragmentPinRegisterBinding.inflate(LayoutInflater.from(this.context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            lifecycleOwner = this@PinRegisterFragment
            fragment = this@PinRegisterFragment
            rvPhoto.run {
                adapter = pinRegisterPhotoAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }

        initDataBinding()
        improvedTypePick(improveType)
        Log.d(TAG, binding.etTitle.text.toString())

    }
    fun isNewOrImproved(i : Int) : Boolean{
        when(i) {
            1 -> return true
            else -> return false
        }
    }

    //BindingAdapter
    fun improvedTypePick(i: Int) {
        if (f == 2) {
            when (i) {
                1 -> {
                    binding.ivObstacle.setImageResource(R.drawable.pin_hurdle_on)
                    binding.tvObstacle.setTextColor(resources.getColor(R.color.orange))
                }
                2 -> {
                    binding.ivDump.setImageResource(R.drawable.pin_dump_on)
                    binding.tvDump.setTextColor(resources.getColor(R.color.orange))

                }
                3 -> {
                    binding.ivUnpaved.setImageResource(R.drawable.group_9)
                    binding.tvUnpaved.setTextColor(resources.getColor(R.color.orange))
                }
                4 -> {
                    binding.ivNarrow.setImageResource(R.drawable.group_10)
                    binding.tvNarrow.setTextColor(resources.getColor(R.color.orange))

                }
                5 -> {
                    binding.ivToilet.setImageResource(R.drawable.pin_toilet_on)
                    binding.tvToilet.setTextColor(resources.getColor(R.color.blue))

                }
                6 -> {
                    binding.ivRestaurant.setImageResource(R.drawable.pin_restaurant_on)
                    binding.tvRestaurant.setTextColor(resources.getColor(R.color.blue))

                }
            }
        }
    }

    fun typeAllOff() {
        binding.ivObstacle.setImageResource(R.drawable.pin_hurdle_off)
        binding.ivDump.setImageResource(R.drawable.pin_dump_off)
        binding.ivUnpaved.setImageResource(R.drawable.pin_unpaved_off)
        binding.ivNarrow.setImageResource(R.drawable.pin_narrow_off)
        binding.ivToilet.setImageResource(R.drawable.pin_toilet_off)
        binding.ivRestaurant.setImageResource(R.drawable.pin_restaurant_off)


        binding.tvObstacle.setTextColor(resources.getColor(R.color.et_text_gray))
        binding.tvDump.setTextColor(resources.getColor(R.color.et_text_gray))
        binding.tvUnpaved.setTextColor(resources.getColor(R.color.et_text_gray))
        binding.tvNarrow.setTextColor(resources.getColor(R.color.et_text_gray))
        binding.tvToilet.setTextColor(resources.getColor(R.color.et_text_gray))
        binding.tvRestaurant.setTextColor(resources.getColor(R.color.et_text_gray))

    }

    fun pickType(i: Int) {
        typeAllOff()
        if (f == 1) {
            when (i) {
                1 -> {
                    binding.ivObstacle.setImageResource(R.drawable.pin_hurdle_on)
                    binding.tvObstacle.setTextColor(resources.getColor(R.color.orange))
                }
                2 -> {
                    binding.ivDump.setImageResource(R.drawable.pin_dump_on)
                    binding.tvDump.setTextColor(resources.getColor(R.color.orange))

                }
                3 -> {
                    binding.ivUnpaved.setImageResource(R.drawable.group_9)
                    binding.tvUnpaved.setTextColor(resources.getColor(R.color.orange))
                }
                4 -> {
                    binding.ivNarrow.setImageResource(R.drawable.group_10)
                    binding.tvNarrow.setTextColor(resources.getColor(R.color.orange))

                }
                5 -> {
                    binding.ivToilet.setImageResource(R.drawable.pin_toilet_on)
                    binding.tvToilet.setTextColor(resources.getColor(R.color.blue))

                }
                6 -> {
                    binding.ivRestaurant.setImageResource(R.drawable.pin_restaurant_on)
                    binding.tvRestaurant.setTextColor(resources.getColor(R.color.blue))

                }
            }
        }
    }


    fun requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {

            } else {
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_READ_STORAGE_REQUEST_CODE
                )
            }
        } else {
            showAlbum()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_READ_STORAGE_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showAlbum()
            } else {
                //finish()
            }
        }
    }

    private fun showAlbum() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
            data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "onActivityResult")
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val selectedImageUri: Uri = data.data
                    imageURI = getRealPathFromURI(selectedImageUri)

                    imageUris.add(selectedImageUri)
                    Log.d(TAG, "imageUris size : ${imageUris.size}")
                    //한 라이브데이타에 넣어주기
                    viewModel.albumImageListLiveData.postValue(imageUris)
                    Log.d(TAG, "imageUri : ${viewModel.albumImageListLiveData.value}")

                }
            }
        }
    }

    private fun initDataBinding() {
        viewModel.albumImageListLiveData.observe(this, Observer {
            it.forEach {
                pinRegisterPhotoAdapter.addUri(it)
                Log.d(TAG, "added uri : ${it}")
            }
            pinRegisterPhotoAdapter.notifyDataSetChanged()
        })
    }

    fun getRealPathFromURI(content: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader: CursorLoader = CursorLoader(context!!, content, proj, null, null, null)
        val cursor: Cursor? = loader.loadInBackground()
        val column_idx =
            cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_idx)

        cursor.close()
        return result
    }
}






