package com.jayneel.thebarber_user.Activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.module.userData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_update_profile.*


class update_profile : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("userdata")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        var sp = getSharedPreferences("Login", Context.MODE_PRIVATE)
        var unm = sp.getString("unm", "abc")

        // Read from the database
        var gender: String = ""
        var userid: String = ""
        // Read from the database
        myRef.child(unm!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(userData::class.java)
                if (value != null) {
                    textView2.text = value.nm
                    edtuphone.setText(value.ph)
                    eduemail.setText(value.email)
                    gender = value.gender.toString()
                    userid = value.userid.toString()


                }


//                Log.d(FragmentActivity.TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //              Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })

        btnupsave.setOnClickListener {
            var user =
                userData(unm, eduemail.text.toString(), edtuphone.text.toString(), gender,userid)
            myRef.child(unm!!).setValue(user).addOnCompleteListener {
                Toast.makeText(this, "data updated", Toast.LENGTH_LONG).show()

            }

        }
    }
}
