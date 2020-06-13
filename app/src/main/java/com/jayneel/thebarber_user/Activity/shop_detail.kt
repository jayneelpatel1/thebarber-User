package com.jayneel.thebarber_user.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.helper.shopIteamAdapter
import com.jayneel.thebarber_user.module.appinmrntMoule
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
    private var mAuth: FirebaseAuth? = null
    private var shopst:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_detail)
        shopst=intent.getStringExtra("st")
        var sunm=intent.getStringExtra("shopunm")
        // geting value for chips current date and next date

        mAuth = FirebaseAuth.getInstance();
        var user= mAuth!!.currentUser
        var unm= user!!.uid



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
                        if(value.status.equals("On"))
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




        val ref= database.getReference("appinment")
        var arlist=ArrayList<String>()
        ref.child(sunm.toString()).child(chiptext).child("time").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                    for (v in p0.children)
                    {
                        var value=v.getValue(String::class.java)
                        arlist.add(value.toString())
                    }
                visibalchip()

                for (i in arlist)
                {
                    chipdisable(i)

                }
                arlist.clear()
              //  Toast.makeText(this@shop_detail,arlist.toString(),Toast.LENGTH_LONG).show()
            }
        })
        arlist.clear()


        chip3.setOnClickListener {
            visibalchip()
            val ref= database.getReference("appinment")
            var arlist=ArrayList<String>()
            ref.child(sunm.toString()).child(chip3.text.toString()).child("time").addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (v in p0.children)
                    {
                        var value=v.getValue(String::class.java)
                        arlist.add(value.toString())
                    }
                    visibalchip()
                    for (i in arlist)
                    {
                        chipdisable(i)

                    }
                    arlist.clear()
                    //  Toast.makeText(this@shop_detail,arlist.toString(),Toast.LENGTH_LONG).show()
                }
            })
            arlist.clear()
        }

        chip4.setOnClickListener {
            visibalchip()
            val ref= database.getReference("appinment")
            var sr=ArrayList<String>()
            ref.child(sunm.toString()).child(chip4.text.toString()).child("time").addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (v in p0.children)
                    {
                        var value=v.getValue(String::class.java)
                        sr.add(value.toString())
                    }
                    visibalchip()

                    for (i in sr)
                    {
                        chipdisable(i)

                    }
                    sr.clear()
                    //  Toast.makeText(this@shop_detail,arlist.toString(),Toast.LENGTH_LONG).show()
                }
            })
            sr.clear()
        }





        if(shopst.equals("close",true)){
            MaterialAlertDialogBuilder(this@shop_detail)
                .setTitle("Shop is close")
                .setMessage("We are sorry we open soon")
                .setPositiveButton("ok") { dialog, which ->
                    startActivity(Intent(this,user_home::class.java))

                }
                .show()
        }

        fb_book.setOnClickListener {



           var chip= chipGroupSlot.checkedChipId

            if(chip >0 &&!selected.equals(null))
            {
                var chipid=findViewById<Chip>(chipGroupSlot.checkedChipId)
                var time=chipid.text

                var chiptext=getchiptext()


                        MaterialAlertDialogBuilder(this@shop_detail)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure you want to book appointment for $chiptext at ${time.toString()}")
                            .setPositiveButton("accept") { dialog, which ->
                                // Respond to positive button press
                               var uniq= bookhistory(sunm,chiptext,time.toString(),selected,unm.toString(),chipid)
                                bookappiment(sunm,chiptext,time.toString(),selected,unm.toString(),chipid,uniq)


                                Toast.makeText(this,"Appointment  Booked",Toast.LENGTH_LONG).show()
                                chip=-1
                                startActivity(Intent(this,user_home::class.java))
                                finish()

                            }
                            .setNegativeButton("Cancel"){dialog, which ->
                                Toast.makeText(this,"Appointment Cancelled",Toast.LENGTH_LONG).show()
                            }
                            .show()


            }
            else
                Toast.makeText(this,"Please select time first",Toast.LENGTH_LONG).show()



        }

           }

    private fun chipdisable(i: String) {
        if(chip21.text.equals(i))
            chip21.visibility=View.GONE
        if(chip20.text.equals(i))
            chip20.visibility=View.GONE
        if(chip2.text.equals(i))
            chip2.visibility=View.GONE
        if(chip5.text.equals(i))
            chip5.visibility=View.GONE
        if(chip6.text.equals(i))
            chip6.visibility=View.GONE
        if(chip7.text.equals(i))
            chip7.visibility=View.GONE
        if(chip8.text.equals(i))
            chip8.visibility=View.GONE
        if(chip9.text.equals(i))
            chip9.visibility=View.GONE
        if(chip10.text.equals(i))
            chip10.visibility=View.GONE
        if(chip11.text.equals(i))
            chip11.visibility=View.GONE
        if(chip12.text.equals(i))
            chip12.visibility=View.GONE
        if(chip13.text.equals(i))
            chip13.visibility=View.GONE
        if(chip14.text.equals(i))
            chip14.visibility=View.GONE
        if(chip15.text.equals(i))
            chip15.visibility=View.GONE
        if(chip16.text.equals(i))
            chip16.visibility=View.GONE
        if(chip17.text.equals(i))
            chip17.visibility=View.GONE
        if(chip18.text.equals(i))
            chip18.visibility=View.GONE
        if(chip19.text.equals(i))
            chip19.visibility=View.GONE

    }

    private fun visibalchip() {
            chip21.visibility=View.VISIBLE
            chip20.visibility=View.VISIBLE
            chip2.visibility=View.VISIBLE
            chip5.visibility=View.VISIBLE
            chip6.visibility=View.VISIBLE
            chip7.visibility=View.VISIBLE
            chip8.visibility=View.VISIBLE
            chip9.visibility=View.VISIBLE
            chip10.visibility=View.VISIBLE
            chip11.visibility=View.VISIBLE
            chip12.visibility=View.VISIBLE
            chip13.visibility=View.VISIBLE
            chip14.visibility=View.VISIBLE
            chip15.visibility=View.VISIBLE
            chip16.visibility=View.VISIBLE
            chip17.visibility=View.VISIBLE
            chip18.visibility=View.VISIBLE
            chip19.visibility=View.VISIBLE
    }

    private fun bookappiment(
        sunm: String?,
        date: String,
        time: String,
        selected: ArrayList<iteamModule>,
        unm: String?,
        chipid:Chip?,uniq:String
    ) {
        val myRef = database.getReference("appinment")
        var sp=getSharedPreferences("username",Context.MODE_PRIVATE)
        var username=sp.getString("username",unm)
        var ap=appinmrntMoule(date,time,selected,username,"",sunm,uniq)
        myRef.child(sunm.toString()).child(date).child(time).setValue(ap).addOnCompleteListener {
            var key=myRef.child(sunm.toString()).child(date).child("time").push().key
            myRef.child(sunm.toString()).child(date).child("time").child(key.toString()).setValue(time)
        }
    }
    private fun bookhistory(
        sunm: String?,
        date: String,
        time: String,
        selected: ArrayList<iteamModule>,
        unm: String?,
        chipid:Chip?
    ):String {
        val myRef = database.getReference("userdata")
        var uniq= myRef.child(unm!!).child("history").push().key
        var ap=appinmrntMoule(date,time,selected,unm,"Remaining",sunm,uniq)

        myRef.child(unm!!).child("history").child(uniq!!).setValue(ap).addOnCompleteListener {
         //   Toast.makeText(this,"add to history",Toast.LENGTH_SHORT).show()
        }
        return uniq
    }

    private fun getchiptext():String {
        return findViewById<Chip>(chipGroup.checkedChipId).text.toString()
    }
}
