package com.jayneel.thebarber_user.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.Adapter.bookingAdapter
import com.jayneel.thebarber_user.module.appinmrntMoule
import kotlinx.android.synthetic.main.activity_bookings.*

class bookings : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookings)
        val database = FirebaseDatabase.getInstance()
        mAuth = FirebaseAuth.getInstance();
        var user= mAuth!!.currentUser
        val userref = database.getReference("userdata")
        var arlist=ArrayList<appinmrntMoule>()
        userref.child(user!!.uid).child("history").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var ad=bookingAdapter(this@bookings,arlist)
                for(v in p0.children) {
                    var value = v.getValue(appinmrntMoule::class.java)
                    arlist.add(value!!)
                }
                rvbookings.adapter=ad
            }

        })
        rvbookings.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
    }
}
