package com.ebookfrenzy.withmap.view.main


import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController

import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.config.WithMapApplication
import com.ebookfrenzy.withmap.data.GetMyRegisterPinData
import com.ebookfrenzy.withmap.data.MarkerItem
import com.ebookfrenzy.withmap.data.MyRegisterPinData
import com.ebookfrenzy.withmap.data.SearchLocationResult
import com.ebookfrenzy.withmap.databinding.FragmentMainMapBinding
import com.ebookfrenzy.withmap.network.response.UserInfo
import com.ebookfrenzy.withmap.respository.SharedPreferenceSource
import com.ebookfrenzy.withmap.viewmodel.MainViewModel
import com.ebookfrenzy.withmap.viewmodel.NotificationViewModel
import com.ebookfrenzy.withmap.viewmodel.hamSetImage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.bottom_sheet_after.*
import kotlinx.android.synthetic.main.bottom_sheet_after.view.*
import kotlinx.android.synthetic.main.bottom_sheet_before.*
import kotlinx.android.synthetic.main.bottom_sheet_before.view.*
import kotlinx.android.synthetic.main.fragment_main_map.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class MainMapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMapClickListener, NavigationView.OnNavigationItemSelectedListener,
    GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraMoveListener {

    override fun onCameraMoveStarted(p0: Int) {
    }

    override fun onCameraMove() {
    }

    private var headerName: String = "홍길동"
    private var headerEmail: String = "iloveyou@naver.com"

    private var beforeCameraPositon: LatLng? = null

    private lateinit var currentLocation: Location
    private val sampleLocation = LatLng(37.57261267, 126.9757016)
    private val dbLocation = LatLng(37.537523, 126.96558)

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val LOCATION_REQUEST_CODE = 101

    private lateinit var mLoc: LatLng

    private lateinit var bottomSheetLayoutImproved: View
    private var persistentBottomSheetBehavior: BottomSheetBehavior<*>? = null

    private lateinit var mapFragment: SupportMapFragment

    private lateinit var headerView: View
    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMainMapBinding

    private lateinit var markerRootView: View
    private lateinit var ivMarker: ImageView
    private val vm: MainViewModel by viewModel()
    private lateinit var vmNoti: NotificationViewModel

    private var searchArg: SearchLocationResult? = null


    var bottomSheetLayout: View? = null

    var beingClicked: Boolean = false

    private val TAG = "MainMapFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.selectedMarkerLiveData.observe(this, Observer {
            initPersistentBottonSheetBehavior(
                it!!
            )
        })

        mapFragment = SupportMapFragment.newInstance()
//        mapFragment.getMapAsync(this)

        childFragmentManager.beginTransaction().replace(R.id.fl_main_map_frag, mapFragment).commit()

        requestPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        markerRootView = LayoutInflater.from(this.context).inflate(R.layout.marker_layout, null)
        ivMarker = markerRootView.findViewById(R.id.iv_marker)
        vmNoti = ViewModelProviders.of(this)[NotificationViewModel::class.java]

        binding = FragmentMainMapBinding.inflate(LayoutInflater.from(this.context))

        // 장소 검색시 실행
        searchArg = MainMapFragmentArgs.fromBundle(arguments!!).location


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("Malibin Debug", "onViewCreated")
        if (vm.isFirst) {
            Navigation.findNavController(view)
                .navigate(R.id.action_mainMapFragment_to_tutorialActivity)
        }

        bt_hamburger.setOnClickListener {
            drawer_layout.openDrawer(nav_view)
        }

        binding.etSearch.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_mainMapFragment_to_searchFragment)
        }

        binding.run {
            lifecycleOwner = this@MainMapFragment
            vmNoti = ViewModelProviders.of(this@MainMapFragment)[NotificationViewModel::class.java]
            fragment = this@MainMapFragment
        }
        getUserInfo2()

