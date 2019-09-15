package com.ebookfrenzy.withmap.view.main

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.data.MarkerItem
import com.ebookfrenzy.withmap.databinding.ActivityMainBinding
import com.ebookfrenzy.withmap.viewmodel.MainViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding
    private lateinit var markerRootView : View
    private lateinit var ivMarker : ImageView
    private var selectedMarker: Marker? = null
    private lateinit var vm : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        markerRootView = LayoutInflater.from(this).inflate(R.layout.marker_layout, null)
//        ivMarker = markerRootView.findViewById(R.id.iv_marker)
//        vm = ViewModelProviders.of(this@MainActivity)[MainViewModel::class.java]
//
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.run {
            lifecycleOwner = this@MainActivity
        }

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(R.id.fl_main_act, MainMapFragment())
        ft.commit()
//
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.frag_main_act) as SupportMapFragment
//        mapFragment.getMapAsync(this)
    }

//    override fun onMapReady(googleMap: GoogleMap?) {
//        mMap = googleMap!!
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.537523, 126.96558), 14f))
//        mMap.setOnMarkerClickListener(this)
//        mMap.setOnMapClickListener(this)
//
//        getSampleMarkerItems()
//    }
//
//    //샘플로 만든 마커들, +추가해놓기
//    fun getSampleMarkerItems() {
//
//        val sampleList = vm.markerItemLiveData.value
//
//        for (markerItem in sampleList!!.iterator()) {
//            addMarker(markerItem, false)
//        }
//    }
//
//    //마커를 추가해놓는 메소드
//    private fun addMarker(markerItem: MarkerItem, isSelectedMarker: Boolean): Marker {
//        val position = LatLng(markerItem.lat, markerItem.lon)
//
//        if (isSelectedMarker) {
//            when(markerItem.type) {
//                1 -> ivMarker.setBackgroundResource(R.drawable.pin_hurdle_touch)
//                2 -> ivMarker.setBackgroundResource(R.drawable.pin_dump_touch)
//                3 -> ivMarker.setBackgroundResource(R.drawable.pin_unpaved_touch)
//                4 -> ivMarker.setBackgroundResource(R.drawable.pin_narrow_touch)
//                5 -> ivMarker.setBackgroundResource(R.drawable.pin_toilet_touch)
//                6 -> ivMarker.setBackgroundResource(R.drawable.pin_restaurant_touch)
//            }
//        } else {
//            when(markerItem.type) {
//                1 -> ivMarker.setBackgroundResource(R.drawable.pin_hurdle_on)
//                2 -> ivMarker.setBackgroundResource(R.drawable.pin_dump_on)
//                3 -> ivMarker.setBackgroundResource(R.drawable.group_9)
//                4 -> ivMarker.setBackgroundResource(R.drawable.group_10)
//                5 -> ivMarker.setBackgroundResource(R.drawable.pin_toilet_on)
//                6 -> ivMarker.setBackgroundResource(R.drawable.pin_restaurant_on)
//            }
//        }
//
//        val markerOptions = MarkerOptions()
//        markerOptions.position(position)
//        markerOptions.icon(
//            BitmapDescriptorFactory.fromBitmap(
//                createDrawableFromView(
//                    this,
//                    markerRootView
//                )
//            )
//        )
//        val marker = mMap.addMarker(markerOptions)
//        marker.tag = markerItem
//
//        return marker
//    }
//
//    //View를 Bitmap으로 변환
//    private fun createDrawableFromView(context: Context, view: View): Bitmap {
//        val displayMetrics = DisplayMetrics()
//        (context as Activity).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
//        view.setLayoutParams(
//            ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//        )
//        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
//        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
//        view.buildDrawingCache()
//        val bitmap: Bitmap = Bitmap.createBitmap(
//            view.getMeasuredWidth(),
//            view.getMeasuredHeight(),
//            Bitmap.Config.ARGB_8888
//        )
//
//        val canvas = Canvas(bitmap)
//        view.draw(canvas)
//
//        return bitmap
//    }
//
//
//    //마커가 클릭되면 현재 클릭된 마커를 지도의 가운데로 이동시킨다
//    override fun onMarkerClick(marker: Marker): Boolean {
//        val center: CameraUpdate = CameraUpdateFactory.newLatLng(marker.position)
//        mMap.animateCamera(center)
//
//        changeSelectedMarker(marker)
//
//        return true
//    }
//
//    override fun onMapClick(p0: LatLng?) {
//        changeSelectedMarker(null)
//    }
//
//    private fun changeSelectedMarker(marker: Marker?) {
//        if (selectedMarker != null) {
//            addMarker(selectedMarker!!, false)
//            selectedMarker!!.remove()
//        }
//
//        if (marker != null) {
//            selectedMarker = addMarker(marker, true)
//            marker.remove()
//        }
//    }
//
//    private fun addMarker(marker: Marker, isSelectedMarker: Boolean): Marker {
//        val lat: Double = marker.position.latitude
//        val lon = marker.position.longitude
//        val extraMarkerData : MarkerItem = marker.tag as MarkerItem
//
//        val temp: MarkerItem = MarkerItem(lat, lon, extraMarkerData.type, extraMarkerData.title)
//
//        return addMarker(temp, isSelectedMarker)
//    }
}
