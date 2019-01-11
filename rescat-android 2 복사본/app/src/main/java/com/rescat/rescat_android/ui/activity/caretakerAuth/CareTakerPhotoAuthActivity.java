package com.rescat.rescat_android.ui.activity.caretakerAuth;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.rescat.rescat_android.Post.CareTakerAuth;
import com.rescat.rescat_android.Post.Response.CareTakerAuthResponse;
import com.rescat.rescat_android.Post.Response.PhotoControllerResponse;
import com.rescat.rescat_android.R;
import com.rescat.rescat_android.application.RescatApplication;
import com.rescat.rescat_android.network.NetworkService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by gominju on 08/01/2019.
 */

public class CareTakerPhotoAuthActivity extends AppCompatActivity {
    private Button btn_finish;
    private ImageView iv_prev, iv_photo, iv_status_lightPink;
    private NetworkService networkService;
    private String TEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJSeWFuZ1QiLCJ1c2VyX2lkeCI6NDcsImV4cCI6MTU0OTcyMDA3MH0.vslv1Wl003DMdM1oBVqrnNP-Vrtmt6eaOJxrCd2Zj7I";

    // 카메라 사진
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2; //가져온 사진을 자르기 위한 변수
    private boolean isPhotoExist = false;
    private Uri photoUri;
    private String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}; //권한 설정 변수
    private static final int MULTIPLE_PERMISSIONS = 101; //권한 동의 여부 문의 후 CallBack 함수에 쓰일 변수

    private String imgUrlStr = "";
    private MultipartBody.Part photoBody;
    private String photoUriFromServer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_taker_photo_auth);

        initView();

        iv_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CareTakerAreaAuthActivity.class);
                startActivity(intent);
            }
        });

        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPhotoExist) {
                    changeBar();
                    // popup
                    final CustomDialog dialog = new CustomDialog(CareTakerPhotoAuthActivity.this);
                    dialog.show();
                    careTakerAuthNetwork();
                } else {
                    Toast.makeText(getApplicationContext(), "사진을 넣어주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getPhotoUri() {
        // 먼저 MultipartBody를 만듬
        if (imgUrlStr == "") {
            Log.i("minjuLog", "imgUrlStr null");
            photoBody = null;
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다

            InputStream in = null; // here, you need to get your context.
            try {
                in = getApplicationContext().getContentResolver().openInputStream(photoUri);
                Log.i("minjuLog", "photoUri: " + photoUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ), 압축된 바이트 배열을 담을 스트림

            Log.i("minjuLOg", "height: " + bitmap.getHeight() + ", width : " + bitmap.getWidth());
//            int height = bitmap.getHeight();
//            int width = bitmap.getWidth();
//            Bitmap resized = null;
//            while (height > 118) {
//                resized = Bitmap.createScaledBitmap(bitmap, (width * 118) / height, 118, true);
//                height = resized.getHeight();
//                width = resized.getWidth();
//            }
//            Log.i("minjuLOg", "resized height: " + resized.getHeight() + ", width : " + resized.getWidth());
//            ByteArrayOutputStream resizedBaos = new ByteArrayOutputStream();
//            resized.compress(Bitmap.CompressFormat.JPEG, 20, resizedBaos);

            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

            File photo = new File(imgUrlStr); // 가져온 파일의 이름을 알아내려고 사용합니다

            // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
            photoBody = MultipartBody.Part.createFormData("data", photo.getName(), requestBody);
            Log.i("minjuLog", "photoBody: " + photoBody + ", photo.getName: " + photo.getName());
        }

        // 포토 컨트롤러 네트워킹
        Call<PhotoControllerResponse> responseCall = networkService.getPhtoUri(TEST_TOKEN, photoBody);
        responseCall.enqueue(new Callback<PhotoControllerResponse>() {
            @Override
            public void onResponse(Call<PhotoControllerResponse> call, Response<PhotoControllerResponse> response) {
                if (response.isSuccessful()) {
                    photoUriFromServer = response.body().getPhotoUrl();
                    Log.i("minjuLog", "photoUriFromServer: " + photoUriFromServer);
                } else {
                    Log.i("minjuLog", "PhotoControllerResponse err: " + response.toString() + ", " + response.body() + ", "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<PhotoControllerResponse> call, Throwable t) {
                Log.i("minjuLog", "PhotoControllerResponse err: " + t.toString());
            }
        });

    }


    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //사진을 찍기 위하여 설정합니다.
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(CareTakerPhotoAuthActivity.this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(CareTakerPhotoAuthActivity.this,
                    "com.rescat.rescat_android.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); //사진을 찍어 해당 Content uri를 photoUri에 적용시키기 위함
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    // Android M에서는 Uri.fromFile 함수를 사용하였으나 7.0부터는 이 함수를 사용할 시 FileUriExposedException이
    // 발생하므로 아래와 같이 함수를 작성합니다.
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "IP" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/test/"); //test라는 경로에 이미지를 저장하기 위함
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[0])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                            }
                        } else if (permissions[i].equals(this.permissions[1])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        } else if (permissions[i].equals(this.permissions[2])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();

                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
                return;
            }
        }
    }

    //권한 획득에 동의를 하지 않았을 경우 아래 Toast 메세지를 띄우며 해당 Activity를 종료시킵니다.
    private void showNoPermissionToastAndFinish() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            Toast.makeText(CareTakerPhotoAuthActivity.this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            isPhotoExist = false;
        }
        if (requestCode == PICK_FROM_CAMERA) {
            cropImage();
            MediaScannerConnection.scanFile(CareTakerPhotoAuthActivity.this, //앨범에 사진을 보여주기 위해 Scan을 합니다.
                    new String[]{photoUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
            isPhotoExist = true; // 사진있다고 마크

        } else if (requestCode == CROP_FROM_CAMERA) {
            try { //저는 bitmap 형태의 이미지로 가져오기 위해 아래와 같이 작업하였으며 Thumbnail을 추출하였습니다.

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                Bitmap thumbImage = ThumbnailUtils.extractThumbnail(bitmap, 128, 128);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, bs); //이미지가 클 경우 OutOfMemoryException 발생이 예상되어 압축

                //여기서는 ImageView에 setImageBitmap을 활용하여 해당 이미지에 그림을 띄우시면 됩니다.
                iv_photo.setImageBitmap(getRoundedCornerBitmap(thumbImage, 10));
                isPhotoExist = true; // 사진있다고 마크

                // TODO step2. <photo controller API> 를 통해서 photoUrl 서버에서 받기
                getPhotoUri();
            } catch (Exception e) {
                Log.e("minjuLog", e.getMessage().toString());
            }
        }
        Log.i("minjuLog", "photoUri: " + photoUri);
    }

    public void cropImage() {
        this.grantUriPermission("com.android.camera", photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        grantUriPermission(list.get(0).activityInfo.packageName, photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "용량이 큰 사진의 경우 시간이 오래 걸릴 수 있습니다.", Toast.LENGTH_SHORT).show();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 4);
            intent.putExtra("aspectY", 3);
            intent.putExtra("scale", true);
            File croppedFileName = null;
            try {
                croppedFileName = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            File folder = new File(Environment.getExternalStorageDirectory() + "/test/");
            File tempFile = new File(folder.toString(), croppedFileName.getName());
            imgUrlStr = croppedFileName.getName();
            Log.i("minjuLog", "imgUrlStr: " + imgUrlStr);

            photoUri = FileProvider.getUriForFile(CareTakerPhotoAuthActivity.this,
                    "com.rescat.rescat_android.fileprovider", tempFile);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); //Bitmap 형태로 받기 위해 해당 작업 진행

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            grantUriPermission(res.activityInfo.packageName, photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, CROP_FROM_CAMERA);
        }

    }


    /* 비트맵 모서리 둥글게*/
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int px) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = px;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private void careTakerAuthNetwork() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String photoUri = photoUriFromServer;
        String nickname = "고민주";
        String phone = preferences.getString("caretaker_phone", "");
        String regionFullName = preferences.getString("caretaker_region", "");
        int type = 0;
        String name = preferences.getString("caretaker_name", "");

        CareTakerAuth careTakerAuth = new CareTakerAuth(photoUri, nickname, phone, regionFullName, type, name);

        Call<Void> authResponseCall = networkService.getCareTakerAuth(TEST_TOKEN, careTakerAuth);
        authResponseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    Log.i("minjuLog", "케어테이커 인증 성공 ");
                } else {
                    // TODO 409 에러 --> 이미 등록된 유저니깐 그거에 따른 예외처리
                    Log.i("minjuLog", "케어테이커 인증 에러 " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i("minjuLog", "케어테이커 인증 실패 " + t.getMessage().toString());
            }
        });

