package com.example.taskcrud.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskcrud.Model.UserModel
import com.example.taskcrud.R
import com.example.taskcrud.Repository.UserRepositoryImpl
import com.example.taskcrud.databinding.ActivityRegistrationBinding
import com.example.taskcrud.utils.ImageUtils
import com.example.taskcrud.viewModel.UserViewModel
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.util.UUID

class RegistrationActivity : AppCompatActivity() {
    lateinit var registrationBinding: ActivityRegistrationBinding
    var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref = firebaseDatabase.reference.child("user")

    lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    var imageUri : Uri? = null

    lateinit var imageUtils: ImageUtils

    lateinit var userViewmodel: UserViewModel

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            activityResultLauncher.launch(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        registrationBinding=ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(registrationBinding.root)

        val repo= UserRepositoryImpl()
        userViewmodel= UserViewModel(repo)
        imageUtils = ImageUtils(this)
        imageUtils.registerActivity { url ->
            url.let {
                imageUri = url
                Picasso.get().load(url).into(registrationBinding.imagebrowse)
            }
        }
        registrationBinding.imagebrowse.setOnClickListener{
            imageUtils.launchGallery(this)
        }
        registrationBinding.buttonregister2.setOnClickListener {
            if (imageUri != null){
                uploadImage()
            }else{
                Toast.makeText(applicationContext,"Please upload image first",Toast.LENGTH_LONG)
                    .show()
            }
        }
        registrationBinding.buttonregister2.setOnClickListener {
            uploadImage()
//            var name : String = registrationBinding.editname.text.toString()
//            var email : String = registrationBinding.editemail.text.toString()
//            var number : Int = registrationBinding.editNumber.text.toString().toInt()
//            var password : String = registrationBinding.editPassword.text.toString()
//
//            Toast.makeText(applicationContext, "Registration Success", Toast.LENGTH_LONG).show()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun uploadImage(){
        val imageName = UUID.randomUUID().toString()
        imageUri?.let {
            userViewmodel.uploadImage(imageName,it){ success, imageUrl ->
                if (success){
                    addUser(imageUrl.toString(),imageName.toString())
                }
            }

        }

    }
    //adduser
    fun addUser(url:String, imageName: String){
        var name : String = registrationBinding.editname.text.toString()
        var email : String = registrationBinding.editemail.text.toString()
        var number : Int = registrationBinding.editNumber.text.toString().toInt()
        var password : String = registrationBinding.editPassword.text.toString()
        var data = UserModel("",name,email,number,password,url,imageName)

        userViewmodel.addUser(data) { success, message ->
            if (success) {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

            }
        }


    }
}
