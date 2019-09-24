package com.ebookfrenzy.withmap.base

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.view.hamburg.MyRegisterPinFragment
import com.ebookfrenzy.withmap.view.hamburg.NotificationFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task

class PracActivity : AppCompatActivity(), OnMapReadyCallback {


    private val TAG = "PracActivity"
    private lateinit var currentLocation : Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val LOCATION_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prac)
        Log.d(TAG, "onCreate")

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "request permission")
            ActivityCompat.requestPermissions(this, Array<String>(1, {android.Manifest.permission.ACCESS_FINE_LOCATION}), LOCATION_REQUEST_CODE)
            return
        }
        fetchLastLocation()
    }

    private fun fetchLastLocation() {
        Log.d(TAG, "fetchlatstLcoation")
        val task : Task<Location> = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener(object : OnSuccessListener<Location> {
            override fun onSuccess(location: Location?) {
                if(location != null) {
                    currentLocation = location
                    Log.d(TAG, "fetchLastLocation success")
                    Toast.makeText(applicationContext, "hi", Toast.LENGTH_SHORT)
                    val supportMapFragment : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                    supportMapFragment.getMapAsync(this@PracActivity)
                }else {
                    Log.d(TAG, "fetchLastLocation fail")
                    Toast.makeText(applicationContext, "No Location recorded", Toast.LENGTH_SHORT).show()

                }
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val latLng : LatLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions : MarkerOptions = MarkerOptions().position(latLng).title("you are here")

        googleMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))

        //adding the create the marker on the map
        googleMap.addMarker(markerOptions)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            LOCATION_REQUEST_CODE -> {
                if(grantResults.size >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onRequestPermissionsresult granted")
                    fetchLastLocation()
                }else {
                    Toast.makeText(applicationContext, "Lcoationp permission missing", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
