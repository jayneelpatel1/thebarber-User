package com.jayneel.thebarber_user.Adapter

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.module.appinmrntMoule
import com.jayneel.thebarber_user.module.ratingModel
import kotlinx.android.synthetic.main.activity_select_city.*
import kotlinx.android.synthetic.main.activity_user_home.*
import kotlinx.android.synthetic.main.booking_history_layout.view.*
import kotlinx.android.synthetic.main.rating.*
import kotlinx.android.synthetic.main.rating.view.*

class bookingAdapter(var ctx: Activity, var arlist:ArrayList<appinmrntMoule>): RecyclerView.Adapter<bookingAdapter.viewholder>() {
    private var mAuth: FirebaseAuth? = null
    inner class viewholder(v: View):RecyclerView.ViewHolder(v){
        var shopname=v.lblbsalonname
        var date=v.lblbdate
        var satus=v.lblstatus

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view=ctx.layoutInflater.inflate(R.layout.booking_history_layout,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return arlist.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {

        var shopid=arlist[position].shopname.toString()
        holder.shopname.text=arlist[position].nm.toString()
        holder.date.text=arlist[position].date.toString()
        holder.satus.text=arlist[position].staus.toString()
        holder.itemView.setOnClickListener {

            var layoy=ctx.layoutInflater.inflate(R.layout.rating,null)
        var m= MaterialAlertDialogBuilder(ctx)
               .setTitle("Give us your honest Rating")
            .setMessage("Rating aap to bija ne khbar pade")
               .setView(layoy)
            .setPositiveButton("ok") { dialog, which ->
                // Respond to positive button press
                mAuth = FirebaseAuth.getInstance();
                var user= mAuth!!.currentUser?.uid
                var ratecontrol= layoy.ratingBar2.rating
                var ref=FirebaseDatabase.getInstance().getReference("Shop")
                var uniq=ref.child(shopid).child("rating").push().key
                var data=ratingModel(uniq,shopid,ratecontrol,user)
                ref.child(shopid).child("rating").child(uniq.toString()).setValue(data).addOnCompleteListener {
                    Toast.makeText(ctx,"Rating added",Toast.LENGTH_LONG).show()
                }
            }
            m.show()



//            //  builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            builder.setCancelable(false)
//            builder.setTitle("Give us your honest Rating")
//            builder.setContentView(R.layout.rating)
//            var btn=builder.findViewById<Button>(R.id.btnrating)
//            btn.setOnClickListener {
//
//            }
//            builder.show()
        }
    }
}