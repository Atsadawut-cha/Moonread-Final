package com.example.moonreadproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_cus_order_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CusOrderDetailActivity : AppCompatActivity() {

    var productList = arrayListOf<Cart>()
    val cartClient = CartAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cus_order_detail)

        val mOrder_id = intent.getStringExtra("mOrder_id")
        val mCus_id = intent.getStringExtra("mCus_id")
        val mTotal = intent.getStringExtra("mTotal")
        val mOrder_date = intent.getStringExtra("mOrder_date")
        val mAddress = intent.getStringExtra("mAddress")
        val mStatus = intent.getStringExtra("mStatus")
        val mPay = intent.getStringExtra("mPay")
        val mDelivery = intent.getStringExtra("mDelivery")

        tv_hisOrderID.text = mOrder_id
        his_status.text = mStatus
        his_address.text = mAddress
        his_payment.text = mPay
        his_deli.text = mDelivery
        his_total.text = mTotal
        his_date.text = mOrder_date

        history_recycle.layoutManager = LinearLayoutManager(applicationContext)
        history_recycle.addItemDecoration(DividerItemDecoration(history_recycle.context,
        DividerItemDecoration.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
        callHistoryDetail()
    }

    fun callHistoryDetail(){
        productList.clear()
        cartClient.showDetail(
            tv_hisOrderID.text.toString().toInt()
        ).enqueue(object :Callback<List<Cart>>{
            override fun onResponse(call: Call<List<Cart>>, response: Response<List<Cart>>) {
                response.body()?.forEach {
                    productList.add(Cart(it.cart_orderDetail_id,it.cart_order_id,it.cart_book_id,it.cart_book_name,it.cart_book_price,it.cart_qty,it.cart_price))
                }
                history_recycle.adapter = CusHisDetailAdapter(productList,applicationContext)
            }

            override fun onFailure(call: Call<List<Cart>>, t: Throwable) {
                return t.printStackTrace()
                Toast.makeText(applicationContext,"ERROR Call history Detail", Toast.LENGTH_LONG).show()
            }

        })
    }

}