package com.example.moonreadproject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder

data class Book(
//    @Expose
//    @SerializedName("customer_id") val customer_id: Int,

    @Expose
    @SerializedName("book_id") val book_id: Int,

    @Expose
    @SerializedName("book_name") val book_name: String,

    @Expose
    @SerializedName("book_price") val book_price: Int,

    @Expose
    @SerializedName("img_book") val img_book: String,

    @Expose
    @SerializedName("book_type") val book_type: String,

    @Expose
    @SerializedName("book_detail") val book_detail: String

){}