//        vm.getPinsAround(vm.centerLatLng.value!!.latitude, vm.centerLatLng.value!!.longitude)

    }

    fun getUserInfo2() {
        val networkService = WithMapApplication.instance.networkService
        val getMyRegisterPin =
            networkService.getUserInfo2(SharedPreferenceSource(this.context!!).authToken)
        getMyRegisterPin.enqueue(object : Callback<UserInfo> {
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.e(TAG, t.stackTrace.toString())
            }

            override fun onResponse(
                call: Call<UserInfo>,
                response: Response<UserInfo>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "getUserInfo success")
                    if (response.body() != null) {
                        val data = response.body()
                        headerName = data!!.name
                        headerEmail = data!!.email
                    }
                }
                Log.d(TAG, response.message())
            }
        })
        setHeader(nav_view)
    }


    override fun onCameraIdle() {
        Log.d(TAG, "onCameraIdle()")
        mMap.clear()

        if (vm.centerLatLng.value != mMap.cameraPosition.target) {
            Log.d(
                TAG,
                "before LatLng : ${vm.centerLatLng.value!!.latitude}, ${vm.centerLatLng.value!!.longitude} \n " +
                        "after LatLng : ${mMap.cameraPosition.target.latitude}, ${mMap.cameraPosition.target.longitude}"
            )
            vm.centerLatLng.value = mMap.cameraPosition.target
            vm.getPinsAround(vm.centerLatLng.value!!.latitude, vm.centerLatLng.value!!.longitude)
        }

    }

    //샘플로 만든 마커들, +추가해놓기
    fun getSampleMarkerItems() {

//        vm.getPinsAround(currentLocation.latitude, currentLocation.longitude)

        vm.markerItemLiveData.observe(this, Observer {
            if (it != null) {
                val sampleList = it
                Log.d(TAG, "sampleList : ${sampleList.toString()}")

                mMap.clear()
                for (markerItem in sampleList) {
                    addMarker(markerItem)
                }
            }
        })

    }


    private fun requestPermission() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!);
        if (ActivityCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "request permission")
            ActivityCompat.requestPermissions(
                activity!!,
                Array<String>(1, { android.Manifest.permission.ACCESS_FINE_LOCATION }),
                LOCATION_REQUEST_CODE
            )
            return
        }
        fetchLastLocation()
    }

    private fun fetchLastLocation() {
        Log.d(TAG, "fetchlatstLcoation")
        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener(object : OnSuccessListener<Location> {
            override fun onSuccess(location: Location?) {
                if (location != null) {
                    currentLocation = location
                    Log.d(TAG, "fetchLastLocation success")
                    Toast.makeText(context, "hi", Toast.LENGTH_SHORT)
                    val supportMapFragment: SupportMapFragment =
                        childFragmentManager.findFragmentById(R.id.fl_main_map_frag) as SupportMapFragment
                    supportMapFragment.getMapAsync(this@MainMapFragment)
                } else {
                    Log.d(TAG, "fetchLastLocation fail")
                    Toast.makeText(context, "No Location recorded", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

    fun goToPinRegister() {
        if (SharedPreferenceSource(this.context!!).authToken.isNotEmpty()) {
            Log.d(TAG, "go to Pin Register")
            binding.root.findNavController()
                .navigate(R.id.action_mainMapFragment_to_pinRegisterFragment)
        } else {
            Toast.makeText(this.context!!, "로그인이 필요합니다", Toast.LENGTH_SHORT).show()
        }
    }

    fun showCurrent() {
        mLoc = LatLng(currentLocation.latitude, currentLocation.longitude)
        mMap.animateCamera(CameraUpdateFactory.newLatLng(mLoc))
    }

    private fun goSearchedLocation() {
        searchArg?.let {
            val location = LatLng(it.latitude, it.longitude)
            mMap.animateCamera(CameraUpdateFactory.newLatLng(location))
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

//        mLoc = LatLng(currentLocation.latitude, currentLocation.longitude)
        mLoc = LatLng(sampleLocation.latitude, sampleLocation.longitude)
//        mLoc = LatLng(dbLocation.latitude, dbLocation.longitude)


//        mLoc = LatLng(37.537523, 126.96558)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLoc, 14f))
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)
        mMap.setOnCameraMoveStartedListener(this)
        mMap.setOnCameraIdleListener(this)
        mMap.setOnCameraMoveListener(this)

        vm.centerLatLng.value = LatLng(sampleLocation.latitude, sampleLocation.longitude)
        vm.getPinsAround(sampleLocation.latitude, sampleLocation.longitude)

        getSampleMarkerItems()

        goSearchedLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsresult granted")
                    fetchLastLocation()
                } else {
                    Toast.makeText(context!!, "Lcoationp permission missing", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    fun setHeader(view_navi: NavigationView) {
        headerView = view_navi.getHeaderView(0)
        val headerAlarm: ImageView = headerView.findViewById(R.id.header_alarm)
        val headerAccount: ConstraintLayout = headerView.findViewById(R.id.rl_account)
        val headerExchange: Button = headerView.findViewById(R.id.bt_exchange)
        val name: TextView = headerView.findViewById(R.id.tv_username)
        val email: TextView = headerView.findViewById(R.id.tv_email)

        if (SharedPreferenceSource(this.context!!).authToken.isNotEmpty()) {


            name.text = headerName
            email.text = headerEmail

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
                it.findNavController()
                    .navigate(R.id.action_mainMapFragment_to_myRegisterPinFragment)
            }

            vmNoti.notificationLiveData.observe(this, Observer {
                hamSetImage(headerAlarm, it)
            })
        } else {
            name.text = "로그인이 필요합니다"
            email.text = ""

            headerExchange.setOnClickListener {
                Toast.makeText(this.context, "로그인이 필요합니다", Toast.LENGTH_SHORT)
            }
            headerAccount.setOnClickListener {
                Log.d(TAG, "go to login")
                it.findNavController().navigate(R.id.action_mainMapFragment_to_loginFragment2)
            }
            headerAlarm.setOnClickListener {
                Log.d(TAG, "go to Notification Fragment")
                it.findNavController().navigate(R.id.action_mainMapFragment_to_notificationFragment)
            }

            val myPinRegister: RelativeLayout = headerView.findViewById(R.id.rl_my_pin)
            myPinRegister.setOnClickListener {
                Toast.makeText(this.context, "로그인이 필요합니다", Toast.LENGTH_SHORT)
            }

            vmNoti.notificationLiveData.observe(this, Observer {
                hamSetImage(headerAlarm, it)
            })
        }


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


    //마커를 추가해놓는 함수
    private fun addMarker(markerItem: MarkerItem): Marker {
        val position = LatLng(markerItem.latitude!!, markerItem.longitude!!)
        Log.d(TAG, "addMarker()")

        if (markerItem.type == 5 || markerItem.type == 6) {
            markerItem.improved = false
        }
        if (!markerItem.improved) {
            //개선되기전

            when (markerItem.type) {
                1 -> ivMarker.setImageResource(R.drawable.pin_hurdle_on)
                2 -> ivMarker.setImageResource(R.drawable.pin_dump_on)
                3 -> ivMarker.setImageResource(R.drawable.group_9)
                4 -> ivMarker.setImageResource(R.drawable.group_10)
                5 -> ivMarker.setImageResource(R.drawable.pin_toilet_on)
                6 -> ivMarker.setImageResource(R.drawable.pin_restaurant_on)
            }
//            }
        } else {
            //개선된 후
            when (markerItem.type) {

                1 -> ivMarker.setImageResource(R.drawable.pin_hurdle_off)
                2 -> ivMarker.setImageResource(R.drawable.pin_dump_off)
                3 -> ivMarker.setImageResource(R.drawable.pin_unpaved_off)
                4 -> ivMarker.setImageResource(R.drawable.pin_narrow_off)
                5 -> ivMarker.setImageResource(R.drawable.pin_toilet_on)
                6 -> ivMarker.setImageResource(R.drawable.pin_restaurant_on)
            }
        }
        if (vm.selectedMarkerLiveData.value != null) {
            if (position == (vm.selectedMarkerLiveData.value as Marker).position) {
                when (markerItem.type) {
                    1 -> ivMarker.setImageResource(R.drawable.pin_hurdle_touch)
                    2 -> ivMarker.setImageResource(R.drawable.pin_dump_touch)
                    3 -> ivMarker.setImageResource(R.drawable.pin_unpaved_touch)
                    4 -> ivMarker.setImageResource(R.drawable.pin_narrow_touch)
                    5 -> ivMarker.setImageResource(R.drawable.pin_toilet_touch)
                    6 -> ivMarker.setImageResource(R.drawable.pin_restaurant_touch)
                }
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

        if (marker != vm.selectedMarkerLiveData.value)
            changeSelectedMarker(marker)
        return true
    }

    private fun changeSelectedMarker(marker: Marker?) {
        Log.d(TAG, "new selected : ${marker!!.tag}")


        if (vm.selectedMarkerLiveData.value != null) {
            if (vm.selectedMarkerLiveData.value!!.tag != null) {
                addMarker(vm.selectedMarkerLiveData.value!!.tag as MarkerItem)
                vm.selectedMarkerLiveData.value!!.remove()
            }
        }
        //선택된 마커를 selectedMarker로 '새로'지정해서 지도에 추가
        vm.selectedMarkerLiveData.value = marker

    }

    //BottomSheet발생시키는 함수
    private fun initPersistentBottonSheetBehavior(marker: Marker) {

        val mMarkerItem = marker.tag as MarkerItem
        val markerItem = marker.tag as MarkerItem

        var h: Int = 1
        var off: Float = 0f
        Log.d(
            TAG,
            "-----initPersistentBottomSheetBehavior()-----selectedMarkerItem : $mMarkerItem "
        )
        if (persistentBottomSheetBehavior != null) {
            persistentBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        if (!markerItem.improved) {
            Log.d(TAG, "!markerItem.improved")
            bottomSheetLayout = bottom_sheet_before

            if (markerItem.type == 5 || markerItem.type == 6) {
                Log.d(TAG, "marker type is 5 or 6")
                when (markerItem.type) {
                    5 -> bottomSheetLayout!!.iv_image.setImageResource(R.drawable.default_rest_o)
                    6 -> bottomSheetLayout!!.iv_image.setImageResource(R.drawable.default_food)
                }

                bottomSheetLayout!!.ll_info_orange.visibility = View.GONE
                bottomSheetLayout!!.ll_info_blue.visibility = View.VISIBLE

                bottomSheetLayout!!.bt_was_improved.visibility = View.GONE
                bottomSheetLayout!!.bt_show_detail.visibility = View.GONE
                bottomSheetLayout!!.bt_show_detail_blue.visibility = View.VISIBLE

                bottomSheetLayout!!.bt_show_detail_blue.setOnClickListener {
                    val direction =
                        MainMapFragmentDirections.actionMainMapFragmentToPinDetailFragment(
                            markerItem.id
                        )
                    it.findNavController().navigate(direction)
                    Log.d(TAG, "bt_show_detail_blue clicked")
                }
            } else {
                Log.d(TAG, "marker type is 1~4")
                bottomSheetLayout!!.iv_image.setImageResource(R.drawable.default_road_x)

                bottomSheetLayout!!.bt_was_improved.visibility = View.VISIBLE
                bottomSheetLayout!!.bt_show_detail.visibility = View.VISIBLE
                bottomSheetLayout!!.bt_show_detail_blue.visibility = View.GONE

                bottomSheetLayout!!.ll_info_orange.visibility = View.VISIBLE
                bottomSheetLayout!!.ll_info_blue.visibility = View.GONE
                bottomSheetLayout!!.bt_was_improved.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putParcelable("item", markerItem as Parcelable)
                    it.findNavController()
                        .navigate(
                            R.id.action_mainMapFragment_to_pinRegisterFragment,
                            bundle
                        )
                    Log.d(TAG, "bt_was_improved clicked")
                }
                bottomSheetLayout!!.bt_show_detail.setOnClickListener {
                    val direction =
                        MainMapFragmentDirections.actionMainMapFragmentToPinDetailFragment(
                            markerItem.id
                        )
                    it.findNavController().navigate(direction)
                    Log.d(TAG, "bt_show_detail clicked")
                }
            }

            persistentBottomSheetBehavior =
                BottomSheetBehavior.from(bottomSheetLayout).apply {
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
//                                    BottomSheetBehavior.STATE_HIDDEN -> {
//                                        beingClicked = false
//                                        Log.d(TAG, "bottomSheet hidden")
//                                        if (vm.selectedMarkerLiveData.value != null) {
//                                            if (vm.selectedMarkerLiveData.value!!.tag != null) {
//                                                addMarker(vm.selectedMarkerLiveData.value!!.tag as MarkerItem)
//                                                vm.selectedMarkerLiveData.value!!.remove()
//                                            }
//                                        }
//
//                                        if (persistentBottomSheetBehavior != null) {
//                                            vm.beforeSelectedwasImproved.value = 0
//                                        }
//                                    }
                                BottomSheetBehavior.STATE_EXPANDED -> {
                                    Log.d(TAG, "bottomSheet expanded")

                                    view.tv_title_sheet_before_blue.text = mMarkerItem.name
                                    view.tv_date_sheet_before_blue.text =
                                        mMarkerItem.crtDate
                                    view.tv_info_location_blue.text = mMarkerItem.address
                                    view.tv_usable_time_blue.text = "24시간"
                                    view.tv_call_number_blue.text = "000-0000-0000"

                                    view.tv_title_sheet_before.text = mMarkerItem.name
                                    view.tv_date_sheet_before.text = mMarkerItem.crtDate
                                    view.tv_location_sheet_before.text = mMarkerItem.address
                                    Log.d(TAG, "name : ${mMarkerItem.name}")

                                }
                                BottomSheetBehavior.STATE_SETTLING -> {

                                    view.tv_title_sheet_before_blue.text = mMarkerItem.name
                                    view.tv_date_sheet_before_blue.text =
                                        mMarkerItem.crtDate
                                    view.tv_info_location_blue.text = mMarkerItem.address
                                    view.tv_usable_time_blue.text = "24시간"
                                    view.tv_call_number_blue.text = "000-0000-0000"

                                    view.tv_title_sheet_before.text = mMarkerItem.name
                                    view.tv_date_sheet_before.text = mMarkerItem.crtDate
                                    view.tv_location_sheet_before.text = mMarkerItem.address
                                }
                            }
                        }
                    })
                    state = BottomSheetBehavior.STATE_COLLAPSED
                    state = BottomSheetBehavior.STATE_EXPANDED
                    Log.d(TAG, "unImproved markerItem BottomSheet expanded")
                }

        } else {
            Log.d(TAG, "markerItem unImproved")
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
                            Log.d(TAG, "bottomsheet onSlide when markeritem unImproved")
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
//                                    BottomSheetBehavior.STATE_HIDDEN -> {
//                                        beingClicked = false
//                                        Log.d(TAG, "bottomSheet hiddden")
//                                        if (vm.selectedMarkerLiveData.value != null) {
//                                            if (vm.selectedMarkerLiveData.value!!.tag != null) {
//                                                addMarker(vm.selectedMarkerLiveData.value!!.tag as MarkerItem)
//                                                vm.selectedMarkerLiveData.value!!.remove()
//                                            }
//                                        }
//
//                                        if (persistentBottomSheetBehavior != null) {
//                                            vm.beforeSelectedwasImproved.value = 0
//                                        }
//                                    }
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
                    state = BottomSheetBehavior.STATE_COLLAPSED
                    state = BottomSheetBehavior.STATE_EXPANDED
                    Log.d(TAG, "improved markerItem BottomSheet expanded")

                }

        }
    }


    private fun setMapPaddingBottom(offset: Float) {
        //From 0.0(min) - 1.0(max) //BottomShetExpanded - BottomSheepCollapsed
        val maxMapPaddingBottom = 1.0f
//        mMap.setPadding(0, 0, 0, Math.round(offset * maxMapPaddingBottom))
        cl_main_map_frag.setPadding(0, 0, 0, Math.round(offset * maxMapPaddingBottom))
    }

    override fun onMapClick(p0: LatLng?) {
        beingClicked = false
        Log.d(TAG, "Map clicked")
        if (vm.selectedMarkerLiveData.value != null) {
            if (vm.selectedMarkerLiveData.value!!.tag != null) {
                addMarker(vm.selectedMarkerLiveData.value!!.tag as MarkerItem)
                vm.selectedMarkerLiveData.value!!.remove()
            }
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