package com.rescat.rescat_android.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.model.MapData
import com.rescat.rescat_android.network.NetworkService

class SearchResultActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    lateinit var MapdataList : ArrayList<MapData>

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }





//통신

    private fun getMapResponse() {

//        var getMapResponse = networkService.getMapResponse()
//        getMapResponse.enqueue(object: Callback<GetMapResponse> {
//            override fun onFailure(call: Call<GetMapResponse>, t: Throwable) {
//                Log.e("TAG", "통신에러")
//            }
//
//            override fun onResponse(call: Call<GetMapResponse>, response: Response<GetMapResponse>) {
//                if (response.isSuccessful){
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(response.body()!!.data[0].latitude,  response.body()!!.data[0].longitude), 15.0f))
//
//                                mMap.addCircle(CircleOptions().
//                                            center(LatLng(response.body()!!.data[0].latitude, response.body()!!.data[0].longitude)).
//                                                   radius(100.0).strokeColor(Color.RED).fillColor(Color.parseColor("#80be9981"))
//
//                                              )
//
//                    response.body()!!.data.forEach {
//                                               addNewMarker(it)
//                                                it?.let {
//                                                      addNewMarker(it)
//                                                 }
//                                              addNewMarker(it)
//                    }
//
//                }
//                Log.e("TAG", response.body().toString())
//            }
//
//        })



    }


    //지도 시작
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        //mMarker = mMap.addMarker(MarkerOptions().position(LatLng(-34.0, 151.0)).title("Marker in Sydney"))

       // getMapResponse()
        // 디폴트 받아온 좌표값을 여기서..!
//        mMarker = mMap.addMarker(MarkerOptions().position(LatLng(-34.0, 151.0)).title("Marker in Sydney"))
//        mMap.addMarker(MarkerOptions().position(LatLng(-34.0, 151.0)).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLn
// g(-34.0, 151.0)))
        //mMap.addMarker(MarkerOptions().position(LatLng(L,)))
        Log.e("TAG", "지도 잘 보여진드아")


    }


    var count = 0
    val builder = LatLngBounds.Builder()

    private fun addNewMarker(data:MapData) {
        val mMarker : Marker = mMap.addMarker(MarkerOptions().position(LatLng(data.lat, data.lng)))
        val mMarkerOption = MarkerOptions()


        //주소받아오기
//        val address = Geocoder(activity!!, Locale.KOREAN)
//            .getFromLocation(data.latitude, data.longitude, 2)
//

//       mMarkerOption.position(LatLng(currentLatitude, currentLongitude))
//            .snippet(address[0].getAddressLine(0).toString()).title(address[0].subLocality)

        //mMarker.remove()
//        currentLatitude = data.latitude
//        currentLongitude = data.longitude
//        val address = Geocoder(activity!!, Locale.KOREAN)
//            .getFromLocation(currentLatitude, currentLongitude, 2)

        //대한민국 경기도 용인시 수지구 성북동 420-1
        //address[0].getAddressLine(0).toString()
//
//        if (address.size!=0){
//            Log.e("Address", address[0].getAddressLine(0).toString())
//        }
//

//
        //mMarker = mMap.addMarker(mMarkerOption)
//
//        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(currentLatitude, currentLongitude), 15.0f))
//        count++
//
        builder.include(mMarker.position)
        val bounds = builder.build()
        val padding = 0 // offset from edges of the map in pixels
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)

        if (count == 4){
            mMap.moveCamera(cu)
        }
    }


}
