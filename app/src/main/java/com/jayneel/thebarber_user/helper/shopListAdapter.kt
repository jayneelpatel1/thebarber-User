package com.jayneel.thebarber_user.helper

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.jayneel.thebarber_user.Activity.shop_detail
import com.jayneel.thebarber_user.R
import com.jayneel.thebarber_user.module.shopModule
import kotlinx.android.synthetic.main.shop_list_cust.view.*

class shopListAdapter(var ctx:Activity,var arlist:ArrayList<shopModule>):RecyclerView.Adapter<shopListAdapter.viewholder>() {
    inner class viewholder(v:View):RecyclerView.ViewHolder(v){
        var shopname=v.lblshopnm
        var imageView=v.item_image
        var imageback=v.imageView4
        var list_constrain=v.list_constrain
        var city=v.lblcity

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view=ctx.layoutInflater.inflate(R.layout.shop_list_cust,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
       return arlist.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {

        if(arlist[position].imgurl.toString()!="") {
            val storage = FirebaseStorage.getInstance()
            val storageReference = storage.getReferenceFromUrl(arlist[position].imgurl.toString())

            storageReference.downloadUrl.addOnSuccessListener {
                val imgurl = it.toString()
                Glide.with(ctx).load(imgurl).into(holder.imageView).view
            }
        }
        holder.list_constrain.startAnimation(AnimationUtils.loadAnimation(ctx,R.anim.rvdata))
        holder.imageView.startAnimation(AnimationUtils.loadAnimation(ctx,R.anim.rv))

        holder.shopname.text=arlist[position].shopName.toString()
        holder.city.text=arlist[position].city.toString()
        holder.itemView.setOnClickListener {
            var int1=Intent(ctx,shop_detail::class.java)
            int1.putExtra("shopunm",arlist[position].userName.toString())
            ctx.startActivity(int1)
        }
    }
}