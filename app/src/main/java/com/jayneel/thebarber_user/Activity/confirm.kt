package com.jayneel.thebarber_user.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jayneel.thebarber_user.R
import org.json.JSONObject

class confirm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        var data=intent.getStringExtra("data")
   //     var json=JSONObject(intent.getStringExtra("data"))
        var obj = JSONObject(data)


        Toast.makeText(this,obj.toString(),Toast.LENGTH_LONG).show()
    }
}
