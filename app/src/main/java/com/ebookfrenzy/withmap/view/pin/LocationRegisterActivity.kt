package com.ebookfrenzy.withmap.view.pin

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.databinding.ActivityLocationRegisterBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import java.io.IOException

class LocationRegisterActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener {
    private val TAG = "LocationRegistActivity"

    val centerAddressList: MutableLiveData<List<Address>> = MutableLiveData<List<Address>>()
    val centerAddress: MutableLiveData<String> = MutableLiveData<String>()
    val centerLatLng: MutableLiveData<LatLng> = MutableLiveData<LatLng>()

    private lateinit var mMap: GoogleMap
    private lateinit var mLoc: LatLng
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val LOCATION_REQUEST_CODE = 101
    private lateinit var mapFragment: SupportMapFragment

    private lateinit var geocoder: Geocoder
    val area: MutableLiveData<String> = MutableLiveData<String>()
    private var mAddress: Address? = null

    private lateinit var binding: ActivityLocationRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        geocoder = Geocoder(this)

        binding = ActivityLocationRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.run {
            lifecycleOwner = this@LocationRegisterActivity
            activity = this@LocationRegisterActivity
        }

        mapFragment = SupportMapFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fl_loc_regist_act, mapFragment)
            .commit()
        requestPermission()


    }

    override fun onCameraMoveStarted(p0: Int) {
        Log.d(TAG, "onCameraMoveStarted")
    }

    override fun onCameraMove() {
    }

    override fun onCameraIdle() {
        Log.d(TAG, "onCameraIdle()")
        getCenterPositionName()
    }

    //지도 중간 position불러오기
    fun getCenterPositionName() {
        val center: LatLng = mMap.cameraPosition.target
        var list: List<Address>? = null
        var strbuf = StringBuffer()
        var buf: String = ""
//
//        try{
//            list = geocoder.getFromLocation(center.latitude, center.longitude, 2)
//            if(list != null) {
//                if(list.size >0) {
//                    mAddress = list.get(0)
//                    var i = 0
//                    while(mAddress!!.getAddressLine(i)!=null){
//                        strbuf.append(buf+"\n")
//                    }
//                    area.value = strbuf.toString()
////                centerAddress.value = list.get(0).toString()
//                }
//            }
//        } catch(e : IOException) {
//            e.printStackTrace()
//            Log.e(TAG, e.message)
//        }

        centerLatLng.value = mMap.cameraPosition.target
        centerLatLng.observe(this, Observer {
            Log.d(TAG, "centerLatLng : ${(centerLatLng.value as LatLng).latitude}")
            try {
                list = geocoder.getFromLocation(it.latitude, it.longitude, 2)
                if (list != null) {
                    if (list!!.size > 0) {
                        Log.d(TAG, "list size : ${list!!.size}")
//                    mAddress = list!!.get(0)
//                    var i = 0
//                    while(mAddress!!.getAddressLine(i)!=null){
//                        strbuf.append(buf+"\n")
//                        i++
//                    }
//                    Log.d(TAG, "strbuf : ${strbuf.toString()}")
//                    area.value = strbuf.toString()
                        area.value = list!![0].getAddressLine(0).toString()
//                centerAddress.value = list.get(0).toString()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e(TAG, e.message)
            }
        })
    }

    /**
     * 현재위치 불러오기
     */
    private fun requestPermission() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "request permission")
            ActivityCompat.requestPermissions(
                this,
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
                    Toast.makeText(this@LocationRegisterActivity, "hi", Toast.LENGTH_SHORT)
                    val supportMapFragment: SupportMapFragment =
                        supportFragmentManager.findFragmentById(R.id.fl_loc_regist_act) as SupportMapFragment
                    supportMapFragment.getMapAsync(this@LocationRegisterActivity)
                } else {
                    Log.d(TAG, "fetchLastLocation fail")
                    Toast.makeText(
                        this@LocationRegisterActivity,
                        "No Location recorded",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        })
    }

    fun showCurrent() {
        Log.d(TAG, "showCurrent clicked")
        mLoc = LatLng(currentLocation.latitude, currentLocation.longitude)
        mMap.animateCamera(CameraUpdateFactory.newLatLng(mLoc))
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
                    Toast.makeText(this, "Lcoationp permission missing", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        mMap.setOnCameraMoveStartedListener(this)
        mMap.setOnCameraMoveListener(this)
        mMap.setOnCameraIdleListener(this)
        mLoc = LatLng(currentLocation.latitude, currentLocation.longitude)

//        mLoc = LatLng(37.537523, 126.96558)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLoc, 14f))

        val center: LatLng = mMap.cameraPosition.target
        var list: List<Address>? = null
        var strbuf = StringBuffer()
        var buf: String = ""
        try {
            list = geocoder.getFromLocation(center.latitude, center.longitude, 2)
            Log.d(TAG, "address list[0] : ${list[0].toString()}")
            if (list != null) {
                if (list!!.size > 0) {
                    Log.d(TAG, "list size : ${list!!.size}")
//                    mAddress = list!!.get(0)
//                    var i = 0
//                    while(mAddress!!.getAddressLine(i)!=null){
//                        strbuf.append(buf+"\n")
//                        i++
//                    }
//                    Log.d(TAG, "strbuf : ${strbuf.toString()}")
//                    area.value = strbuf.toString()
                    area.value = list[0].getAddressLine(0).toString()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, e.message)
        }
    }

    fun registerLocation() {
        Log.d(TAG, "register clicked")

        val intent = Intent()
        intent.putExtra("latitude", (centerLatLng.value as LatLng).latitude.toString())
        intent.putExtra("longitude", (centerLatLng.value as LatLng).longitude.toString())
        intent.putExtra("address", area.value)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun popBackStack() {
        Log.d(TAG, "popBackStack")
        finish()
    }

}
