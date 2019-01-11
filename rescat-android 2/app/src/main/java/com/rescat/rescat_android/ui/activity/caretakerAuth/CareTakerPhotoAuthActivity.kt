package com.rescat.rescat_android.ui.activity.caretakerAuth

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.media.ExifInterface
import android.media.MediaScannerConnection
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import com.rescat.rescat_android.Post.CareTakerAuth
import com.rescat.rescat_android.Post.Response.CareTakerAuthResponse
import com.rescat.rescat_android.Post.Response.PhotoControllerResponse
import com.rescat.rescat_android.R
import com.rescat.rescat_android.application.RescatApplication
import com.rescat.rescat_android.network.NetworkService
import com.rescat.rescat_android.ui.activity.MainActivity
import com.rescat.rescat_android.ui.fragment.helpcat.HelpCatFragment

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by gominju on 08/01/2019.
 */

class CareTakerPhotoAuthActivity : AppCompatActivity() {
    private var btn_finish: Button? = null
    private var iv_prev: ImageView? = null
    private var iv_photo: ImageView? = null
    private var iv_status_lightPink: ImageView? = null
    private var networkService: NetworkService? = null
    private val TEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJSeWFuZ1QiLCJ1c2VyX2lkeCI6NDcsImV4cCI6MTU0OTg3OTc4NH0.lPzwNfA9MHK4DBE52c0SL4N9rXlnsSv0qyw10WlaHoY"
    private var isPhotoExist = false
    private var photoUri: Uri? = null
    private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA) //권한 설정 변수

    private var imgUrlStr = ""
    private var photoBody: MultipartBody.Part? = null
    private var photoUriFromServer: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_taker_photo_auth)

        initView()
        checkPermissions()

        iv_prev!!.setOnClickListener {
            val intent = Intent(applicationContext, CareTakerAreaAuthActivity::class.java)
            startActivity(intent)
        }

        iv_photo!!.setOnClickListener { takePhoto() }

        btn_finish!!.setOnClickListener {
            if (isPhotoExist) {
                changeBar()
                // popup
                val dialog = CustomDialog(this@CareTakerPhotoAuthActivity)
                dialog.show()
                careTakerAuthNetwork()
            } else {
                Toast.makeText(applicationContext, "사진을 넣어주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPhotoUri() {
        // 먼저 MultipartBody를 만듬
        if (imgUrlStr === "") {
            Log.i("minjuLog", "imgUrlStr null")
            photoBody = null
        } else {
            val options = BitmapFactory.Options()
            options.inSampleSize = 10 //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다

            var `in`: InputStream? = null // here, you need to get your context.
            try {
                `in` = applicationContext.contentResolver.openInputStream(photoUri!!)
                Log.i("minjuLog", "photoUri: " + photoUri!!)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            val bitmap = BitmapFactory.decodeStream(`in`, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
            val baos = ByteArrayOutputStream()
            //            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

            // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ), 압축된 바이트 배열을 담을 스트림

            Log.i("minjuLOg", "height: " + bitmap.height + ", width : " + bitmap.width)
            //            int height = bitmap.getHeight();
            //            int width = bitmap.getWidth();
            //            Bitmap resized = null;
            //            while (height > 118) {
            //                resized = Bitmap.createScaledBitmap(bitmap, (width * 118) / height, 118, true);
            //                height = resized.getHeight();
            //                width = resized.getWidth();
            //            Log.i("minjuLOg", "resized height: " + resized.getHeight() + ", width : " + resized.getWidth());
            //            ByteArrayOutputStream resizedBaos = new ByteArrayOutputStream();
            //            resized.compress(Bitmap.CompressFormat.JPEG, 20, resizedBaos);

            val requestBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())

            val photo = File(imgUrlStr) // 가져온 파일의 이름을 알아내려고 사용합니다

            // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
            photoBody = MultipartBody.Part.createFormData("data", photo.name, requestBody)
            Log.i("minjuLog", "photoBody: " + photoBody + ", photo.getName: " + photo.name)
        }

        // 포토 컨트롤러 네트워킹
        val responseCall = networkService!!.getPhtoUri(photoBody!!)
        responseCall.enqueue(object : Callback<PhotoControllerResponse> {
            override fun onResponse(call: Call<PhotoControllerResponse>, response: Response<PhotoControllerResponse>) {
                if (response.isSuccessful) {
                    photoUriFromServer = response.body()!!.photoUrl
                    Log.i("minjuLog", "photoUriFromServer: " + photoUriFromServer!!)
                } else {
                    Log.i("minjuLog", "PhotoControllerResponse err: " + response.toString() + ", " + response.body() + ", " + response.code())
                }
            }

            override fun onFailure(call: Call<PhotoControllerResponse>, t: Throwable) {
                Log.i("minjuLog", "PhotoControllerResponse err: " + t.toString())
            }
        })

    }


    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE) //사진을 찍기 위하여 설정합니다.
        var photoFile: File? = null
        try {
            photoFile = createImageFile()
        } catch (e: IOException) {
            Toast.makeText(this@CareTakerPhotoAuthActivity, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            Log.i("minjuLog", "이미지 처리 오류 : " + e.message.toString())
            finish()
        }

        if (photoFile != null) {
            photoUri = FileProvider.getUriForFile(this@CareTakerPhotoAuthActivity,
                    "com.rescat.rescat_android.fileprovider", photoFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri) //사진을 찍어 해당 Content uri를 photoUri에 적용시키기 위함
            startActivityForResult(intent, PICK_FROM_CAMERA)
        }
    }

    // Android M에서는 Uri.fromFile 함수를 사용하였으나 7.0부터는 이 함수를 사용할 시 FileUriExposedException이
    // 발생하므로 아래와 같이 함수를 작성합니다.
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("HHmmss").format(Date())
        val imageFileName = "CareTaker_" + timeStamp + "_"
        val storageDir = File(Environment.getExternalStorageDirectory().toString() + "/test/") //test라는 경로에 이미지를 저장하기 위함
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MULTIPLE_PERMISSIONS -> {
                if (grantResults.size > 0) {
                    for (i in permissions.indices) {
                        if (permissions[i] == this.permissions[0]) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish()
                            }
                        } else if (permissions[i] == this.permissions[1]) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish()

                            }
                        } else if (permissions[i] == this.permissions[2]) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish()

                            }
                        }
                    }
                } else {
                    showNoPermissionToastAndFinish()
                }
                return
            }
        }
    }

    private fun checkPermissions(): Boolean {
        var result: Int
        val permissionList = ArrayList<String>()
        for (pm in permissions) {
            result = ContextCompat.checkSelfPermission(this, pm)
            if (result != PackageManager.PERMISSION_GRANTED) { //사용자가 해당 권한을 가지고 있지 않을 경우 리스트에 해당 권한명 추가
                permissionList.add(pm)
            }
        }
        if (!permissionList.isEmpty()) { //권한이 추가되었으면 해당 리스트가 empty가 아니므로 request 즉 권한을 요청합니다.
            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), MULTIPLE_PERMISSIONS)
            return false
        }
        return true
    }

    //권한 획득에 동의를 하지 않았을 경우 아래 Toast 메세지를 띄우며 해당 Activity를 종료시킵니다.
    private fun showNoPermissionToastAndFinish() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this@CareTakerPhotoAuthActivity, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            isPhotoExist = false
        }
        if (requestCode == PICK_FROM_CAMERA) {
            cropImage()
            MediaScannerConnection.scanFile(this@CareTakerPhotoAuthActivity, //앨범에 사진을 보여주기 위해 Scan을 합니다.
                    arrayOf(photoUri!!.path), null
            ) { path, uri -> }
            isPhotoExist = true // 사진있다고 마크

        } else if (requestCode == CROP_FROM_CAMERA) {
            try { //저는 bitmap 형태의 이미지로 가져오기 위해 아래와 같이 작업하였으며 Thumbnail을 추출하였습니다.

                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
                val thumbImage = ThumbnailUtils.extractThumbnail(bitmap, 128, 128)
                val bs = ByteArrayOutputStream()
                thumbImage.compress(Bitmap.CompressFormat.JPEG, 100, bs) //이미지가 클 경우 OutOfMemoryException 발생이 예상되어 압축

                //여기서는 ImageView에 setImageBitmap을 활용하여 해당 이미지에 그림을 띄우시면 됩니다.
                iv_photo!!.setImageBitmap(getRoundedCornerBitmap(thumbImage, 10))
                isPhotoExist = true // 사진있다고 마크

                // TODO step2. <photo controller API> 를 통해서 photoUrl 서버에서 받기
                getPhotoUri()
            } catch (e: Exception) {
                Log.e("minjuLog", e.message.toString())
            }

        }
        Log.i("minjuLog", "photoUri: " + photoUri!!)
    }

    fun cropImage() {
        this.grantUriPermission("com.android.camera", photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(photoUri, "image/*")

        val list = packageManager.queryIntentActivities(intent, 0)
        grantUriPermission(list[0].activityInfo.packageName, photoUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val size = list.size
        if (size == 0) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show()
            return
        } else {
            Toast.makeText(this, "용량이 큰 사진의 경우 시간이 오래 걸릴 수 있습니다.", Toast.LENGTH_SHORT).show()
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.putExtra("crop", "true")
            intent.putExtra("aspectX", 1)
            intent.putExtra("aspectY", 1)
            intent.putExtra("scale", true)
            var croppedFileName: File? = null
            try {
                croppedFileName = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val folder = File(Environment.getExternalStorageDirectory().toString() + "/test/")
            val tempFile = File(folder.toString(), croppedFileName!!.name)
            imgUrlStr = croppedFileName.name
            Log.i("minjuLog", "imgUrlStr: $imgUrlStr")

            photoUri = FileProvider.getUriForFile(this@CareTakerPhotoAuthActivity,
                    "com.rescat.rescat_android.fileprovider", tempFile)

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)


            intent.putExtra("return-data", false)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()) //Bitmap 형태로 받기 위해 해당 작업 진행

            val i = Intent(intent)
            val res = list[0]
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            grantUriPermission(res.activityInfo.packageName, photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)

            i.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            startActivityForResult(i, CROP_FROM_CAMERA)
        }

    }

    private fun careTakerAuthNetwork() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val photoUri = photoUriFromServer
