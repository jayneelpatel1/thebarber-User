package com.jayneel.thebarber_user.helper

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.module.iteamModule
import kotlinx.android.synthetic.main.shop_iteam_list.view.*

class shopIteamAdapter(var ctx:Activity,var arlist:ArrayList<iteamModule>):RecyclerView.Adapter<shopIteamAdapter.viewholder>() {



    var chekedteacher=ArrayList<iteamModule>()

    inner class viewholder(v:View):RecyclerView.ViewHolder(v){
        var name=v.lbliteamnm
        var price=v.lblprice
        var chk=v.chkbook
        var container=v.container

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
       var view=ctx.layoutInflater.inflate(R.layout.shop_iteam_list,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return arlist.size
    }
    fun getselectedlist():ArrayList<iteamModule>
    {
        return chekedteacher
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {

        var anim=AnimationUtils.loadAnimation(ctx,R.anim.rv)
        holder.name.startAnimation(anim)
        holder.container.startAnimation(AnimationUtils.loadAnimation(ctx,R.anim.rvdata))
        holder.name.text= arlist[position].name
        holder.price.text= arlist[position].price+" ₹"

        holder.itemView.chkbook.setOnCheckedChangeListener { buttonView, isChecked ->
            val list=arlist[position]
            if(isChecked)
            {
                chekedteacher.add(list)
            }
            else
            {
                chekedteacher.remove(list)
            }

        }
    }
}