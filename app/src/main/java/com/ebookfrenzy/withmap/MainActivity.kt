package com.ebookfrenzy.withmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.CameraUpdateFactory.zoomTo
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var mMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment  = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {

        mMap = googleMap!!

        val SEOUL : LatLng = LatLng(37.56, 126.97)

        val markerOptions = MarkerOptions().apply{
            position(SEOUL)
            title("서울")
            snippet("한국의 수도")
        }

        mMap.apply{
            addMarker(markerOptions)
            moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
            animateCamera(CameraUpdateFactory.zoomTo(10f))
        }


         }
}
