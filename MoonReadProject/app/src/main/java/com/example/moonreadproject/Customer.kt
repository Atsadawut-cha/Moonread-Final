package com.example.moonreadproject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Customer(
    @Expose
@SerializedName("cus_username") val cus_username: String,

@Expose
@SerializedName("cus_password") val cus_password: String,

@Expose
@SerializedName("cus_tel") val cus_tel: Int) {}