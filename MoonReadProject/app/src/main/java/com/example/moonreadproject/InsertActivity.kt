package com.example.moonreadproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_insert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
    }

    fun addBook(v:View){
        val createClient = BookAPI.create()
        var selectedId: Int = radioGroup.checkedRadioButtonId
        var radioButton: RadioButton? = findViewById(selectedId)
        createClient.insertBook(
            edt_name.text.toString(),
            edt_price.text.toString().toInt(),
            edt_img.text.toString(),
            radioButton?.text.toString(),
            edt_detail.text.toString()
        )
            .enqueue(object :Callback<Book>{

                override fun onFailure(call: Call<Book>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error onFailure", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Book>, response: Response<Book>) {
                    if(response.isSuccessful()){
                        Toast.makeText(applicationContext,"Successfully Inserted",Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
                    }
                }
            })

    }
    fun resetBook(v:View){
        edt_name.text.clear()
        edt_price.text.clear()
        edt_img.text.clear()
        radioGroup.clearCheck()
    }
}