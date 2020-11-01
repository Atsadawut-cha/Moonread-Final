package com.example.moonreadproject

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_show_all_book.view.*

class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
    //val book_id = view.book_id
    val cus_id = view.cus_id
    val book_name = view.book_name
    val book_price = view.book_price
    val img_book = view.img_book
    //val book_type = view.book_type
    //val book_detail = view.book_detail

}

