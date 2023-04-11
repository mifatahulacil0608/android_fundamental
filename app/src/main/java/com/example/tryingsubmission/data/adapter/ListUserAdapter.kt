package com.example.tryingsubmission.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.tryingsubmission.data.remote.response.ItemsItem
import com.example.tryingsubmission.R
import com.example.tryingsubmission.ui.DetailUserActivity
import de.hdodenhof.circleimageview.CircleImageView

class ListUserAdapter(private val listUser: List<ItemsItem>) :
    RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoUser: CircleImageView = itemView.findViewById(R.id.img_item_photo)
        val nameUser: TextView = itemView.findViewById(R.id.tv_item_name)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
    )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val userLogin = listUser[position]

        viewHolder.nameUser.text = userLogin.login

        println(viewHolder.nameUser)
        Glide.with(viewHolder.itemView.context)
            .load(userLogin.avatarUrl)
            .into(viewHolder.photoUser)


        viewHolder.itemView.setOnClickListener{
            val intentDetail = Intent(viewHolder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra("usernameLogin",userLogin.login)
            intentDetail.putExtra("avatarUrl",userLogin.avatarUrl)
            viewHolder.itemView.context.startActivity(intentDetail)
        }

        /*viewHolder.itemView.setOnClickListener {
            val intentDetail = Intent(viewHolder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra(DetailUserActivity.USER, userLogin.login)
            intentDetail.putExtra(DetailUserActivity.AVATARURL, userLogin.avatarUrl)
            viewHolder.itemView.context.startActivity(intentDetail)
        }*/
    }

    override fun getItemCount() = listUser.size

}