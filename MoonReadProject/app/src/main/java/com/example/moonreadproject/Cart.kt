package com.example.moonreadproject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cart(

    @Expose
    @SerializedName("detail_id") val cart_orderDetail_id: Int,

    @Expose
    @SerializedName("order_id") val cart_order_id: Int,

    @Expose
    @SerializedName("book_id") val cart_book_id: Int,

    @Expose
    @SerializedName("book_name") val cart_book_name: String,

    @Expose
    @SerializedName("book_price") val cart_book_price: Int,

    @Expose
    @SerializedName("qty") val cart_qty: Int,

    @Expose
    @SerializedName("price") val cart_price: Int) {
}