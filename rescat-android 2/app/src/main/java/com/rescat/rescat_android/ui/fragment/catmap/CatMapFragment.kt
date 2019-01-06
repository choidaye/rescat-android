package com.rescat.rescat_android.ui.fragment.catmap

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.rescat.rescat_android.Get.GetMapResponse

import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication

import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.AddMarkerActivity
import com.rescat.rescat_android.ui.activity.SearchActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.dialog_my_address.*
import kotlinx.android.synthetic.main.fragment_cat_map.*
import org.jetbrains.anko.locationManager
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class CatMapFragment : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {


    lateinit var MapdataList : ArrayList<GetMapResponse>

    val networkService: NetworkService by lazy {
        RescatApplication.instance.networkService
    }

    //마커 클릭 이벤트
    override fun onMarkerClick(p0: Marker?) : Boolean{

        cv_marker_detail.setVisibility(View.VISIBLE)

        //toast(p0.toString())
        return true
    }



    var result : String = ""

    var currentLongitude: Double = 0.0
    var currentLatitude: Double = 0.0



    //



    private lateinit var mMap: GoogleMap
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(activity!!)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cat_map, container, false)
    }


    //프래그먼트에 override

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBtnClickListener()
        map.onCreate(savedInstanceState)
        map.onResume()
        map.getMapAsync(this)


    }



    //버튼클릭리스너

    private fun setOnBtnClickListener() {
        btn_fg_cmap_search.setOnClickListener{
            startActivity<SearchActivity>()
        }

        btn_fg_cmap_add_item.setOnClickListener{
            startActivity<AddMarkerActivity>()
        }



        btn_fg_cmap_filter_all.setOnClickListener{
            allsetupMarkerMap()

        }


        btn_fg_cmap_filter_cat.setOnClickListener {
            setupVisibleMap(2)


        }


        btn_fg_cmap_filter_hospital.setOnClickListener {
            setupVisibleMap(1)
        }

        btn_fg_cmap_filter_eat.setOnClickListener {
            setupVisibleMap(0)
        }

        btn_fg_cmap_my_address.setOnClickListener {

            MyaddressDialog()
        }

        btn_marker_detail_more.setOnClickListener {
            cv_marker_detail.setVisibility(View.GONE)
            activity!!.findViewById<ConstraintLayout>(R.id.popup_more).visibility = View.VISIBLE
        }




    }


    private fun allsetupMarkerMap() {
        MapdataList.forEach {
            it?.let {
                addNewMarker(it)

            }
            addNewMarker(it)
        }

    }

    //실제로 마커가 보이는 부분
    private fun setupVisibleMap(num: Int) {

        //다른 마커가 찍히지 않도록 clear해줌
        mMap.clear()

        //foreach는 아이템갯수에 따라 반복문을 돌아준다.
        MapdataList.filter { it.category == num }.forEach {

            CircleOption(it)
            addNewMarker(it)

        }
    }



    //통신
    private fun getMapResponse() {

        Log.e("mapresponse","맵통신 연결")

        var token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJSeWFuZ1QiLCJ1c2VyX2lkeCI6MSwiZXhwIjoxNTQ5Mjk2MjU4fQ.Svr3JqKjOzmIFoYN2_XY5AZdVFT70GtL3EnACscWJpE"
        var emdcode ="1108072"


        var getMapResponse = networkService.getMapResponse(token,emdcode)
        getMapResponse.enqueue(object: Callback<ArrayList<GetMapResponse>> {
            override fun onFailure(call: Call<ArrayList<GetMapResponse>>, t: Throwable) {
                Log.e("TAG", "지도 통신에러")
            }

            override fun onResponse(call: Call<ArrayList<GetMapResponse>>, response: Response<ArrayList<GetMapResponse>>) {
                if (response.isSuccessful){

                    Log.e("TAG", "지도 통신완료")

                    MapdataList = response.body()!!

                    setupVisibleMap(4)
                    allsetupMarkerMap()


                    //카메라 줌
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(MapdataList[0].lat, MapdataList[0].lng), 15.0f))

//                    mMap.addCircle(
//                        CircleOptions().center(
//                            LatLng(
//                                response.body()!!.lat,
//                                response.body()!!.data[0].lng
//                            )
//                        ).radius(130.0).fillColor(Color.parseColor("#4Df29191")).strokeColor(Color.parseColor("#4Df29191"))
//
//                    )

                }


            }
        })



    }

    //지도 시작
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener {
            activity!!.findViewById<ConstraintLayout>(R.id.popup_more).visibility = View.GONE
            cv_marker_detail.setVisibility(View.GONE)
        }

       getMapResponse()

        // 디폴트 받아온 좌표값을 여기서..!
  //     mMarker = mMap.addMarker(MarkerOptions().position(LatLng(-34.0, 151.0)).title("Marker in Sydney"))
