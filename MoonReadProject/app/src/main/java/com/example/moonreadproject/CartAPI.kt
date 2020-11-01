package com.example.moonreadproject

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CartAPI {

    /*@GET("showDetail/{order_id}")
    fun showDetail(): Call<List<Cart>>*/

    @FormUrlEncoded
    @POST("createorder_detail")
    fun insertedDetail(
        @Field("order_id") cart_order_id: Int,
        @Field("book_id") cart_book_id: Int,
        @Field("qty") cart_qty: Int,
        @Field("price") cart_price: Int
    ): Call<Cart>

    @GET("show_cus_order/{order_id}")
    fun showDetail(
        @Path("order_id") order_id :Int
    ):Call<List<Cart>>

    @FormUrlEncoded
    @PUT("update/{order_id}/{detail_id}")
    fun updateOrderDetail(
        @Path("order_id") order_id: Int,
        @Path("detail_id") detail_id: Int,
        @Field("qty") cart_qty: Int,
        @Field("price") cart_price: Int
    ):Call<Cart>

    @DELETE("delete_order/{order_id}/{book_id}")
    fun deleteOrderDetail(
        @Path("order_id") order_id: Int,
        @Path("book_id") book_id : Int
    ):Call<Cart>

    companion object{
        fun create():CartAPI{
            val cartClient: CartAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CartAPI::class.java)
            return cartClient
        }
    }
}