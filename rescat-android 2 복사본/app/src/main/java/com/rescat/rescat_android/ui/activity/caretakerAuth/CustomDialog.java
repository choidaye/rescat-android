package com.rescat.rescat_android.ui.activity.caretakerAuth;

/**
 * Created by gominju on 08/01/2019.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.rescat.rescat_android.R;

public class CustomDialog extends Dialog {
    private Button btn_finish;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("minjuLog", "customDialog");
        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 바 삭제
        setContentView(R.layout.dialog_caretaker_auth_finish);

        btn_finish = findViewById(R.id.btn_finish);

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}

