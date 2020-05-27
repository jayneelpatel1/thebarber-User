package com.jayneel.thebarber_user.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.module.userData

class bookings : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings)
        val database = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance();
        var user= mAuth!!.currentUser
        val userref = database.getReference("userdata")
        userref.child(user!!.uid).child("History").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var value=p0.getValue(userData::class.java)
            }
        })
    }
}
