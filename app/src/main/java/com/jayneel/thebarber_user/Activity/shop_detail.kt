package com.jayneel.thebarber_user.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.helper.shopIteamAdapter
import com.jayneel.thebarber_user.module.iteamModule
import kotlinx.android.synthetic.main.activity_shop_detail.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class shop_detail : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        Log.i("demo","on start call")
    }
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_detail)
        var sp=getSharedPreferences("Login", Context.MODE_PRIVATE)
        var unm=sp.getString("unm","abc")
        var sunm=intent.getStringExtra("shopunm")
        Log.i("demo","on create call")
        // geting value for chips current date and next date
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatted = current.format(formatter)
        chip3.text=formatted
        val df = SimpleDateFormat("dd-MM-yyyy")
        val d: Date = df.parse(formatted.toString())
        val cal = Calendar.getInstance()
        cal.time = d
        cal.add(Calendar.DATE, 1)
        val newTime: String = df.format(cal.time)
        chip4.text=newTime



        val myRef = database.getReference("Category")
        var data= arrayListOf<iteamModule>()
        var selected= arrayListOf<iteamModule>()
        myRef.child(sunm).addListenerForSingleValueEvent(object : ValueEventListener {

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




        chipGroup.check(R.id.chip3)
        var chiptext=getchiptext()
        var st:String?= "null"



        fb_book.setOnClickListener {

            if(chipGroupSlot.isSelected && !selected.equals(null))
            {
                var time=findViewById<Chip>(chipGroupSlot.checkedChipId).text
                var chiptext=getchiptext()
                bookappiment(sunm,chiptext,time.toString(),selected,unm.toString())
                Toast.makeText(this,"Appinment Booked",Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(this,"Select time first and category",Toast.LENGTH_LONG).show()



        }

           }

    private fun bookappiment(
        sunm: String?,
        date: String,
        time: String,
        selected: ArrayList<iteamModule>,
        unm: String?
    ) {
        val myRef = database.getReference("appinment")
        myRef.child(sunm.toString()).child(date).child(time).child(unm.toString()).setValue(selected)
    }

    private fun isappinment(
        chiptext: String,
        sunm: String?,
        selected: ArrayList<iteamModule>,
        unm: String?
    ){
        val myRef = database.getReference("appinment")
        var ans= arrayListOf<iteamModule>()
        var k=""
        var tt:String=""
        if (sunm != null) {
            myRef.child(sunm).child(chiptext).addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(this@shop_detail,"Somerthing Wrog Try ofter sometime",Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(p0: DataSnapshot) {

                    for(v in p0.children)
                    {
                        k=v.key.toString()
                    }
                    for(v in p0.children){}

                    var sum:Int=0
                    for(t in ans)
                    {
                        sum=sum+t.minute.toString().toInt()
                    }

                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                    val formatted = k.format(formatter)

                        val df = SimpleDateFormat("HH:mm")
                        val d: Date = df.parse(formatted.toString())
                        val cal = Calendar.getInstance()
                        cal.time = d
                        cal.add(Calendar.MINUTE, sum)
                        val newTime: String = df.format(cal.time)
                    bookappiment(sunm,chiptext,newTime.toString(),selected,unm)
                }
            })
        }

    }
    private fun getchiptext():String {
        return findViewById<Chip>(chipGroup.checkedChipId).text.toString()
    }
}
