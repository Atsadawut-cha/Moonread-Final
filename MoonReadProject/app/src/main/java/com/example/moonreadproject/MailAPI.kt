package com.example.moonreadproject

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MailAPI {

    @GET("allorder")
    fun showOrder(): Call<List<Mail>>

    /*@GET("order/{customer_id}")
    fun CusShowOrder(
        @Path("customer_id") customer_id :Int
    ): Call<List<Mail>>*/

    companion object{
        fun create():MailAPI{
            val mailClient: MailAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MailAPI::class.java)
            return mailClient
        }
    }

}