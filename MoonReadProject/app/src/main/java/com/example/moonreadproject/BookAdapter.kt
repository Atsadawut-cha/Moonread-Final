package com.example.moonreadproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Response


class BookAdapter (val item : List<Book>, val context: Context): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view_item = LayoutInflater.from(parent.context).inflate(R.layout.activity_show_all_book,parent,false)
//        return ViewHolder(view_item)
        val myHolder = ViewHolder(view_item)

        view_item.setOnClickListener(){
            val pos = myHolder.adapterPosition
            val context:Context = parent.getContext()
            val book = item[pos]
            val intent = Intent(context,MoreInfo::class.java)
            intent.putExtra("book_id",book.book_id.toString())
            intent.putExtra("book_name",book.book_name)
            intent.putExtra("img_book",book.img_book)
            intent.putExtra("book_price",book.book_price.toString())
            intent.putExtra("book_type",book.book_type)
            intent.putExtra("book_detail",book.book_detail)
            //intent.putExtra("customer_id",book.customer_id.toString())
            context.startActivity(intent)

        }
        return myHolder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.book_id.text=item[position].book_id.toString()
        //holder.cus_id.text=item[position].customer_id.toString()
        holder.book_name.text=item[position].book_name
        holder.book_price.text=item[position].book_price.toString()+ " บาท"
        //if (context != null) {
            Glide.with(context)
                .load(item[position].img_book)
                .into(holder.img_book)
       // }
        //holder.book_type.text=item[position].book_type
        //holder.book_detail.text=item[position].book_detail

    }



    override fun getItemCount(): Int {
        return item.size
    }

}