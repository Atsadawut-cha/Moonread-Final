package com.example.moonreadproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_cart.button_nav
import kotlinx.android.synthetic.main.activity_confirm.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmActivity : AppCompatActivity() {

    var productList = arrayListOf<Cart>()
    val cartClient = CartAPI.create()
    val orderClient = OrderAPI.create()
    var sum :Int = 0
    var num : Int = 0
    var delivery :String = ""

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.home->{
                val intent = Intent(this,MainCustomer::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.cart->{
                val intent =Intent(this,CartActivity::class.java)
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
        setContentView(R.layout.activity_confirm)

        recycler_view_confirm.layoutManager= LinearLayoutManager(applicationContext)
        recycler_view_confirm.addItemDecoration(
            DividerItemDecoration(recycler_view_confirm.context, DividerItemDecoration.VERTICAL)
        )

        button_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onResume() {
        super.onResume()
        callProduct()
    }

    fun callProduct(){
        productList.clear()
        cartClient.showDetail(
            customerID.order_id
        ).enqueue(object : Callback<List<Cart>> {
            override fun onResponse(call: Call<List<Cart>>, response: Response<List<Cart>>) {
                response.body()?.forEach{
                    productList.add(Cart(it.cart_orderDetail_id,it.cart_order_id,it.cart_book_id,it.cart_book_name,it.cart_book_price,it.cart_qty,it.cart_price))
                    sum += it.cart_price
                    num = sum
                }
                recycler_view_confirm.adapter = ConfirmAdapter(productList,applicationContext)
                total.text = num.toString()
            }

            override fun onFailure(call: Call<List<Cart>>, t: Throwable) {
                return t.printStackTrace()
                Toast.makeText(applicationContext,"ERROR callProduct", Toast.LENGTH_LONG).show()
            }

        })
    }

    fun submitOrder(v: View){
        var selecTedID :Int = Payment_radioGroup.checkedRadioButtonId
        var paymentRadio: RadioButton = findViewById(selecTedID)
        orderClient.updateOrder(
            customerID.order_id,
            total.text.toString().toInt(),
            edt_Date.text.toString(),
            edt_Address.text.toString(),
            "ordered",
            paymentRadio?.text.toString(),
            delivery
        ).enqueue(object :Callback<Order>{
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    Toast.makeText( applicationContext, "Successfully Updated", Toast.LENGTH_LONG).show()
                    newOrder()
                } else {
                    Toast.makeText(applicationContext, " Update Failure", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Order>, t: Throwable) {
                Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
            }

        })
    }

    fun newOrder(){
        orderClient.insertOrder(
            customerID.id.toString().toInt(),
            0,
            "",
            "",
            "order",
            "",
            ""
        ).enqueue(object :Callback<Order>{
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                Toast.makeText( applicationContext, "Successfully New Order", Toast.LENGTH_LONG).show()
                finish()
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                Toast.makeText(applicationContext,"Error New Order ",Toast.LENGTH_LONG).show()
            }

        })
    }

    fun regis(v:View){
        num = sum
        num += 30
        delivery = "ลงทะเบียน"
        change(num)
    }

    fun ems(v:View){
        num = sum
        num += 50
        change(num)
        delivery = "EMS"
    }

    private fun change(number:Int){
        total.text = number.toString()
    }

}