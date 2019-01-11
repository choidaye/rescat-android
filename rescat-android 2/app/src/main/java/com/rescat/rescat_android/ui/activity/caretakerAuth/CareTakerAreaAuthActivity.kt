package com.rescat.rescat_android.ui.activity.caretakerAuth

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView

import com.rescat.rescat_android.R

import java.io.IOException
import java.util.Locale

/**
 * Created by gominju on 08/01/2019.
 */

class CareTakerAreaAuthActivity : AppCompatActivity() {
    private var btn_next: Button? = null
    private var btn_refresh: Button? = null
    private var iv_prev: ImageView? = null

    private val PERMISSIONS_ACCESS_FINE_LOCATION = 1000
    private val PERMISSIONS_ACCESS_COARSE_LOCATION = 1001
    private var isAccessFineLocation = false
    private var isAccessCoarseLocation = false
    private var isPermission = false
    private var gps: GpsInfo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_taker_area_auth)

        initView()
        initGPS()

        iv_prev!!.setOnClickListener {
            val intent = Intent(applicationContext, CareTakerMobileAuthActivity::class.java)
            startActivity(intent)
        }

        btn_refresh!!.setOnClickListener { initGPS() }

        btn_next!!.setOnClickListener {
            // 이거 그냥 넘어가도 되나?
            val intent = Intent(applicationContext, CareTakerPhotoAuthActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initGPS() {
        if (!isPermission) {
            Log.i("minjuLog", "GPS isPermission false!")
            callPermission()
            return
        }

        gps = GpsInfo(this@CareTakerAreaAuthActivity)
        // GPS 사용유무 가져오기
        if (gps!!.isGetLocation) {
            val latitude = gps!!.latitude //String.valueOf(latitude)
            val longitude = gps!!.longitude
            Log.i("minjuLog", "위도 $latitude, 경도 : $longitude")

            // 위도 경도 -> 주소로 바꾸기
            //            GpsToAddress gps;
            //            try {
            //                gps = new GpsToAddress(latitude, longitude);
            //                btn_refresh.setText(gps.getAddress());
            //                Log.i("minjuLog", "한글 주소 : " + gps.getAddress());
            //            } catch (Exception e) {
            //                Log.i("minjuLog", "한글 주소 변환 실패, " + e.toString());
            //                e.printStackTrace();
            //            }

            val gCoder = Geocoder(applicationContext, Locale.KOREA)
            var addrArray: List<Address>? = null
            try {
                addrArray = gCoder.getFromLocation(latitude, longitude, 1)
                val addr = addrArray!![0]
                //                String modifiedAddr = addr.getAdminArea()+" "+addr.getLocality()+" "+addr.getThoroughfare();
                Log.i("minjuLog", "사용자 위치: " + addrArray[0].getAddressLine(0))
                //                Log.i("minjuLog", "getLocality: " + addr.getLocality() + ", getSubLocality:  " + addr.getSubLocality() + ", " );
                val userAddr = addr.locality + " " + addr.subLocality + " " + addr.thoroughfare
                btn_refresh!!.text = userAddr

                val editor = PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
                editor.putString("caretaker_region", userAddr)
                editor.commit()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.i("minjuLog", "addr trans err")
            }

        } else {
            // GPS 를 사용할수 없으므로
            Log.i("minjuLog", "GPS isGetLocation false")
            gps!!.showSettingsAlert()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessCoarseLocation = true
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true
        }
    }


    private fun callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_ACCESS_FINE_LOCATION)

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    PERMISSIONS_ACCESS_COARSE_LOCATION)
        } else {
            isPermission = true
            initGPS()
            Log.i("minjuLog", "GPS isPermission true!")

        }

    }

    private fun initView() {
        btn_next = findViewById(R.id.btn_caretaker_area_next)
        btn_refresh = findViewById(R.id.btn_caretaker_area_refresh)
        iv_prev = findViewById(R.id.iv_caretaker_area_prev)


        // 사용자가 위치 정보를 허용했는지 확인
        // 허용 안한 경우 -> 팝업

        // 허용 한 경우 -> 버튼 안에 텍스트를 현 위치 주소로 변경
    }
}
