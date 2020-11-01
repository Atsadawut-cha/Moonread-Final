package com.example.moonreadproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_more_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoreInfo : AppCompatActivity() {
    val cartClient = CartAPI.create()
    val orderCLient = OrderAPI.create()


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.home->{
                val intent = Intent(this,MainCustomer::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.cart->{
                val intent = Intent(this,CartActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.mail->{

                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)

        var customer_id = intent?.getStringExtra("customer_id")
        var book_id = intent?.getStringExtra("book_id")
        var book_name = intent?.getStringExtra("book_name")
        var img_book = intent?.getStringExtra("img_book")
        var book_price = intent?.getStringExtra("book_price")
        var book_detail = intent?.getStringExtra("book_detail")
        var book_type = intent?.getStringExtra("book_type")

        more_book_id.setText(book_id)
        //more_img_info.setImageURI(img_book)
        //more_cus_id.setText(customerID.id)
        Glide.with(this).load(img_book).into(more_img_info)
        more_book_name.setText(book_name)
        more_book_detail.setText(book_detail)
        more_book_price.setText(book_price)

        //Toast.makeText(applicationContext,"Moreinfo "+customerID.id,Toast.LENGTH_LONG).show()

        val actionBar = supportActionBar
        actionBar!!.title = "More Information"

        button_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun checkOrderID(v:View){
        /*val serv : OrderAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OrderAPI::class.java)*/
        //Toast.makeText(applicationContext,"test", Toast.LENGTH_SHORT).show()
        orderCLient.showOrderID(customerID.id)
            .enqueue(object :Callback<Order>{
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    //Toast.makeText(applicationContext,"test2", Toast.LENGTH_SHORT).show()
                    if(response.body()?.status.toString() == "order"){
                        //println(":::::::::::::::::::::::::test:::::::::::::::::::::::::::::::")
                        customerID.setOrderID(response.body()?.order_id.toString().toInt())
                        Toast.makeText(applicationContext,customerID.order_id.toString(), Toast.LENGTH_SHORT).show()
                        insertOrderDetail(v)
                    }
                    if(response.body()?.status.toString() == "ordered"){
                        Toast.makeText(applicationContext,"You already order !!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                createOrder(v)
            }

        })

    }

    fun createOrder(v:View){
        val serv : OrderAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OrderAPI::class.java)

        serv.insertOrder(
            customerID.id.toString().toInt(),
            0,
            "",
            "",
            "order",
            "",
            ""
                    ).enqueue(object : Callback<Order> {
                            // FOR PHP Callback<List<mapMovieDB>>
                            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                                if (response.isSuccessful) {
                                    customerID.setOrderID(response.body()?.order_id.toString().toInt())
                                    //customerID.setOrderID(response.body()?.order_id.toString().toInt())
                                    //Toast.makeText(applicationContext,customerID.order_id,Toast.LENGTH_SHORT).show()
                                    Toast.makeText(applicationContext,"Successfully Create Order" , Toast.LENGTH_SHORT).show()
                                    checkOrderID(v)

                                }else{
                                    Toast.makeText(applicationContext,"Fail create order ", Toast.LENGTH_SHORT).show()
                                }
                            }
                            override fun onFailure(call: Call<Order>, t: Throwable) {
                                Toast.makeText(applicationContext," Error create order" + t.message, Toast.LENGTH_LONG).show()
                            }
                        })

            }

    fun insertOrderDetail(v:View){
        var book_p :Int = tv_number.text.toString().toInt() * more_book_price.text.toString().toInt()
        cartClient.insertedDetail(
            customerID.order_id,
            more_book_id.text.toString().toInt(),
            tv_number.text.toString().toInt(),
            book_p
        )
            .enqueue(object :Callback<Cart>{
                override fun onResponse(call: Call<Cart>, response: Response<Cart>) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext,"Successfully Inserted orderDetail", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MoreInfo,CartActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(applicationContext,"Fail Inserted orderDetail", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Cart>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error Insert OrderDetail " + t.message, Toast.LENGTH_LONG).show()
                }

            })

    }
    fun negQTY(v: View){
        if (tv_number.text.toString().toInt() >1){
            ChangeNum(tv_number.text.toString().toInt() - 1)
        }
    }
    fun plusQTY(v:View){
        ChangeNum(tv_number.text.toString().toInt() + 1)
    }
    fun ChangeNum(number:Int){
        tv_number.text = "$number"
    }


}









