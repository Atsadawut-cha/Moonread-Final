package com.example.moonreadproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart.button_nav
import kotlinx.android.synthetic.main.activity_more_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartActivity : AppCompatActivity() {

    var productList = arrayListOf<Cart>()
    val cartClient = CartAPI.create()
    val orderClient = OrderAPI.create()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.home->{
                val intent = Intent(this,MainCustomer::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.cart->{
                return@OnNavigationItemSelectedListener true
            }
            R.id.mail->{
                val intent = Intent(this,CustomerMailActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recycler_view_cart.layoutManager= LinearLayoutManager(applicationContext)
        recycler_view_cart.addItemDecoration(
            DividerItemDecoration(recycler_view_cart.context, DividerItemDecoration.VERTICAL))

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
        getOrderID()
    }


    fun getOrderID(){
        orderClient.showOrderID(
            customerID.id
        ).enqueue(object :Callback<Order>{
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    customerID.setOrderID(response.body()?.order_id.toString().toInt())
                    Toast.makeText(applicationContext,"OrderID "+customerID.order_id.toString(), Toast.LENGTH_SHORT).show()
                    callProduct()
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Toast.makeText(applicationContext,"NO PRODUCT IN CART !", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun callProduct(){
        productList.clear()
        cartClient.showDetail(
            customerID.order_id
        ).enqueue(object :Callback<List<Cart>>{
            override fun onResponse(call: Call<List<Cart>>, response: Response<List<Cart>>) {
                response.body()?.forEach{
                    productList.add(Cart(it.cart_orderDetail_id,it.cart_order_id,it.cart_book_id,it.cart_book_name,it.cart_book_price,it.cart_qty,it.cart_price))
                }
                recycler_view_cart.adapter = CartAdapter(productList,applicationContext)
            }

            override fun onFailure(call: Call<List<Cart>>, t: Throwable) {
                return t.printStackTrace()
                Toast.makeText(applicationContext,"ERROR callProduct",Toast.LENGTH_LONG).show()
            }

        })
    }


    fun confirmOrder(v:View){
        if(productList.isEmpty()){
            Toast.makeText(applicationContext,"please Add Product fist",Toast.LENGTH_LONG).show()
        }else{
        val intent = Intent(this,ConfirmActivity::class.java)
        startActivity(intent)
        }
    }

}