//        mMap.addMarker(MarkerOptions().position(LatLng(-34.0, 151.0)).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-34.0, 151.0)))
        //mMap.addMarker(MarkerOptions().position(LatLng(L,)))
        Log.e("mapshow", "지도 잘 보여진드아")




    }

    protected fun MyaddressDialog() {


        Log.v("asdf","다예맵")
        var myaddressDialog = Dialog(activity)
        myaddressDialog.setCancelable(true)

        val preferMemberDialogView = activity!!.layoutInflater.inflate(R.layout.dialog_my_address, null)
        myaddressDialog.setContentView(preferMemberDialogView)

        myaddressDialog.rb_dialog_my_address_1.setOnCheckedChangeListener { buttonView, isChecked ->


            result = myaddressDialog.rb_dialog_my_address_1.text.toString()
            Log.v("asdf","다예맵2 + " + result)
        }

        myaddressDialog.rb_dialog_my_address_2.setOnCheckedChangeListener { buttonView, isChecked ->


            result = myaddressDialog.rb_dialog_my_address_2.text.toString()
            Log.v("asdf","다예맵2 + " + result)
        }

        myaddressDialog.rb_dialog_my_address_3.setOnCheckedChangeListener { buttonView, isChecked ->


            result = myaddressDialog.rb_dialog_my_address_3.text.toString()
            Log.v("asdf","다예맵2 + " + result)
        }

        myaddressDialog.btn_dialog_my_address_ok.setOnClickListener {
            tv_fg_cmap_my_address.text = result
            myaddressDialog.dismiss()

        }

        myaddressDialog.btn_dialog_my_address_cancel.setOnClickListener {
            myaddressDialog.dismiss()
        }

        myaddressDialog.show()
    }



//
//    private fun getLastLocation() {
//        val isGPSEnabled = activity!!.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//        val isNetworkEnabled = activity!!.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//
//
//        //gps 체크
//        if (isGPSEnabled || isNetworkEnabled) {
//            if (ActivityCompat.checkSelfPermission(
//                    activity!!,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                fusedLocationClient.lastLocation.addOnCompleteListener {
//                    if (it.isSuccessful && it.result != null) {
//                        currentLatitude = it.result!!.latitude
//                        currentLongitude = it.result!!.longitude
//
//                    }
//                }
//            }
//        } else {
//            toast("GPS를 체크해주세요.")
//        }
//    }
//


    var count = 0
    val builder = LatLngBounds.Builder()




    val mMarkerOption: MarkerOptions by lazy {
        MarkerOptions().apply {
            icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_cat))
        }
    }



    lateinit var mMarker : Marker
    lateinit var mCircle : Circle



    private fun CircleOption(data:GetMapResponse){

        when(data.category) {
            2 -> {
                mCircle = mMap.addCircle(

                    CircleOptions().center(
                        LatLng(
                            data.lng,
                            data.lat
                        )
                    ).radius(130.0).fillColor(Color.parseColor("#4Df29191")).strokeColor(Color.parseColor("#4Df29191"))

                )
                Log.v("circle","영역 " )

            }

        }

    }



    private fun addNewMarker(data:GetMapResponse) {





        when(data.category){

            2 -> {
                mMarker = mMap.addMarker(MarkerOptions().position(LatLng(data.lat, data.lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_cat)))

            }

            1-> {
                mMarker = mMap.addMarker(MarkerOptions().position(LatLng(data.lat, data.lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_hospital)))
            }

            0->{

                mMarker = mMap.addMarker(MarkerOptions().position(LatLng(data.lat, data.lng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_food)))

            }

        }





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
//        address[0].getAddressLine(0).toString()
//
//        if (address.size!=0){
//            Log.e("Address", address[0].getAddressLine(0).toString())
//        }
//


//        mMarker = mMap.addMarker(mMarkerOption)

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(currentLatitude, currentLongitude), 15.0f))
 //       count++

        builder.include(mMarker.position)
        val bounds = builder.build()
        val padding = 0 // offset from edges of the map in pixels
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)

        if (count == 4){
            mMap.moveCamera(cu)
        }
    }


}
