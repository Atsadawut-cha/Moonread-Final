package com.example.moonreadproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_mail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MailActivity : AppCompatActivity() {

    var orderList = arrayListOf<Order>()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.home->{
                val intent = Intent(this,AdminMain::class.java)
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
        setContentView(R.layout.activity_mail)

        admin_bottom_nav2.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        recycler_view2.adapter = MailAdapter(orderList, applicationContext)
        recycler_view2.layoutManager = LinearLayoutManager(applicationContext)
        recycler_view2.addItemDecoration(
            DividerItemDecoration(recycler_view2.context,
                DividerItemDecoration.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        callOrderData()

    }

    fun callOrderData(){
        orderList.clear()
        //Toast.makeText(applicationContext,"Wellcome test1 ",Toast.LENGTH_SHORT).show()
        val serv: OrderAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OrderAPI::class.java)
        serv.showOrder()
            .enqueue(object : Callback<List<Order>> {
                override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                    //Toast.makeText(applicationContext,"Wellcome test2 ",Toast.LENGTH_SHORT).show()
                    response.body()?.forEach{
                        orderList.add(Order(it.order_id,it.customer_id,it.total_price,it.order_date,it.address,it.status,it.pay,it.delivery))
                    }
                    recycler_view2.adapter = MailAdapter(orderList,applicationContext)
                }

                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    return t.printStackTrace()
                    Toast.makeText(applicationContext, "ERROR", Toast.LENGTH_LONG).show()
                }

            })
    }
}