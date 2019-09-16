package com.ebookfrenzy.withmap.view.main


import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders

import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.data.MarkerItem
import com.ebookfrenzy.withmap.databinding.ActivityMainBinding
import com.ebookfrenzy.withmap.databinding.FragmentMainMapBinding
import com.ebookfrenzy.withmap.viewmodel.MainViewModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_after.*
import kotlinx.android.synthetic.main.bottom_sheet_after.view.*
import kotlinx.android.synthetic.main.bottom_sheet_before.*
import kotlinx.android.synthetic.main.bottom_sheet_before.view.*

/**
 * A simple [Fragment] subclass.
 */
class MainMapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMapClickListener {

    private lateinit var bottomSheetLayout : View
    private lateinit var bottomSheetLayoutImproved : View
    private lateinit var persistentBottomSheetBehavior: BottomSheetBehavior<*>

    private lateinit var mapFragment: SupportMapFragment

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMainMapBinding
    private lateinit var markerRootView: View
    private lateinit var ivMarker: ImageView
    private var selectedMarker: Marker? = null
    private lateinit var vm: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        markerRootView = LayoutInflater.from(this.context).inflate(R.layout.marker_layout, null)
        ivMarker = markerRootView.findViewById(R.id.iv_marker)
        vm = ViewModelProviders.of(this)[MainViewModel::class.java]

        binding = FragmentMainMapBinding.inflate(LayoutInflater.from(this.context))
        binding.run {
            lifecycleOwner = this@MainMapFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = SupportMapFragment.newInstance()
        mapFragment.getMapAsync(this)

        childFragmentManager.beginTransaction().replace(R.id.fl_main_map_frag, mapFragment).commit()
    }

    private fun initPersistentBottonSheetBehavior(markerItem: MarkerItem) {

        if (!markerItem.improved) {
            bottomSheetLayout = bottom_sheet_before
            persistentBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)


            if (persistentBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetLayout.tv_title_sheet_before.text = markerItem.title
                bottomSheetLayout.tv_date_sheet_before.text = markerItem.date
                bottomSheetLayout.tv_location_sheet_before.text = markerItem.location
            } else {
                persistentBottomSheetBehavior.setBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {

                    }

                    override fun onStateChanged(view: View, newState: Int) {
                        when (newState) {
                            BottomSheetBehavior.STATE_EXPANDED -> {
                                view.tv_title_sheet_before.text = markerItem.title
                                view.tv_date_sheet_before.text = markerItem.date
                                view.tv_location_sheet_before.text = markerItem.location
                            }
                        }
                    }
                })

                persistentBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        } else {
            bottomSheetLayoutImproved = bottom_sheet_after
            persistentBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayoutImproved)


            if (persistentBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetLayoutImproved.tv_title_before.text = markerItem.title
                bottomSheetLayoutImproved.tv_date_before.text = markerItem.date
                bottomSheetLayoutImproved.tv_title_after.text = markerItem.improvedTitle
                bottomSheetLayoutImproved.tv_date_after.text = markerItem.improvedDate

            } else {
                persistentBottomSheetBehavior.setBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {

                    }

                    override fun onStateChanged(view: View, newState: Int) {
                        when (newState) {
                            BottomSheetBehavior.STATE_EXPANDED -> {
                                view.tv_title_before.text = markerItem.title
                                view.tv_date_before.text = markerItem.date
                                view.tv_title_after.text = markerItem.improvedTitle
                                view.tv_date_after.text = markerItem.improvedDate
                            }
                        }
                    }
                })

                persistentBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.537523, 126.96558), 14f))
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)

        getSampleMarkerItems()
    }

    //샘플로 만든 마커들, +추가해놓기
    fun getSampleMarkerItems() {

        val sampleList = vm.markerItemLiveData.value

        for (markerItem in sampleList!!.iterator()) {
            addMarker(markerItem, false)
        }
    }

    //마커를 추가해놓는 메소드
    private fun addMarker(markerItem: MarkerItem, isSelectedMarker: Boolean): Marker {
        val position = LatLng(markerItem.lat, markerItem.lon)

        if (!markerItem.improved) {
            //개선되기전
            if (isSelectedMarker) {
                when (markerItem.type) {
                    1 -> ivMarker.setBackgroundResource(R.drawable.pin_hurdle_touch)
                    2 -> ivMarker.setBackgroundResource(R.drawable.pin_dump_touch)
                    3 -> ivMarker.setBackgroundResource(R.drawable.pin_unpaved_touch)
                    4 -> ivMarker.setBackgroundResource(R.drawable.pin_narrow_touch)
                    5 -> ivMarker.setBackgroundResource(R.drawable.pin_toilet_touch)
                    6 -> ivMarker.setBackgroundResource(R.drawable.pin_restaurant_touch)
                }
            } else {
                when (markerItem.type) {
                    1 -> ivMarker.setBackgroundResource(R.drawable.pin_hurdle_on)
                    2 -> ivMarker.setBackgroundResource(R.drawable.pin_dump_on)
                    3 -> ivMarker.setBackgroundResource(R.drawable.group_9)
                    4 -> ivMarker.setBackgroundResource(R.drawable.group_10)
                    5 -> ivMarker.setBackgroundResource(R.drawable.pin_toilet_on)
                    6 -> ivMarker.setBackgroundResource(R.drawable.pin_restaurant_on)
                }
            }
        } else {
            //개선된 후
            when (markerItem.type) {

                1 -> ivMarker.setBackgroundResource(R.drawable.pin_hurdle_off)
                2 -> ivMarker.setBackgroundResource(R.drawable.pin_dump_off)
                3 -> ivMarker.setBackgroundResource(R.drawable.pin_unpaved_off)
                4 -> ivMarker.setBackgroundResource(R.drawable.pin_narrow_off)
                5 -> ivMarker.setBackgroundResource(R.drawable.pin_toilet_off)
                6 -> ivMarker.setBackgroundResource(R.drawable.pin_restaurant_off)
            }
        }


        val markerOptions = MarkerOptions()
        markerOptions.position(position)
        markerOptions.icon(
            BitmapDescriptorFactory.fromBitmap(
                createDrawableFromView(
                    this.context!!,
                    markerRootView
                )
            )
        )
        val marker = mMap.addMarker(markerOptions)
        marker.tag = markerItem

        return marker
    }


    //View를 Bitmap으로 변환
    private fun createDrawableFromView(context: Context, view: View): Bitmap {
        val displayMetrics = DisplayMetrics()
        (context as Activity).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        view.setLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap: Bitmap = Bitmap.createBitmap(
            view.getMeasuredWidth(),
            view.getMeasuredHeight(),
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        view.draw(canvas)

        return bitmap
    }

    //마커가 클릭되면 현재 클릭된 마커를 지도의 가운데로 이동시킨다
    override fun onMarkerClick(marker: Marker): Boolean {
        val center: CameraUpdate = CameraUpdateFactory.newLatLng(marker.position)
        mMap.animateCamera(center)

        changeSelectedMarker(marker)

        return true
    }

    override fun onMapClick(p0: LatLng?) {
        changeSelectedMarker(null)
    }

    private fun changeSelectedMarker(marker: Marker?) {

        //BottomSheet 등장


        //직전에 선택된 핀이 있는 경우
        if (selectedMarker != null) {
            addMarker(selectedMarker!!, false)
            selectedMarker!!.remove()
        }
        //직전에 다른 핀이 선택되지 않은 상황
        if (marker != null) {
            selectedMarker = addMarker(marker, true)
            marker.remove()
        }
    }


    private fun addMarker(marker: Marker, isSelectedMarker: Boolean): Marker {
        val lat: Double = marker.position.latitude
        val lon = marker.position.longitude
        val extraMarkerData: MarkerItem = marker.tag as MarkerItem

        val temp: MarkerItem = MarkerItem(
            lat,
            lon,
            extraMarkerData.type,
            extraMarkerData.title,
            extraMarkerData.date,
            extraMarkerData.location,
            extraMarkerData.improved,
            extraMarkerData.improvedTitle,
            extraMarkerData.improvedDate
        )
        initPersistentBottonSheetBehavior(marker.tag as MarkerItem)

        return addMarker(temp, isSelectedMarker)
    }
}
