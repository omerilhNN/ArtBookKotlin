package com.omrilhn.artbookkotlin

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.omrilhn.artbookkotlin.databinding.ActivityArtBinding

class ArtActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArtBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>//Start activity for a certain result
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    var selectedBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        registerLauncher()
    }

    fun saveButtonClicked(view: View) {

    }

    fun selectImage(view: View) {/*Reaching users gallery is at 'Dangerous' protection level so check twice with manifest and
           if block down below. If it was Internet permission just add it to the Manifest.*/
        
        //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) use -> READ_MEDIA_IMAGES instead of EXTERNAL_STORAGE

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) //If permission is denied to the GALLERY
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {//Check whether it needs to show Permission rationale
                Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Give Permission", View.OnClickListener {
                        //Request permission

                    }).show()

            } else {//request permission without pop up
                activityResultLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)//Take pic from Media
            activityResultLauncher.launch(intentToGallery)
        }
    }

    private fun registerLauncher() {//go into the gallery and select Image that you want
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if(result.resultCode == RESULT_OK)//If user pressed OK button
            {
                val intentFromResult = result.data
                if(intentFromResult!=null)
                {
                    val imageData = intentFromResult.data
                    if(imageData != null){
                        try{
                            if(Build.VERSION.SDK_INT >= 28){
                                val source = ImageDecoder.createSource(this@ArtActivity.contentResolver,imageData)
                                selectedBitmap = ImageDecoder.decodeBitmap(source)
                                binding.selectImage.setImageBitmap(selectedBitmap)
                            }else{
                                selectedBitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageData)
                                binding.selectImage.setImageBitmap(selectedBitmap)
                            }
                        }catch(e: Exception)
                        {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        //Check whether permission is granted or not then do Something.
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){result ->
            if(result)
            {//permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
            else{//permission denied
                //Show system Message to the user
                Toast.makeText(this@ArtActivity,"Permission needed!", Toast.LENGTH_LONG).show()
            }
        }
    }

}