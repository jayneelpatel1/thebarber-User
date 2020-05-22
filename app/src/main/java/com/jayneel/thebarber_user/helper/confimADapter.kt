package com.jayneel.thebarber_user.helper

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.module.iteamModule
import kotlinx.android.synthetic.main.confrim_list.view.*

class confimADapter(var ctx:Activity,var arlist:ArrayList<iteamModule>):RecyclerView.Adapter<confimADapter.viewholder>() {
    inner class viewholder(v:View):RecyclerView.ViewHolder(v){
        var price=v.lblcprice
        var name=v.lblcname
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(ctx.layoutInflater.inflate(R.layout.activity_confirm,parent,false))
    }

    override fun getItemCount(): Int {
       return arlist.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {

        holder.name.text=arlist[position].name
        holder.price.text=arlist[position].price
    }
}