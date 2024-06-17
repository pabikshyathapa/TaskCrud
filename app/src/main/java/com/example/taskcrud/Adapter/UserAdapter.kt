package com.example.taskcrud.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.taskcrud.Model.UserModel
import com.example.taskcrud.R
import com.example.taskcrud.ui.UpdateUserActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class UserAdapter(var context: Context,var data:ArrayList<UserModel>): RecyclerView.Adapter<UserAdapter.UserViewHolder> (){
    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var email:TextView = view.findViewById(R.id.labelEmail)
        var number: TextView = view.findViewById(R.id.labelNumber)
        var password: TextView = view.findViewById(R.id.labelPassword)
        var btnEdit : TextView = view.findViewById(R.id.btnEdit)
        var progressBar : ProgressBar = view.findViewById(R.id.bar)
        var image: ImageView = view.findViewById(R.id.display)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.sample_login,parent,false)
        return  UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  data.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.email.text = data[position].email
        holder.number.text = data[position].number.toInt().toString()
        holder.password.text = data[position].password

        var image = data[position].url

        Picasso.get().load(image).into(holder.image, object : Callback{
            override fun onSuccess() {
                holder.progressBar.visibility = View.INVISIBLE
            }

            override fun onError(e: Exception?) {
                Toast.makeText(context,e?.localizedMessage,Toast.LENGTH_LONG).show()
            }
        })
        holder.btnEdit.setOnClickListener {
            var intent = Intent(context, UpdateUserActivity::class.java)
            intent.putExtra("user",data[position])
            context.startActivity(intent)
        }
    }
    fun getUserId(position: Int): String{
        return data[position].id
    }
    fun getImageName(position :Int): String{
        return data[position].imageName
    }
    fun updateData(user:List<UserModel>){
        data.clear()
        data.addAll(user)
        notifyDataSetChanged()
    }

}