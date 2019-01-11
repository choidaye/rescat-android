package com.rescat.rescat_android.ui.activity

import android.Manifest
import android.content.CursorLoader
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.rescat.rescat_android.R
import kotlinx.android.synthetic.main.activity_add_marker.*
import kotlinx.android.synthetic.main.activity_add_marker.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity

class AddMarkerActivity : AppCompatActivity() {


    val REQUEST_CODE_SELECT_IMAGE: Int = 1004
    val My_READ_STORAGE_REQUEST_CODE = 7777

    var imageURI : String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_marker)

        setOnBtnClickListener()
    }


    private fun setOnBtnClickListener() {
        //앨범 열기 버튼 리스너

        btn_tv_ac_add_marker_ok.setOnClickListener {
            startActivity<MarkerRequestActivity>()
        }



        btn_ac_add_marker_img_upload.setOnClickListener {
            //앨범을 열기 전 권한 요청을 한다.
            requestReadExternalStoragePermission()
        }

    }





    private fun showAlbum(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
    }


    fun getRealPathFromURI(content : Uri) : String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader : CursorLoader = CursorLoader(this, content, proj, null, null, null)
        val cursor : Cursor = loader.loadInBackground()
        val column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_idx)
        cursor.close()
        return result
    }

    //이 메소드는 외부저장소(앨범과 같은)에 접근 관련해 권한 요청을 하는 로직을 메소드로 만든 것입니다!
    private fun requestReadExternalStoragePermission(){
        //첫번째 if문을 통해 과거에 이미 권한 메시지에 대한 OK를 했는지 아닌지에 대해 물어봅니다!
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //사용자에게 권한을 왜 허용해야되는지에 메시지를 주기 위한 대한 로직을 추가하려면 이 블락에서 하면됩니다!!
                //하지만 우리는 그냥 비워놓습니다!! 딱히 줄말 없으면 비워놔도 무관해요!!! 굳이 뭐 안넣어도됩니다!
                Log.v("ygyg", "ygyg1")
//                val intent = Intent()
//                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                intent.data = Uri.fromParts("package", packageName, null)
//                startActivity(intent)
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), My_READ_STORAGE_REQUEST_CODE)

            } else {
                //아래 코드는 권한을 요청하는 메시지를 띄우는 기능을 합니다! 요청에 대한 결과는 callback으로 onRequestPermissionsResult 메소드에서 받습니다!!!
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), My_READ_STORAGE_REQUEST_CODE)
                Log.v("ygyg", "ygyg2")
            }
        } else {
            //첫번째 if문의 else로써, 기존에 이미 권한 메시지를 통해 권한을 허용했다면 아래와 같은 곧바로 앨범을 여는 메소드를 호출해주면됩니다!!
            showAlbum()
            Log.v("ygyg", "ygyg3")

        }
    }

    //외부저장소(앨범과 같은)에 접근 관련 요청에 대해 OK를 했는지 거부했는지를 callback으로 받는 메소드입니다!
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //onActivityResult와 같은 개념입니다. requestCode로 어떤 권한에 대한 callback인지를 체크합니다.
        if (requestCode == My_READ_STORAGE_REQUEST_CODE){
            //결과에 대해 허용을 눌렀는지 체크하는 조건문이구요!
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //이곳은 외부저장소 접근을 허용했을 때에 대한 로직을 쓰시면됩니다. 우리는 앨범을 여는 메소드를 호출해주면되겠죠?
                Log.v("ygyg", "ygyg4")

                showAlbum()
            } else {
                //이곳은 외부저장소 접근 거부를 했을때에 대한 로직을 넣어주시면 됩니다.
                Log.v("ygyg", "ygyg5")

                //finish()

            }
        }
    }

}
