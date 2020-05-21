package com.jayneel.thebarber_user.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.jayneel.thebarber_user.R

class SplaceScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splace_screen)
        val zoom_in= AnimationUtils.loadAnimation(this,R.anim.zoom_in)
        val imageView= findViewById(R.id.imageView) as ImageView
        imageView.startAnimation(zoom_in)
        val zoom_logo= AnimationUtils.loadAnimation(this,R.anim.zoom_logo)
        val imagelogo= findViewById(R.id.imagelogo) as ImageView
        imagelogo.startAnimation(zoom_logo)

        Handler().postDelayed({
            startActivity(Intent(this,login::class.java))
            finish()
        },2500)
    }
}
