package com.jayneel.thebarber_user.helper

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.module.shopModule
import kotlinx.android.synthetic.main.booking_history_layout.view.*

class bookingAdapter(var ctx: Activity, var arlist:ArrayList<shopModule>): RecyclerView.Adapter<bookingAdapter.viewholder>() {
    inner class viewholder(v: View):RecyclerView.ViewHolder(v){
        var shopname=v.lblbsalonname
        var date=v.lblbdate

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view=ctx.layoutInflater.inflate(R.layout.booking_history_layout,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return arlist.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {

        holder.shopname.text=arlist[position].shopName.toString()
        holder.date.text=arlist[position].city.toString()
    }
}