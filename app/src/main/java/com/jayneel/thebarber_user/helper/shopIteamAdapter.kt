package com.jayneel.thebarber_user.helper

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.module.iteamModule
import kotlinx.android.synthetic.main.shop_iteam_list.view.*

class shopIteamAdapter(var ctx:Activity,var arlist:ArrayList<iteamModule>):RecyclerView.Adapter<shopIteamAdapter.viewholder>() {
    inner class viewholder(v:View):RecyclerView.ViewHolder(v){
        var name=v.lbliteamnm
        var price=v.lblprice
        var chk=v.chkbook
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
       var view=ctx.layoutInflater.inflate(R.layout.shop_list_cust,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return arlist.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.name.text= arlist[position].name
        holder.price.text= arlist[position].price
    }
}