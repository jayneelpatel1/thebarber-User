package com.jayneel.thebarber_user.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.module.userData
import kotlinx.android.synthetic.main.activity_login.*


class login : AppCompatActivity() {
// Write a message to the database

    private var mAuth: FirebaseAuth? = null
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("userdata")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //declare animation
        val uper= AnimationUtils.loadAnimation(this,R.anim.uper)
        val start= findViewById(R.id.start) as LinearLayout
        start.startAnimation(uper)


        mAuth = FirebaseAuth.getInstance();
        val currentUser = mAuth!!.currentUser

        if(currentUser!=null){
            startActivity(Intent(this,
                user_home::class.java))
            finish()
        }

//
//        var sp=getSharedPreferences("Login", Context.MODE_PRIVATE)
//        var unm=sp.getString("unm","abc")
//        if(unm!="abc"){
//            startActivity(Intent(this,
//                user_home::class.java))
//            finish()
//        }
        btngotosignup.setOnClickListener {
            startActivity(Intent(this,
                MainActivity::class.java))
        }


        btnlogin.setOnClickListener {
            progressBar2.visibility=View.VISIBLE
            mAuth!!.signInWithEmailAndPassword(edtunm.text.toString(), edtlpass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(TAG, "signInWithEmail:success")
                        val user = mAuth!!.currentUser
                        progressBar2.visibility=View.GONE
                        startActivity(Intent(this,
                            user_home::class.java))
                        finish()
                       // updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w(TAG, "signInWithEmail:failure", task.exception)
                        progressBar2.visibility=View.GONE

                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                       // updateUI(null)
                        // ...
                    }

                    // ...
                }

//            myRef.child(edtunm.text.toString()).addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//                    val value = dataSnapshot.getValue(userData::class.java)
//                    if (value != null) {
//                        if(value.pass.equals(edtlpass.text.toString())){
//                            var sp=getSharedPreferences("Login", Context.MODE_PRIVATE)
//                            var edt=sp.edit()
//                            edt.putString("unm",edtunm.text.toString())
//                            edt.apply()
//                            edt.commit()
//                            startActivity(Intent(this@login,
//                                user_home::class.java))
//                            finish()
//                        }
//                        else
//                        {
//                            Toast.makeText(this@login,"invalid user name or password",Toast.LENGTH_SHORT).show()
//                        }
//
//                    }
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    // Failed to read value
//                    //Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
//                }
//            })
        }
    }
}
