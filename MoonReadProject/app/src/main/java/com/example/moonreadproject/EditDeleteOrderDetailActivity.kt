package com.example.moonreadproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edit_delete_order_detail.*
import kotlinx.android.synthetic.main.activity_edit_delete_order_detail.tv_number
import kotlinx.android.synthetic.main.activity_more_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDeleteOrderDetailActivity : AppCompatActivity() {

    val cartClient =CartAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_delete_order_detail)

        val mDetailID = intent.getStringExtra("mDetailID")
        val mOrderID = intent.getStringExtra("mOrder_id")
        val mBookName = intent.getStringExtra("mBookName")
        val mBookPrice = intent.getStringExtra("mBookPrice")
        val mQty = intent.getStringExtra("mQty")
        val mBookID = intent.getStringExtra("mBookID")

        ordDetailID.text = mDetailID
        ordID.text = mOrderID
        TXBookName.text = mBookName
        TXBookPrice.text = mBookPrice
        BookID.text = mBookID
        tv_number.text = mQty


    }

    fun updateOrderDetail(v:View){
        var up_P :Int = TXBookPrice.text.toString().toInt()*tv_number.text.toString().toInt()

        cartClient.updateOrderDetail(
            ordID.text.toString().toInt(),
            ordDetailID.text.toString().toInt(),
            tv_number.text.toString().toInt(),
            up_P
        ).enqueue(object  :Callback<Cart>{
            override fun onResponse(call: Call<Cart>, response: Response<Cart>) {
                if (response.isSuccessful) {
                    Toast.makeText( applicationContext, "Seccessfully Updated", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, " Update Failure", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<Cart>, t: Throwable) {
                Toast.makeText(applicationContext," Error Update orderDetail" + t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    fun deleteOrderDetail(v:View){
        val myBuilder = AlertDialog.Builder(this)
        myBuilder.apply {
            setTitle("เตือน !")
            setMessage("คุณต้องการที่จะลบสินค้านี้หรือไม่ ?")
            setNegativeButton("ตกลง"){ dialog , which->
                cartClient.deleteOrderDetail(
                    ordID.text.toString().toInt(),
                    BookID.text.toString().toInt()
                )
                    .enqueue(object :Callback<Cart>{
                        override fun onResponse(call: Call<Cart>, response: Response<Cart>) {
                            if(response.isSuccessful){
                                Toast.makeText(applicationContext,"Successfully Deleted", Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(applicationContext,"Delete Failure",Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<Cart>, t: Throwable) {
                            Toast.makeText(applicationContext," Error Delete orderDetail" + t.message, Toast.LENGTH_LONG).show()
                        }
                    })
                finish()
            }
            setPositiveButton("ยกเลิก"){dialog, which ->  dialog.cancel()}
            show()
        }
    }


    fun negQTY(v: View){
        if (tv_number.text.toString().toInt() >1){
            ChangeNum(tv_number.text.toString().toInt() - 1)
        }
    }
    fun plusQTY(v: View){
        ChangeNum(tv_number.text.toString().toInt() + 1)
    }
    private fun ChangeNum(number:Int){
        tv_number.text = "$number"
    }


}