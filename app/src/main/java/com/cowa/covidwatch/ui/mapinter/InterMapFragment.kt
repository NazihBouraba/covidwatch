package com.example.citylist.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.cowa.covidwatch.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class InterMapFragment : Fragment(), OnMapReadyCallback {

    protected lateinit var rootView: View
    private lateinit var viewModel: ViewModel
    var location = LatLng(-34.0, 151.0)     //Default map location is Sydney, Australia
    private var mMap: GoogleMap? = null

    companion object {
        var mapFragment: SupportMapFragment? = null
        val TAG: String = InterMapFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_inter_map, container, false)

        //Get fragment instance
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        //Observe selected city's location from the ViewModel
//        viewModel.getSelectedCoordinates().observe(this, Observer<coord> {
//            //assign coordinates to mapview location
//            location = LatLng(it.lat.toDouble(), it.lon.toDouble())
//            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 9.0f), 900, null)
//        })

        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap?.let {
            it.moveCamera(CameraUpdateFactory.newLatLng(location))
        }

        mMap!!.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(point: LatLng) {
                Log.d("DEBUG", "Map clicked [" + point.latitude + " / " + point.longitude + "]")

            }
        })

    }
}