//        val nickname = preferences.getString("USER_NICKNAME", "")
        var nickname = RescatApplication.preference.nickname
        val phone = preferences.getString("caretaker_phone", "")
        val regionFullName = preferences.getString("caretaker_region", "")
        val type = 0
        val name = preferences.getString("caretaker_name", "")
        Log.i("minjuLog", "nickname: " + nickname + ", phone: " + phone + ", regionFullName: " + regionFullName + ", name: " + name)
        Log.i("minjuLog", "is careTaker? " + RescatApplication.preference.careTaker)
        val careTakerAuth = CareTakerAuth(photoUri!!, nickname!!, phone!!, regionFullName!!, type, name!!)

        val authResponseCall = networkService!!.getCareTakerAuth(careTakerAuth)
        authResponseCall.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Log.i("minjuLog", "케어테이커 인증 성공 ")
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    RescatApplication.preference.careTaker = "1"
                } else {
                    RescatApplication.preference.careTaker = "0"
                    // TODO 404 에러


                    // TODO 409 에러 --> 이미 등록된 유저니깐 그거에 따른 예외처리
                    if (response.code() == 409) {
                        Toast.makeText(applicationContext, "케어테이커 인증이 처리중입니다 잠시만 기다려주세요", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        RescatApplication.preference.careTaker = "1"
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "케어테이커 인증 에러입니다 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                        Log.i("minjuLog", "케어테이커 인증 에러 " + response.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.i("minjuLog", "케어테이커 인증 실패 " + t.message.toString())
                Toast.makeText(applicationContext, "케어테이커 인증 서버 에러입니다 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                RescatApplication.preference.careTaker = "0"
            }
        })

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

    private fun changeBar() {
        iv_status_lightPink!!.setBackgroundColor(resources.getColor(R.color.pink))
    }

    private fun initView() {
        btn_finish = findViewById(R.id.btn_caretaker_photo_finish)
        iv_prev = findViewById(R.id.iv_caretaker_photo_prev)
        iv_photo = findViewById(R.id.iv_caretaker_photo_frame)
        iv_status_lightPink = findViewById(R.id.iv_status_lightPink)
        networkService = RescatApplication.instance.networkService
    }

    companion object {

        // 카메라 사진
        private val PICK_FROM_CAMERA = 1
        private val CROP_FROM_CAMERA = 2 //가져온 사진을 자르기 위한 변수
        private val MULTIPLE_PERMISSIONS = 101 //권한 동의 여부 문의 후 CallBack 함수에 쓰일 변수


        /* 비트맵 모서리 둥글게*/
        fun getRoundedCornerBitmap(bitmap: Bitmap, px: Int): Bitmap {
            val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(output)
            val color = -0xbdbdbe
            val paint = Paint()
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)
            val roundPx = px.toFloat()
            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)
            return output
        }
    }
}
