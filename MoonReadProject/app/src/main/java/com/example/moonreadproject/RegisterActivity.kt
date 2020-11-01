package com.example.moonreadproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
    fun addCustomer(v: View){
        if (usr_password.text.toString() == usr_conpassword.text.toString()){

        val serv: CustomerAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CustomerAPI::class.java)

        serv.insertCus(
            usr_name.text.toString(),
            usr_password.text.toString(),
            usr_tel.text.toString(),
        1).enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (response.isSuccessful()) {
                    Toast.makeText(applicationContext, "Successfully Inserted", Toast.LENGTH_LONG)
                        .show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    "Error onFailure " + t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        }
        else{
            Toast.makeText(applicationContext, "Password not sync", Toast.LENGTH_LONG).show()
        }
    }
}