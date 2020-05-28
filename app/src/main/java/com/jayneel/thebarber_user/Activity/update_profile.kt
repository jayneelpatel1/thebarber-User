package com.jayneel.thebarber_user.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.module.userData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_update_profile.*
import java.util.*


class update_profile : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("userdata")
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private var d:ByteArray?=null
    private var username:String?=null
    private var flg:String="false"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

         var mAuth: FirebaseAuth? = null
        mAuth = FirebaseAuth.getInstance();
        var user= mAuth!!.currentUser
        username= user!!.uid




        var email=""
        // Read from the database
        var gender: String = ""
        var userid: String = ""
        var imgurl:String=""
        // Read from the database
        myRef.child(username!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(userData::class.java)
                if (value != null) {
                    lblupdateusername.text = value.email
                    edtuphone.setText(value.ph)
                    eduemail.setText(value.nm)
                    gender = value.gender.toString()
                    userid = value.userid.toString()
                    imgurl=value.imgurl.toString()
                    if(value.imgurl!="") {
                        val storage = FirebaseStorage.getInstance()
                        val storageReference = storage.getReferenceFromUrl(value.imgurl!!)

                        storageReference.downloadUrl.addOnSuccessListener {
                            val imgurl = it.toString()
                            Glide.with(this@update_profile).load(imgurl).into(profileupdate).view
                        }
                    }

                }


//                Log.d(FragmentActivity.TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //              Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })

        profileupdate.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }

        btnupsave.setOnClickListener {
            uploadImage()
            var user =
                userData( eduemail.text.toString(),lblupdateusername.text.toString(), edtuphone.text.toString(), gender,userid,imgurl)
            myRef.child(username!!).setValue(user).addOnCompleteListener {
                Toast.makeText(this, "data updated", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.getData() != null )
        {
            filePath=data.data
            var bitmap= MediaStore.Images.Media.getBitmap(contentResolver,filePath)
            profileupdate.setImageBitmap(bitmap)


        }
    }


    private fun uploadImage(){
        storageReference = FirebaseStorage.getInstance().reference

        if(filePath != null){
            progressBar3.visibility= View.VISIBLE
            val ref = storageReference?.child("userpic/" +  UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            if (uploadTask != null) {
                var imgdata=  ref
                uploadTask.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = ref.downloadUrl
                        myRef.child(username!!).child("imgurl").setValue(imgdata.toString()).addOnCompleteListener {
                            progressBar3.visibility= View.GONE
                            Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                        }


                    } else {
                        // Handle failures
                    }
                }
                uploadTask.addOnFailureListener{
                    progressBar.visibility= View.GONE

                    Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }

    }
}