//        Call<CareTakerAuthResponse> authResponseCall = networkService.getCareTakerAuth(TEST_TOKEN, careTakerAuth);
//        authResponseCall.enqueue(new Callback<CareTakerAuthResponse>() {
//            @Override
//            public void onResponse(Call<CareTakerAuthResponse> call, Response<CareTakerAuthResponse> response) {
//                if(response.isSuccessful()) {
//                    Log.i("minjuLog", "케어테이커 인증 성공 " + response.body().getStatusCode());
//                } else {
//                    // TODO 409 에러 --> 이미 등록된 유저니깐 그거에 따른 예외처리
//                    Log.i("minjuLog", "케어테이커 인증 에러 " + response.toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CareTakerAuthResponse> call, Throwable t) {
//                Log.i("minjuLog", "케어테이커 인증 실패 " + t.getMessage().toString());
//            }
//        });
    }

    private void changeBar() {
        iv_status_lightPink.setBackgroundColor(getResources().getColor(R.color.pink));
    }

    private void initView() {
        btn_finish = findViewById(R.id.btn_caretaker_photo_finish);
        iv_prev = findViewById(R.id.iv_caretaker_photo_prev);
        iv_photo = findViewById(R.id.iv_caretaker_photo_frame);
        iv_status_lightPink = findViewById(R.id.iv_status_lightPink);
        networkService = RescatApplication.instance.networkService;
    }
}
