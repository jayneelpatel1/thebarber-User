package com.jayneel.thebarber_user.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.helper.shopListAdapter
import com.jayneel.thebarber_user.module.shopModule
import kotlinx.android.synthetic.main.activity_user_home.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import java.util.*

class user_home : AppCompatActivity() {
    var data= arrayListOf<shopModule>()
    var displaylist= arrayListOf<shopModule>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        var sp=getSharedPreferences("Login", Context.MODE_PRIVATE)
     //  textView.text="welcome ${sp.getString("unm","abc")}"
        setSupportActionBar(toolbarhome)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Shop")
        // var ad=shopListAdapter(this,data)

        myRef.addValueEventListener(object : ValueEventListener {
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
            R.id.action_logout ->{
                var sp=getSharedPreferences("Login", Context.MODE_PRIVATE)
                var edt=sp.edit()
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
}
