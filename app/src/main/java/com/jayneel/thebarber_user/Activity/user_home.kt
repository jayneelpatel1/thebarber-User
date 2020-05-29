package com.jayneel.thebarber_user.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.helper.shopListAdapter
import com.jayneel.thebarber_user.module.shopModule
import com.jayneel.thebarber_user.module.userData
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.activity_user_home.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import kotlinx.android.synthetic.main.headerlaout.*
import kotlinx.android.synthetic.main.headerlaout.view.*
import java.util.*

class user_home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var data= arrayListOf<shopModule>()
    var displaylist= arrayListOf<shopModule>()

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        mAuth = FirebaseAuth.getInstance();
        var user= mAuth!!.currentUser
        val emailVerified = user!!.isEmailVerified


        var sp=getSharedPreferences("Login", Context.MODE_PRIVATE)
     //  textView.text="welcome ${sp.getString("unm","abc")}"



        setSupportActionBar(toolbarhome)

        //creating hamburger icon
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


       var header= navigation.getHeaderView(0)
        //header.lblhradername.text=sp.getString("unm","user not found")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Shop")
        val userref = database.getReference("userdata")
        var username:String=""
        var email:String=""
        userref.child(user.uid).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var value=p0.getValue(userData::class.java)
                username= value!!.nm.toString()
                email= value!!.email.toString()
                if(value.imgurl!="") {
                    val storage = FirebaseStorage.getInstance()
                    val storageReference = storage.getReferenceFromUrl(value.imgurl!!)

                    storageReference.downloadUrl.addOnSuccessListener {
                        val imgurl = it.toString()
                        Glide.with(this@user_home).load(imgurl).into(header.imgheader).view
                    }
                }
                var sp=getSharedPreferences("username", Context.MODE_PRIVATE)
                var edt=sp.edit()
                edt.putString("username",username)
                edt.commit()
                edt.apply()
                header.lblhradername.text=username
                header.lblheaderemail.text=email
            }
        })


        // var ad=shopListAdapter(this,data)

        //Navigation drawer layout settings
        var toggler=ActionBarDrawerToggle(this,dreawerlayout,toolbar,0,0)

        dreawerlayout.addDrawerListener(toggler)
        toggler.syncState()

        navigation.setNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.nav_profile->{
                    startActivity(Intent(this,
                        update_profile::class.java)
                    )
                }
                R.id.nav_bookings->{
                    startActivity(Intent(this,
                        bookings::class.java)
                    )
                }
                R.id.nav_logout ->{
                    mAuth = FirebaseAuth.getInstance();
                    mAuth!!.signOut()
                    var sp=getSharedPreferences("Login", Context.MODE_PRIVATE)
                    var edt=sp.edit()
                    edt.clear()
                    edt.commit()
                    startActivity(Intent(this, login::class.java))
                }
            }
            dreawerlayout.closeDrawer(GravityCompat.START)
            true
        }






        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                data.clear()
                pbhome.visibility=View.VISIBLE
                var ad=shopListAdapter(this@user_home,displaylist)
              for(v in dataSnapshot.children) {
                  val value = v.getValue(shopModule::class.java)
                  Log.d("key",value.toString())
                  if (value != null) {
                        data.add(value)
                  }
              }
                displaylist.addAll(data)
                pbhome.visibility=View.GONE
                rv.adapter=ad

//                Log.d(FragmentActivity.TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //              Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })
        rv.layoutManager=LinearLayoutManager(this@user_home,LinearLayoutManager.VERTICAL,false)




    }

    override fun onBackPressed() {
        if(dreawerlayout.isDrawerOpen(GravityCompat.START))
            dreawerlayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }
    override fun onSupportNavigateUp(): Boolean {
        dreawerlayout.openDrawer(navigation,true)
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.optionmenu,menu)
        var searchiteam=menu!!.findItem(R.id.action_search)
        if(searchiteam !=null){
            val searchview=searchiteam.actionView as SearchView
            searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty()){
                        displaylist.clear()
                        val serch=newText.toLowerCase(Locale.getDefault())
                        data.forEach {
                            if(it.shopName!!.toLowerCase(Locale.getDefault()).contains(serch))
                            {
                                displaylist.add(it)
                            }
                        }
                        rv.adapter!!.notifyDataSetChanged()
                    }
                    else
                    {
                        displaylist.clear()
                        displaylist.addAll(data)
                        rv.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_logout -> {
                var sp = getSharedPreferences("Login", Context.MODE_PRIVATE)
                var edt = sp.edit()
                edt.clear()
                edt.commit()
                startActivity(Intent(this, login::class.java))
                true
            }
            R.id.action_edit ->{
                startActivity(Intent(this,
                    update_profile::class.java))
                true
            }
         else ->super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        dreawerlayout.closeDrawer(GravityCompat.START)
        return true
    }
}

