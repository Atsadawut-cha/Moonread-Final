package com.example.moonreadproject

import retrofit2.Call
import retrofit2.http.*

interface CustomerAPI {
    @GET("allcus")
    fun retrieveCustomer(): Call<List<Customer>>

    @FormUrlEncoded
    @POST("cus")
    fun insertCus(
        @Field("cus_username") cus_username : String,
        @Field("cus_password") cus_password : String,
        @Field("cus_tel") cus_tel : String,
        @Field("type") type : Int
    ): Call<Customer>

    @GET("logincus/{cus_username}/{cus_password}")
    fun loginCus(
        @Path("cus_username") cus_username : String,
        @Path("cus_password") cus_password : String

    ): Call<Login>


}