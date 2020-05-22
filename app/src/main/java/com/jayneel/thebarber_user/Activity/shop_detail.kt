package com.jayneel.thebarber_user.Activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.helper.shopIteamAdapter
import com.jayneel.thebarber_user.module.book
import com.jayneel.thebarber_user.module.iteamModule
import com.jayneel.thebarber_user.module.shopModule
import kotlinx.android.synthetic.main.activity_shop_detail.*

class shop_detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_detail)
        var sp=getSharedPreferences("Login", Context.MODE_PRIVATE)
        var unm=sp.getString("unm","abc")
        var sunm=intent.getStringExtra("shopunm")
        Log.d("key",sunm.toString())



        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Category")

        var data= arrayListOf<iteamModule>()
        var selected= arrayListOf<iteamModule>()
        myRef.child(sunm).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var ad= shopIteamAdapter(this@shop_detail,data)
                for(v in dataSnapshot.children) {
                    val value = v.getValue(iteamModule::class.java)
                    Log.d("key",value.toString())
                    if (value != null) {
                        data.add(value)
                    }
                }
                selected=ad.getselectedlist()
                rviteam.adapter=ad


//                Log.d(FragmentActivity.TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //              Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })
//
        fab_book.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("appinment")
            val ref=database.getReference("Shop")
            var time:String?=null

            ref.child(sunm).addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                  //  var ad= shopIteamAdapter(this@shop_detail,data)
                        val value =dataSnapshot.getValue(shopModule::class.java)
                        Log.d("key",value.toString())
                        if (value != null) {
                            time=value.openingTime.toString()
                            var builder=AlertDialog.Builder(this@shop_detail)
                            builder.setTitle("Confirm Booking")
                            builder.setMessage("You have selected ${selected.size} and your booking appoinment on ${time.toString()}")
                            builder.setPositiveButton("confirm",
                                DialogInterface.OnClickListener { dialog, id ->
                                    // FIRE ZE MISSILES!
                                })
                            builder.show()
                        }
                    }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    //              Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
                }
            })






//            var int1=Intent(this,confirm::class.java)
//            int1.putExtra("data",selected.toString())
//            startActivity(int1)

//            for (i in selected)
//            {
//
//                var book=book(i.name.toString())
//                myRef.child(sunm).child(unm.toString()).child(i.name.toString()).setValue(book).addOnCompleteListener{
//                    j++
//                    Toast.makeText(this,"$selected",Toast.LENGTH_LONG).show()
//                }
//            }




        }
        rviteam.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }
}
