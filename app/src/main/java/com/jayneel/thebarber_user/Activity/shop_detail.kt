package com.jayneel.thebarber_user.Activity

import android.content.Context
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
import com.jayneel.thebarber_user.module.iteamModule
import com.jayneel.thebarber_user.module.shopModule
import kotlinx.android.synthetic.main.activity_shop_detail.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
                rv_book.adapter=ad

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@shop_detail,"Something wrong",Toast.LENGTH_LONG).show()
            }
        })
        rv_book.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        fb_book.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("appinment")
            val ref=database.getReference("Shop")
            var time:String?=null
            ref.child(sunm).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val value = dataSnapshot.getValue(shopModule::class.java)
                      //  Log.d("key",value.toString())
                        if (value != null) {
                            time=value.openingTime.toString()
                            val builder = AlertDialog.Builder(this@shop_detail)
                            //set title for alert dialog
                            builder.setTitle("Cinfirm Your booking")
                            //set message for alert dialog
                            builder.setMessage("Hiii you Booked ${selected.size} and you will be assigne to on $time")
                            builder.setIcon(android.R.drawable.ic_dialog_alert)

                            //performing positive action
                                builder.setPositiveButton("Yes"){dialogInterface, which ->

                                myRef.child(sunm).child(unm.toString()).setValue(selected).addOnCompleteListener {
                                  //  ref.child(sunm).child("openingTime").setValue(time.conver)
                                    val current = time
                                    var add:Int=0
                                    for(i in selected)
                                    {
                                        add=i.minute!!+add
                                    }

                                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                                    val formatted = current!!.format(formatter)
                                    val df = SimpleDateFormat("HH:mm")
                                    val d: Date = df.parse(formatted.toString())
                                    val cal = Calendar.getInstance()
                                    cal.time = d
                                    cal.add(Calendar.MINUTE, add)
                                    val newTime: String = df.format(cal.time)
                                    ref.child(sunm).child("openingTime").setValue(newTime)
                                    myRef.child(sunm).child(unm.toString()).child("time").setValue(time)
                                    Toast.makeText(applicationContext,"Appinment Booked",Toast.LENGTH_LONG).show()
                                }


                            }
                            //performing cancel action
                            builder.setNeutralButton("Cancel"){dialogInterface , which ->
                                Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
                            }
                            //performing negative action
                            builder.setNegativeButton("No"){dialogInterface, which ->
                                Toast.makeText(applicationContext,"clicked No",Toast.LENGTH_LONG).show()
                            }
                            // Create the AlertDialog
                            val alertDialog: AlertDialog = builder.create()
                            // Set other dialog properties
                            alertDialog.setCancelable(false)
                            alertDialog.show()
                        }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@shop_detail,"Something wrong",Toast.LENGTH_LONG).show()
                }
            })





        }
    }
}
