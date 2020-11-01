package com.example.moonreadproject

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface BookAPI {
    @GET("allbook")
    fun retrieveBook(): Call<List<Book>>

    companion object{
        fun create(): BookAPI{
            val bookClient: BookAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BookAPI::class.java)
            return bookClient
        }
    }

    @FormUrlEncoded
    @POST("book")
    fun insertBook(
        @Field("book_name") book_name :String,
        @Field("book_price") book_price :Int,
        @Field("img_book") img_book :String,
        @Field("book_type") book_type :String,
        @Field("book_detail") book_detail :String

    ):Call<Book>


    @FormUrlEncoded
    @PUT("book/{book_id}")
    fun updateBook(
        @Path("book_id") book_id: Int,
        @Field("book_name") book_name:String,
        @Field("book_price") book_price:Int,
        @Field("img_book") img_book:String,
        @Field("book_type") book_type:String,
        @Field("book_detail") book_detail: String
    ): Call<Book>

    @DELETE("book/{book_id}")
    fun deleteBook(
        @Path("book_id") book_id: Int):Call<Book>

    @GET("book/{keyword}")
    fun searchbook(
        @Path("keyword") book_name: String
    ): Call<List<Book>>

    @GET("bookFiction")
    fun getFiction():Call<List<Book>>

    @GET("bookComic")
    fun getComic():Call<List<Book>>

    @GET("bookEbook")
    fun getEbook():Call<List<Book>>
}