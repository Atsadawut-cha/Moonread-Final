package com.example.moonreadproject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Mail(

    @Expose
    @SerializedName("order_id") val order_id :Int,

    @Expose
    @SerializedName("customer_id") val customer_id :Int,

    @Expose
    @SerializedName("total_price") val total_price :Int,

    @Expose
    @SerializedName("order_date") val order_date :String,

    @Expose
    @SerializedName("address") val address :String,

    @Expose
    @SerializedName("status") val status :String) {
}