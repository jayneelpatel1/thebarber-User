package com.jayneel.thebarber_user.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.jayneel.thebarber_user.R
import kotlinx.android.synthetic.main.activity_forget__password.*

class forget_Password : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget__password)


        mAuth = FirebaseAuth.getInstance();

        button.setOnClickListener {
            val emailAddress = edtforgetemail.text.toString()

            mAuth!!.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"link send",Toast.LENGTH_SHORT).show()
                        startActivity(
                            Intent(this,
                            user_home::class.java)
                        )
                        finish()
                    }
                }
        }
    }
}
