package com.example.moonreadproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_admin_edit_status.*
import kotlinx.android.synthetic.main.activity_edit_delete.*
import kotlinx.android.synthetic.main.update_status_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminEditStatusActivity : AppCompatActivity() {

    var productList = arrayListOf<Cart>()
    val cartClient = CartAPI.create()
    val orderClient = OrderAPI.create()
    var order_id: Int = 0
    var cus_id: Int = 0
    var total: Int = 0
    var date: String = ""
    var adess: String = ""
    var Status: String = ""
    var pay: String = ""
    var deliver: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_edit_status)

        val aOrderID = intent.getStringExtra("aOrderID")
        val aCustomerID = intent.getStringExtra("aCustomerID")
        val aTotal = intent.getStringExtra("aTotal")
        val aDate = intent.getStringExtra("aDate")
        val aAddress = intent.getStringExtra("aAddress")
        val aStatus = intent.getStringExtra("aStatus")
        val aPay = intent.getStringExtra("aPay")
        val aDel = intent.getStringExtra("aDel")

        cus_id = aCustomerID.toString().toInt()
        order_id = aOrderID.toString().toInt()
        total = aTotal.toString().toInt()
        date = aDate.toString()
        adess = aAddress.toString()
        Status = aStatus.toString()
        pay = aPay.toString()
        deliver = aDel.toString()

        ad_order_id.text = aOrderID.toString()
        ad_address.text = aAddress.toString()
        ad_date.text = aDate.toString()
        ad_deli.text = aDel.toString()
        ad_payment.text = aPay.toString()
        ad_status.text = aStatus.toString()
        ad_total.text = aTotal.toString()

        ad_recycle.layoutManager = LinearLayoutManager(applicationContext)
        ad_recycle.addItemDecoration(
            DividerItemDecoration(
                ad_recycle.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onResume() {
        super.onResume()
        callAdOrderData()
    }

    fun callAdOrderData() {
        productList.clear()
        cartClient.showDetail(
            ad_order_id.text.toString().toInt()
        ).enqueue(object : Callback<List<Cart>> {
            override fun onResponse(call: Call<List<Cart>>, response: Response<List<Cart>>) {
                response.body()?.forEach {
                    productList.add(
                        Cart(
                            it.cart_orderDetail_id,
                            it.cart_order_id,
                            it.cart_book_id,
                            it.cart_book_name,
                            it.cart_book_price,
                            it.cart_qty,
                            it.cart_price
                        )
                    )
                }
                ad_recycle.adapter = AdminORDetailAdapter(productList, applicationContext)
            }

            override fun onFailure(call: Call<List<Cart>>, t: Throwable) {
                return t.printStackTrace()
                Toast.makeText(applicationContext, "ERROR Call Admin Detail", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }

    fun changeStaus(v: View) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.update_status_layout, null)
        val myBuilder = AlertDialog.Builder(this)
        myBuilder.setView(mDialogView)

        val mAlertDialog = myBuilder.show()
        mAlertDialog.btnUpdate.setOnClickListener() {
            var selectedAD: Int = mAlertDialog.changeStatus_radio.checkedRadioButtonId
            var AddRadio: RadioButton? = mAlertDialog.findViewById(selectedAD)
            orderClient.updateOrder(
                order_id,
                total,
                date,
                adess,
                AddRadio?.text.toString(),
                pay,
                deliver
            ).enqueue(object : Callback<Order> {
                override fun onResponse(call: Call<Order>, response: Response<Order>) {
                    if (response.isSuccessful) {
                        Toast.makeText( applicationContext, "Successfully Updated", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, " Update Failure", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Order>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
                }
            })
            mAlertDialog.dismiss()

        }

        mAlertDialog.btnCancel.setOnClickListener(){
            mAlertDialog.dismiss()
        }


    }
}