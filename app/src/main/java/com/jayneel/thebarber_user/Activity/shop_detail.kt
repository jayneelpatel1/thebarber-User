package com.jayneel.thebarber_user.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.helper.shopIteamAdapter
import com.jayneel.thebarber_user.module.iteamModule
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
                rviteam.adapter=ad


//                Log.d(FragmentActivity.TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //              Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })
//

        rviteam.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

    }
}
