package com.jayneel.thebarber_user.Activity

import android.R.attr.fragmentAllowReturnTransitionOverlap
import android.R.attr.password
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.module.userData
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        val currentUser = mAuth!!.currentUser
        //updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if(currentUser!=null){
            Toast.makeText(this,"Register Sucessfull",Toast.LENGTH_SHORT).show()
            var sp=getSharedPreferences("Login", Context.MODE_PRIVATE)
            var edt=sp.edit()
            if (currentUser != null) {
                edt.putString("unm",currentUser.uid.toString())
            }
            edt.apply()
            edt.commit()
            startActivity(Intent(this, user_home::class.java))
            finish()

        }
        else
        {
           // Toast.makeText(this,"Try after some time",Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val uper= AnimationUtils.loadAnimation(this,R.anim.uper)
        val start= findViewById(R.id.start) as LinearLayout
        start.startAnimation(uper)



        mAuth = FirebaseAuth.getInstance();










        btnsignup.setOnClickListener {
//          val database = FirebaseDatabase.getInstance()
//          val myRef = database.getReference("userdata")
            progressBar.visibility=View.VISIBLE
                var userfirebase:FirebaseUser?=null
            //  createuser(edtemail.text.toString(),edtpass.text.toString())

            mAuth!!.createUserWithEmailAndPassword(edtemail.text.toString(), edtpass.text.toString())
                .addOnCompleteListener(this) { task ->
                    val user = mAuth!!.currentUser
                    if (user != null) {
                        userfirebase= user
                        var id= radioGroup.checkedRadioButtonId
                        var rd=findViewById<RadioButton>(id)
                        var selrd=rd.text.toString()
                        val user= userData(
                            edtname.text.toString(),
                            edtemail.text.toString(),
                            edtphon.text.toString(),
                            selrd, userfirebase!!.uid.toString()
                        )
                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.getReference("userdata")
                        var email=edtemail.text.toString()
                        myRef.child(userfirebase!!.uid).setValue(user).addOnCompleteListener {
                            progressBar.visibility=View.VISIBLE
                            updateUI(userfirebase)
                        }
                    }
                }

            }


            // Write a message to the database


        }

    private fun createuser(email: String, password: String): FirebaseUser? {
        var userid:FirebaseUser?=null

        return userid
    }
}

