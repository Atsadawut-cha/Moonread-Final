package com.example.moonreadproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_customer_mail.*
import kotlinx.android.synthetic.main.activity_main_customer.button_nav
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CustomerMailActivity : AppCompatActivity() {

    var orderList = arrayListOf<Order>()
    val createClient = OrderAPI.create()


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
        setContentView(R.layout.activity_customer_mail)

        mail_recycle.layoutManager=LinearLayoutManager(applicationContext)
        mail_recycle.addItemDecoration(DividerItemDecoration(mail_recycle.context,
            DividerItemDecoration.VERTICAL))


        button_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cus_option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.logout ->{
                customerID.setData(0)
                customerID.setOrderID(0)
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                return true
            }
            else ->  return super.onOptionsItemSelected(item)
        }

    }

    override fun onResume() {
        super.onResume()
        callHistrory()
    }

    fun callHistrory(){
        orderList.clear()
        createClient.showOrderHistory(
            customerID.id
        )
            .enqueue(object :Callback<List<Order>>{
                override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                    response.body()?.forEach {
                        orderList.add(Order(it.order_id,it.customer_id,it.total_price,it.order_date,it.address,it.status,it.pay,it.delivery))
                    }
                    mail_recycle.adapter = CusHistoryAdapter(orderList,applicationContext)

                    if(orderList.isEmpty()){
                        Toast.makeText(applicationContext,"ไม่พบประวัติการสั่งซื้อ",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                    return t.printStackTrace()
                    Toast.makeText(applicationContext,"ERROR2",Toast.LENGTH_LONG).show()
                }

            })
    }

}
