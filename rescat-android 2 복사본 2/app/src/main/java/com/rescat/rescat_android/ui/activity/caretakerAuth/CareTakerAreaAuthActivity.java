package com.rescat.rescat_android.ui.activity.caretakerAuth;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.rescat.rescat_android.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by gominju on 08/01/2019.
 */

public class CareTakerAreaAuthActivity extends AppCompatActivity {
    private Button btn_next, btn_refresh;
    private ImageView iv_prev;

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;
    private GpsInfo gps;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_taker_area_auth);

        initView();
        initGPS();

        iv_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CareTakerMobileAuthActivity.class);
                startActivity(intent);
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGPS();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이거 그냥 넘어가도 되나?
                Intent intent = new Intent(getApplicationContext(), CareTakerPhotoAuthActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initGPS() {
        if (!isPermission) {
            Log.i("minjuLog", "GPS isPermission false!");
            callPermission();
            return;
        }

        gps = new GpsInfo(CareTakerAreaAuthActivity.this);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {
            double latitude = gps.getLatitude(); //String.valueOf(latitude)
            double longitude = gps.getLongitude();
            Log.i("minjuLog", "위도 " + latitude  + ", 경도 : " + longitude);

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

            Geocoder gCoder = new Geocoder(getApplicationContext(), Locale.KOREA);
            List<Address> addrArray = null;
            try {
                addrArray = gCoder.getFromLocation(latitude, longitude, 1);
                Address addr = addrArray.get(0);
                String modifiedAddr = addr.getAdminArea()+" "+addr.getLocality()+" "+addr.getThoroughfare();
                Log.i("minjuLog", "사용자 위치: " + modifiedAddr + ", addr: " + addr.toString());
                btn_refresh.setText(addrArray.get(0).getAddressLine(0).toString().substring(5));
//                btn_refresh.setText(modifiedAddr);

                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                editor.putString("caretaker_region", modifiedAddr);
                editor.commit();

            } catch (IOException e) {
                e.printStackTrace();
                Log.i("minjuLog", "addr trans err");
            }
        } else {
            // GPS 를 사용할수 없으므로
            Log.i("minjuLog", "GPS isGetLocation false");
            gps.showSettingsAlert();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }


    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
            initGPS();
            Log.i("minjuLog", "GPS isPermission true!");

        }

    }

    private void initView() {
        btn_next = findViewById(R.id.btn_caretaker_area_next);
        btn_refresh = findViewById(R.id.btn_caretaker_area_refresh);
        iv_prev = findViewById(R.id.iv_caretaker_area_prev);


        // 사용자가 위치 정보를 허용했는지 확인
        // 허용 안한 경우 -> 팝업

        // 허용 한 경우 -> 버튼 안에 텍스트를 현 위치 주소로 변경
    }
}
