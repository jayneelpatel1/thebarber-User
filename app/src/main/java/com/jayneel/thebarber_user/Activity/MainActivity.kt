package com.jayneel.thebarber_user.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.module.userData
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnsignup.setOnClickListener {
//            val database = FirebaseDatabase.getInstance()
//            val myRef = database.getReference("userdata")
            var id= radioGroup.checkedRadioButtonId
            var rd=findViewById<RadioButton>(id)
            var selrd=rd.text.toString()
            val user= userData(
                edtname.text.toString(),
                edtemail.text.toString(),
                edtphon.text.toString(),
                edtpass.text.toString(),
                selrd
            )
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("userdata")
            var email=edtemail.text.toString()
            myRef.child(edtname.text.toString()).setValue(user).addOnCompleteListener {
                Toast.makeText(this,"Register Sucessfull",Toast.LENGTH_SHORT).show()
                var sp=getSharedPreferences("Login", Context.MODE_PRIVATE)
                var edt=sp.edit()
                edt.putString("unm",edtname.text.toString())
                edt.apply()
                edt.commit()
                startActivity(Intent(this, user_home::class.java))
                finish()
            }
           }
            // Write a message to the database


        }
    }

