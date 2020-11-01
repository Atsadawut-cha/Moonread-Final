package com.example.moonreadproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var customerList = arrayListOf<Customer>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_register.setOnClickListener(){
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

    }


    fun login(v: View){
        customerList.clear()


        val api : CustomerAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CustomerAPI :: class.java)

        api.loginCus(
            edt_name.text.toString(),
            edt_password.text.toString()).enqueue(object : Callback<Login>{
            override fun onResponse(call: Call<Login>,response: Response<Login>){
                response.body()?.cus_username
                customerID.setData(response.body()?.cus_id.toString().toInt())

                if(response.body()?.type == 1){
                    val intent = Intent(applicationContext,MainCustomer::class.java)
                    intent.putExtra("customer_id",response.body()?.cus_id)
                    startActivity(intent)
                }
                else if(response.body()?.type == 2) {
                    val intent = Intent(applicationContext,AdminMain::class.java)
                    intent.putExtra("customer_id",response.body()?.cus_id)
                    startActivity(intent)
                }
                Toast.makeText(applicationContext,"Wellcome "+response.body()?.cus_username,Toast.LENGTH_LONG).show()

            }
            override fun onFailure(call: Call<Login>,t: Throwable){
                Toast.makeText(applicationContext,"Incorrect username or password !",Toast.LENGTH_LONG).show()
            }
        })

    }

}