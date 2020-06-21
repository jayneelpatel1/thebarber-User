package com.jayneel.thebarber_user.servies

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jayneel.thebarber_user.Activity.MainActivity
import com.jayneel.thebarber_user.Activity.user_home
import com.jayneel.thebarber_user.R

open class MyFirebaseInstanceIdService: FirebaseMessagingService(){

    val TAG = "PushNotifService"
    lateinit var name: String

    fun onTokenRefresh() {
        // Mengambil token perangkat
        val token = FirebaseInstanceId.getInstance().token
        Toast.makeText(this,"$token",Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Token perangkat ini: ${token}")

        // Jika ingin mengirim push notifcation ke satu atau sekelompok perangkat,
        // simpan token ke server di sini. {
    }


}