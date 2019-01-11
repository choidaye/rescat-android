package com.rescat.rescat_android.ui.activity.caretakerAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rescat.rescat_android.Post.Response.CareTakerMobileAuthResponse;
import com.rescat.rescat_android.R;
import com.rescat.rescat_android.application.RescatApplication;
import com.rescat.rescat_android.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gominju on 07/01/2019.
 */

public class _CareTakerMobileAuthActivity extends AppCompatActivity {
    private ImageView iv_prev;
    private TextView tv_next;
    private Button btn_next;
    private Button btn_push_sms;
    private EditText et_name;
    private EditText et_phone;
    private EditText et_code;
    private NetworkService networkService;
    private String phoneNumber;
    private String userInputCode;
    private String authCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_taker_mobile_auth);

        init();

        iv_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CareTakerAuthMainActivity.class);
                startActivity(intent);
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmpty()) {
                    checkCodeValidation();
                } else {
                    Log.i("minjuLog", "checkempty false");
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmpty()) {
                    checkCodeValidation();
                } else {
                    Log.i("minjuLog", "checkempty false");
                }
            }
        });

        btn_push_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_phone.getText().toString().isEmpty()) {
                    phoneNumber = et_phone.getText().toString();
                    getAuthCode();
                    et_code.requestFocus();
                } else {
                    Toast.makeText(_CareTakerMobileAuthActivity.this, "전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getAuthCode() {
        Call<CareTakerMobileAuthResponse> codeCheckCall = networkService.getCareTakerAuthCode(phoneNumber);
        codeCheckCall.enqueue(new Callback<CareTakerMobileAuthResponse>() {
            @Override
            public void onResponse(Call<CareTakerMobileAuthResponse> call, Response<CareTakerMobileAuthResponse> response) {
                if (response.isSuccessful()) {
                    CareTakerMobileAuthResponse res = response.body();
                    authCode = res.getCode();
                } else {
                    Log.i("minjuLog", "response err: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<CareTakerMobileAuthResponse> call, Throwable t) {
                Log.i("minjuLog", "response fail " + t.getMessage().toString());
            }
        });
    }

    private void checkCodeValidation() {
        userInputCode = et_code.getText().toString();
        if (userInputCode.equals(authCode)) {
            Intent intent = new Intent(getApplicationContext(), CareTakerAreaAuthActivity.class);
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
            editor.putString("caretaker_name", et_name.getText().toString());
            editor.putString("caretaker_phone", et_phone.getText().toString());
            editor.commit();
            startActivity(intent);
        } else {
            Toast.makeText(_CareTakerMobileAuthActivity.this, "인증 코드가 틀렸습니다", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkEmpty() {
        if (et_name.getText().toString().isEmpty() || et_code.getText().toString().isEmpty() || et_phone.getText().toString().isEmpty()) {
            Toast.makeText(_CareTakerMobileAuthActivity.this, "빈 칸을 채워주세요", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void init() {
        iv_prev = findViewById(R.id.iv_caretaker_auth_prev);
        tv_next = findViewById(R.id.tv_caretaker_auth_next);
        et_name = findViewById(R.id.et_caretaker_auth_name);
        et_phone = findViewById(R.id.et_caretaker_auth_phone);
        et_code = findViewById(R.id.et_caretaker_auth_code);
        btn_next = findViewById(R.id.btn_caretaker_mobile_next);
        btn_push_sms = findViewById(R.id.btn_caretaker_auth_sms);
        networkService = RescatApplication.instance.networkService;
    }
}
