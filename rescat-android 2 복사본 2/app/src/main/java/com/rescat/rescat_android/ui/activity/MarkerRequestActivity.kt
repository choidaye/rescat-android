package com.rescat.rescat_android.ui.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.rescat.rescat_android.Get.GetMapResponse
import com.rescat.rescat_android.Get.GetMyPageCareResponse
import com.rescat.rescat_android.Post.PostMarkerRequest
import com.rescat.rescat_android.Post.Response.PostMarkerRequestResponse
import com.rescat.rescat_android.Post.Response.PostUserLoginResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import kotlinx.android.synthetic.main.fragment_cat_map.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarkerRequestActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    lateinit var MapdataList : ArrayList<PostMarkerRequest>

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marker_request)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setOnBtnClickListener()

    }

    private fun setOnBtnClickListener() {




    }


    private fun MoveCamera(data: PostMarkerRequest){


    }




//통신

    private fun PostMarkerRequestResponse() {

        Log.e("mapresponse","맵통신 연결")

        var markerRequest : String = ""


        val postMarkerRequestResponse : Call<ArrayList<PostMarkerRequestResponse>> =
           networkService.postMapResponse(markerRequest)
        postMarkerRequestResponse.enqueue(object : Callback<ArrayList<PostMarkerRequestResponse>>{
            override fun onFailure(call: Call<ArrayList<PostMarkerRequestResponse>>, t: Throwable) {
                Log.e("TAG", "지도 통신에러")
            }

            override fun onResponse( call: Call<ArrayList<PostMarkerRequestResponse>>, response: Response<ArrayList<PostMarkerRequestResponse>>) {

                if (response.isSuccessful){

                    //에드 마커

                    //카메라 줌




                    Log.e("TAG", "지도 통신완료")
                }

            }

        })

    }


    private fun moveCamera(data:PostMarkerRequest){

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(data.lat, data.lng), 15.0f))


    }


    //지도 시작
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        PostMarkerRequestResponse()
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


    lateinit var mMarker : Marker

    private fun addNewMarker(data:PostMarkerRequest) {
//        val mMarker : Marker = mMap.addMarker(MarkerOptions().position(LatLng(data.lat, data.lng)))
//        val mMarkerOption = MarkerOptions()


                mMarker = mMap.addMarker(MarkerOptions().position(LatLng(data.lat, data.lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_cat)))




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
