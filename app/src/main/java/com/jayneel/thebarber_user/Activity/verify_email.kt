package com.jayneel.thebarber_user.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

            user!!.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                       // Log.d(TAG, "Email sent.")
                    }
                }
        }
    }
}
