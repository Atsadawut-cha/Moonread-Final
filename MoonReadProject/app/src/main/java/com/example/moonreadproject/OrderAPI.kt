package com.example.moonreadproject

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface OrderAPI {

    @GET("allorder")
    fun showOrder(): Call<List<Order>>

    @FormUrlEncoded
    @POST("createorder")
    fun insertOrder(
        @Field("customer_id") customer_id: Int,
        @Field("total_price") total_price: Int,
        @Field("order_date") order_date: String,
        @Field("address") address: String,
        @Field("status") status: String,
        @Field("pay") pay: String,
        @Field("delivery") delivery: String
    ): Call<Order>

    @GET("getorder_id/{customer_id}")
    fun showOrderID(
        @Path("customer_id") customer_id: Int
    ): Call<Order>

    @FormUrlEncoded
    @PUT("order/{order_id}")
    fun updateOrder(
        @Path("order_id") order_id : Int,
        @Field("total_price") total_price :Int,
        @Field("order_date") order_date: String,
        @Field("address") address: String,
        @Field("status") status: String,
        @Field("pay") pay: String,
        @Field("delivery") delivery: String
    ): Call<Order>

    @GET("getordered_id/{customer_id}")
    fun showOrderHistory(
        @Path("customer_id") customer_id: Int
    ): Call<List<Order>>

    companion object{
        fun create():OrderAPI{
            val orderClient: OrderAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OrderAPI::class.java)
            return orderClient
        }
    }
}