package com.ebookfrenzy.withmap.view.main


import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.data.MarkerItem
import com.ebookfrenzy.withmap.databinding.FragmentMainMapBinding
import com.ebookfrenzy.withmap.viewmodel.MainViewModel
import com.ebookfrenzy.withmap.viewmodel.NotificationViewModel
import com.ebookfrenzy.withmap.viewmodel.hamSetImage
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.bottom_sheet_after.*
import kotlinx.android.synthetic.main.bottom_sheet_after.view.*
import kotlinx.android.synthetic.main.bottom_sheet_before.*
import kotlinx.android.synthetic.main.bottom_sheet_before.view.*
import kotlinx.android.synthetic.main.fragment_main_map.*

/**
 * A simple [Fragment] subclass.
 */
class MainMapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMapClickListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mLoc: LatLng

    private lateinit var bottomSheetLayoutImproved: View
    private var persistentBottomSheetBehavior: BottomSheetBehavior<*>? = null

    private lateinit var mapFragment: SupportMapFragment

    private lateinit var headerView: View
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMainMapBinding

    private lateinit var markerRootView: View
    private lateinit var ivMarker: ImageView
    private lateinit var vm: MainViewModel
    private lateinit var vmNoti: NotificationViewModel

    var bottomSheetLayout: View? = null


    private val TAG = "MainMapFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        markerRootView = LayoutInflater.from(this.context).inflate(R.layout.marker_layout, null)
        ivMarker = markerRootView.findViewById(R.id.iv_marker)
        vm = ViewModelProviders.of(this)[MainViewModel::class.java]
        vmNoti = ViewModelProviders.of(this)[NotificationViewModel::class.java]

        binding = FragmentMainMapBinding.inflate(LayoutInflater.from(this.context))


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bt_hamburger.setOnClickListener {
            drawer_layout.openDrawer(nav_view)
        }

        setHeader(nav_view)

        binding.run {
            lifecycleOwner = this@MainMapFragment
            vmNoti = ViewModelProviders.of(this@MainMapFragment)[NotificationViewModel::class.java]
        }

        vm.bottomSheetUpdate.observe(this, Observer {
            Log.d(TAG, "bottomSheetUpdate in MainMapFragment : ${vm.bottomSheetUpdate.value}")
            initPersistentBottonSheetBehavior(
                vm.selectedMarkerLiveData.value!!.tag as MarkerItem,
                vm.bottomSheetUpdate.value!!
            )
        })

        mapFragment = SupportMapFragment.newInstance()
        mapFragment.getMapAsync(this)

        childFragmentManager.beginTransaction().replace(R.id.fl_main_map_frag, mapFragment).commit()

        Log.d(TAG, vm.selectedMarkerLiveData.value.toString())
    }

    fun setHeader(view_navi: NavigationView) {
        headerView = view_navi.getHeaderView(0)

        val headerAlarm: ImageView = headerView.findViewById(R.id.header_alarm)
        val headerAccount: ConstraintLayout = headerView.findViewById(R.id.rl_account)
        val headerExchange: Button = headerView.findViewById(R.id.bt_exchange)

        headerExchange.setOnClickListener {
            Log.d(TAG, "go to TradeFragment")
            it.findNavController().navigate(R.id.action_mainMapFragment_to_tradeFragment)
        }
        headerAccount.setOnClickListener {
            Log.d(TAG, "go to Account Manager")
            it.findNavController().navigate(R.id.action_mainMapFragment_to_accountFragment)
        }
        headerAlarm.setOnClickListener {
            Log.d(TAG, "go to Notification Fragment")
            it.findNavController().navigate(R.id.action_mainMapFragment_to_notificationFragment)
        }

        val myPinRegister: RelativeLayout = headerView.findViewById(R.id.rl_my_pin)
        myPinRegister.setOnClickListener {
            Log.d(TAG, "layout clicked")
            it.findNavController().navigate(R.id.action_mainMapFragment_to_myRegisterPinFragment)
        }

        vmNoti.notificationLiveData.observe(this, Observer {
            hamSetImage(headerAlarm, it)
        })


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

    //샘플로 만든 마커들, +추가해놓기
    fun getSampleMarkerItems() {

        val sampleList = vm.markerItemLiveData.value
        Log.d(TAG, sampleList.toString())

        for (markerItem in sampleList!!.iterator()) {
            addMarker(markerItem)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        mLoc = LatLng(37.537523, 126.96558)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLoc, 14f))
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)

        getSampleMarkerItems()
    }

    //마커를 추가해놓는 함수
    private fun addMarker(markerItem: MarkerItem): Marker {
        val position = LatLng(markerItem.latitude, markerItem.longitude)

        if (!markerItem.improved) {
            //개선되기전
//            if (isSelectedMarker) {
//                when (markerItem.type) {
//                    1 -> ivMarker.setBackgroundResource(R.drawable.pin_hurdle_touch)
//                    2 -> ivMarker.setBackgroundResource(R.drawable.pin_dump_touch)
//                    3 -> ivMarker.setBackgroundResource(R.drawable.pin_unpaved_touch)
//                    4 -> ivMarker.setBackgroundResource(R.drawable.pin_narrow_touch)
//                    5 -> ivMarker.setBackgroundResource(R.drawable.pin_toilet_touch)
//                    6 -> ivMarker.setBackgroundResource(R.drawable.pin_restaurant_touch)
//                }
//            } else {
            when (markerItem.type) {
                1 -> ivMarker.setBackgroundResource(R.drawable.pin_hurdle_on)
                2 -> ivMarker.setBackgroundResource(R.drawable.pin_dump_on)
                3 -> ivMarker.setBackgroundResource(R.drawable.group_9)
                4 -> ivMarker.setBackgroundResource(R.drawable.group_10)
                5 -> ivMarker.setBackgroundResource(R.drawable.pin_toilet_on)
                6 -> ivMarker.setBackgroundResource(R.drawable.pin_restaurant_on)
            }
//            }
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

    //마커가 클릭되면 현재 클릭된 마커를 지도의 가운데로 이동시킨다
    override fun onMarkerClick(marker: Marker): Boolean {
        lateinit var center: CameraUpdate
        mLoc = marker.position
        center = CameraUpdateFactory.newLatLng(mLoc)
        mMap.animateCamera(center)

        changeSelectedMarker(marker)

        return true
    }

    private fun changeSelectedMarker(marker: Marker?) {
        Log.d(TAG, "new selected : ${marker!!.tag}")

        //개선되지 않은 핀: 선택된 마커 이미지 변경
        if(!(marker.tag as MarkerItem).improved) {
            val selectedMarkerRootView =
                LayoutInflater.from(this.context).inflate(R.layout.marker_layout, null)
            val selectedIvMarker: ImageView = selectedMarkerRootView.findViewById(R.id.iv_marker)
            when ((marker.tag as MarkerItem).type) {
                1 -> selectedIvMarker.setBackgroundResource(R.drawable.pin_hurdle_touch)
                2 -> selectedIvMarker.setBackgroundResource(R.drawable.pin_dump_touch)
                3 -> selectedIvMarker.setBackgroundResource(R.drawable.pin_unpaved_touch)
                4 -> selectedIvMarker.setBackgroundResource(R.drawable.pin_narrow_touch)
                5 -> selectedIvMarker.setBackgroundResource(R.drawable.pin_toilet_touch)
                6 -> selectedIvMarker.setBackgroundResource(R.drawable.pin_restaurant_touch)
            }
            marker.setIcon(
                BitmapDescriptorFactory.fromBitmap(
                    createDrawableFromView(
                        this.context!!,
                        selectedMarkerRootView
                    )
                )
            )
        }

        if (vm.selectedMarkerLiveData.value != null) {
            if(vm.selectedMarkerLiveData.value!!.tag != null) {
                addMarker(vm.selectedMarkerLiveData.value!!.tag as MarkerItem)
                vm.selectedMarkerLiveData.value!!.remove()
            }
        }
        //선택된 마커를 selectedMarker로 '새로'지정해서 지도에 추가
        vm.selectedMarkerLiveData.value = marker
    }


    //BottomSheet발생시키는 함수
    private fun initPersistentBottonSheetBehavior(markerItem: MarkerItem, needUpdate: Boolean) {
        val mMarkerItem = markerItem

        var h: Int = 1
        var off: Float = 0f
        Log.d(
            TAG,
            "-----initPersistentBottomSheetBehavior()-----selectedMarkerItem : $markerItem  $mMarkerItem"
        )

        if (needUpdate) {
            if (persistentBottomSheetBehavior != null) {
                persistentBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            if (!markerItem.improved) {
                Log.d(TAG, "!markerItem.improved")
                bottomSheetLayout = bottom_sheet_before

                if (markerItem.type == 5 || markerItem.type == 6) {
                    Log.d(TAG, "marker type is 5 or 6")
                    bottomSheetLayout!!.bt_was_improved.visibility = View.GONE
                    bottomSheetLayout!!.bt_show_detail.visibility = View.GONE
                    bottomSheetLayout!!.bt_show_detail_blue.visibility = View.VISIBLE

                    bottomSheetLayout!!.bt_show_detail_blue.setOnClickListener {
                        it.findNavController()
                            .navigate(R.id.action_mainMapFragment_to_pinDetailFragment)
                        Log.d(TAG, "bt_show_detail_blue clicked")
                    }
                }else {

                    bottomSheetLayout!!.bt_was_improved.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putParcelable("item", markerItem as Parcelable)
                        it.findNavController()
                            .navigate(R.id.action_mainMapFragment_to_pinRegisterFragment, bundle)
                        Log.d(TAG, "bt_was_improved clicked")
                    }
                    bottomSheetLayout!!.bt_show_detail.setOnClickListener {
                        it.findNavController()
                            .navigate(R.id.action_mainMapFragment_to_pinDetailFragment)
                        Log.d(TAG, "bt_show_detail clicked")
                    }
                }

                persistentBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout).apply {
                    Log.d(TAG, "persistentBottomSheetBehaivior : $this")
                    setBottomSheetCallback(object :
                        BottomSheetBehavior.BottomSheetCallback() {
                        override fun onSlide(bottomSheet: View, slideOffset: Float) {
                            Log.d(TAG, "bottomsheet onSlide")
                            h = bottomSheet.height
                            off = h * slideOffset
                            when (persistentBottomSheetBehavior!!.state) {
                                BottomSheetBehavior.STATE_DRAGGING -> {
                                    Log.d(TAG, "onSlide bottomSheet Dragging")
                                    setMapPaddingBottom(off)
                                    //reposition marker at the center
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc))
                                }

                                BottomSheetBehavior.STATE_SETTLING -> {
                                    Log.d(TAG, "onSlide bottomSheet settling")
                                    setMapPaddingBottom(off)
                                    //reposition marker at the center
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc))
                                }
                            }
                        }

                        override fun onStateChanged(view: View, newState: Int) {
                            when (newState) {
                                BottomSheetBehavior.STATE_HIDDEN -> {
                                    Log.d(TAG, "bottomSheet hidden")
                                }
                                BottomSheetBehavior.STATE_EXPANDED -> {
                                    Log.d(TAG, "bottomSheet expanded")

                                    view.tv_title_sheet_before.text = mMarkerItem.name
                                    view.tv_date_sheet_before.text = mMarkerItem.crtDate
                                    view.tv_location_sheet_before.text = mMarkerItem.address
                                    Log.d(TAG, "name : ${mMarkerItem.name}")

                                }
                                BottomSheetBehavior.STATE_SETTLING -> {
                                    view.tv_title_sheet_before.text = mMarkerItem.name
                                    view.tv_date_sheet_before.text = mMarkerItem.crtDate
                                    view.tv_location_sheet_before.text = mMarkerItem.address
                                }
                            }
                        }
                    })
                    state = BottomSheetBehavior.STATE_EXPANDED
                    Log.d(TAG, "unImproved markerItem BottomSheet expanded")
                }

            } else {
                bottomSheetLayoutImproved = bottom_sheet_after
                persistentBottomSheetBehavior =
                    BottomSheetBehavior.from(bottomSheetLayoutImproved).apply {
                        Log.d(
                            TAG,
                            "persistentBottomSheetBehaivior : $persistentBottomSheetBehavior"
                        )

                        setBottomSheetCallback(object :
                            BottomSheetBehavior.BottomSheetCallback() {
                            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                                h = bottomSheet.height
                                off = h * slideOffset
                                when (persistentBottomSheetBehavior!!.state) {
                                    BottomSheetBehavior.STATE_DRAGGING -> {
                                        setMapPaddingBottom(off)
                                        //reposition marker at the center
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc))
                                    }

                                    BottomSheetBehavior.STATE_SETTLING -> {
                                        setMapPaddingBottom(off)
                                        //reposition marker at the center
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc))
                                    }
                                }
                            }

                            override fun onStateChanged(view: View, newState: Int) {
                                when (newState) {
                                    BottomSheetBehavior.STATE_HIDDEN -> {
                                        Log.d(TAG, "bottomSheet hiddden")
                                    }
                                    BottomSheetBehavior.STATE_EXPANDED -> {
                                        view.tv_title_before.text = mMarkerItem.name
                                        view.tv_date_before.text = mMarkerItem.crtDate
                                        view.tv_title_after.text = mMarkerItem.improvedTitle
                                        view.tv_date_after.text = mMarkerItem.improvedDate
                                    }
                                    BottomSheetBehavior.STATE_SETTLING -> {
                                        view.tv_title_before.text = mMarkerItem.name
                                        view.tv_date_before.text = mMarkerItem.crtDate
                                        view.tv_title_after.text = mMarkerItem.improvedTitle
                                        view.tv_date_after.text = mMarkerItem.improvedDate
                                    }
                                }
                            }
                        })
                        state = BottomSheetBehavior.STATE_EXPANDED
                        Log.d(TAG, "improved markerItem BottomSheet expanded")
                    }
            }
        } else {

            persistentBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
            Log.d(TAG, "needUpdate : $needUpdate")//false여야함
            Log.d(TAG, "persistentBottomSheetBehaivior : $persistentBottomSheetBehavior")

            if (!mMarkerItem.improved) {
                if (markerItem.type == 5 || markerItem.type == 6) {
                    Log.d(TAG, "marker type is 5 or 6")
                    bottomSheetLayout!!.bt_was_improved.visibility = View.GONE
                    bottomSheetLayout!!.bt_show_detail.visibility = View.GONE
                    bottomSheetLayout!!.bt_show_detail_blue.visibility = View.VISIBLE

                    bottomSheetLayout!!.bt_show_detail_blue.setOnClickListener {
                        it.findNavController()
                            .navigate(R.id.action_mainMapFragment_to_pinDetailFragment)
                        Log.d(TAG, "bt_show_detail_blue clicked")
                    }
                }else {

                    bottomSheetLayout!!.bt_was_improved.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putParcelable("item", markerItem as Parcelable)
                        it.findNavController()
                            .navigate(R.id.action_mainMapFragment_to_pinRegisterFragment, bundle)
                        Log.d(TAG, "bt_was_improved clicked")
                    }
                    bottomSheetLayout!!.bt_show_detail.setOnClickListener {
                        it.findNavController()
                            .navigate(R.id.action_mainMapFragment_to_pinDetailFragment)
                        Log.d(TAG, "bt_show_detail clicked")
                    }
                }
                persistentBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
                persistentBottomSheetBehavior!!.setBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        Log.d(TAG, "bottomsheet onSlide")
                        h = bottomSheet.height
                        off = h * slideOffset
                        when (persistentBottomSheetBehavior!!.state) {
                            BottomSheetBehavior.STATE_DRAGGING -> {
                                Log.d(TAG, "onSlide bottomSheet Dragging")
                                setMapPaddingBottom(off)
                                //reposition marker at the center
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc))
                            }

                            BottomSheetBehavior.STATE_SETTLING -> {
                                Log.d(TAG, "onSlide bottomSheet settling")
                                setMapPaddingBottom(off)
                                //reposition marker at the center
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc))
                            }
                        }
                    }

                    override fun onStateChanged(view: View, newState: Int) {
                        when (newState) {
                            BottomSheetBehavior.STATE_HIDDEN -> {
                                Log.d(TAG, "bottomSheet hidden")
                            }
                            BottomSheetBehavior.STATE_EXPANDED -> {
                                Log.d(TAG, "bottomSheet expanded")

                                view.tv_title_sheet_before.text = mMarkerItem.name
                                view.tv_date_sheet_before.text = mMarkerItem.crtDate
                                view.tv_location_sheet_before.text = mMarkerItem.address
                                Log.d(TAG, "name : ${mMarkerItem.name}")
                            }
                            BottomSheetBehavior.STATE_SETTLING -> {
                                view.tv_title_sheet_before.text = mMarkerItem.name
                                view.tv_date_sheet_before.text = mMarkerItem.crtDate
                                view.tv_location_sheet_before.text = mMarkerItem.address
                            }
                        }
                    }
                })
            } else {
                persistentBottomSheetBehavior!!.setBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        h = bottomSheet.height
                        off = h * slideOffset
                        when (persistentBottomSheetBehavior!!.state) {
                            BottomSheetBehavior.STATE_DRAGGING -> {
                                setMapPaddingBottom(off)
                                //reposition marker at the center
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc))
                            }

                            BottomSheetBehavior.STATE_SETTLING -> {
                                setMapPaddingBottom(off)
                                //reposition marker at the center
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(mLoc))
                            }
                        }
                    }

                    override fun onStateChanged(view: View, newState: Int) {
                        when (newState) {
                            BottomSheetBehavior.STATE_HIDDEN -> {
                                Log.d(TAG, "bottomSheet hiddden")
                            }
                            BottomSheetBehavior.STATE_EXPANDED -> {
                                view.tv_title_before.text = mMarkerItem.name
                                view.tv_date_before.text = mMarkerItem.crtDate
                                view.tv_title_after.text = mMarkerItem.improvedTitle
                                view.tv_date_after.text = mMarkerItem.improvedDate
                            }
                            BottomSheetBehavior.STATE_SETTLING -> {
                                view.tv_title_before.text = mMarkerItem.name
                                view.tv_date_before.text = mMarkerItem.crtDate
                                view.tv_title_after.text = mMarkerItem.improvedTitle
                                view.tv_date_after.text = mMarkerItem.improvedDate
                            }
                        }
                    }
                })
            }
            persistentBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun setMapPaddingBottom(offset: Float) {
        //From 0.0(min) - 1.0(max) //BottomShetExpanded - BottomSheepCollapsed
        val maxMapPaddingBottom = 1.0f
        mMap.setPadding(0, 0, 0, Math.round(offset * maxMapPaddingBottom))
    }

    override fun onMapClick(p0: LatLng?) {
        Log.d(TAG, "Map clicked")
        if (vm.selectedMarkerLiveData.value != null) {
            addMarker(vm.selectedMarkerLiveData.value!!.tag as MarkerItem)
            vm.selectedMarkerLiveData.value!!.remove()
        }

        if (persistentBottomSheetBehavior != null) {
            persistentBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            vm.beforeSelectedwasImproved.value = 0
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }
}


