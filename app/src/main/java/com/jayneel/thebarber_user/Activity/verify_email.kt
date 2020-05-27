package com.jayneel.thebarber_user.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.jayneel.thebarber_user.R
import kotlinx.android.synthetic.main.activity_verify_email.*

class verify_email : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_email)
        btnverify.setOnClickListener {


            mAuth = FirebaseAuth.getInstance();
            var user= mAuth!!.currentUser
            val emailVerified = user!!.isEmailVerified
            if(emailVerified){
                startActivity(Intent(this,user_home::class.java))
                finish()
            }

            user!!.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                       // Log.d(TAG, "Email sent.")
                        Toast.makeText(this,"Mail sent to your mail",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,login::class.java))
                        finish()
                    }
                }
        }
    }
}
