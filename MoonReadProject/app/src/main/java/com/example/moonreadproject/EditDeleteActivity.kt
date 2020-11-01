package com.example.moonreadproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edit_delete.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDeleteActivity : AppCompatActivity() {

    val createClient = BookAPI.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_delete)

        val mID = intent.getStringExtra("mId")
        val mName = intent.getStringExtra("mName")
        val mPrice = intent.getStringExtra("mPrice")
        val mImg = intent.getStringExtra("mImg")
        val mType = intent.getStringExtra("mType")
        val mDetail = intent.getStringExtra("mDetail")

        edtId.setText(mID)
        edtId.isEnabled = false
        edtName.setText(mName)
        edtPrice.setText(mPrice)
        edtImg.setText(mImg)
        edtDetail.setText(mDetail)

        when (mType) {
            "Fiction" -> {
                fiction2.isChecked = true
            }
            "Comic" -> {
                comic2.isChecked = true
            }
            "EBook" -> {
                ebook2.isChecked = true
            }
            else -> {
                fiction2.isChecked = true
            }
        }
    }
    fun saveBook(v: View) {
        var selectedId2: Int = radioGroup2.checkedRadioButtonId
        var radioButton2: RadioButton? = findViewById(selectedId2)
        createClient.updateBook(
            edtId.text.toString().toInt(),
            edtName.text.toString(),
            edtPrice.text.toString().toInt(),
            edtImg.text.toString(),
            radioButton2?.text.toString(),
            edtDetail.text.toString()
        )
            .enqueue(object :Callback<Book>{
                override fun onResponse(call: Call<Book>, response: Response<Book>) {
                    if (response.isSuccessful) {
                        Toast.makeText( applicationContext, "Successfully Updated", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(applicationContext, " Update Failure", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Book>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
                }

            })

    }

    fun deleteBook(v:View) {
        val myBuilder = AlertDialog.Builder(this)
        myBuilder.apply {
            setTitle("Warning")
            setMessage("Do you want to delete the book?")
            setNegativeButton("Yes") { dialog, which ->
                createClient.deleteBook(
                    edtId.text.toString().toInt()
                )
                    .enqueue(object :Callback<Book>{
                        override fun onResponse(call: Call<Book>, response: Response<Book>) {
                            if(response.isSuccessful){
                                Toast.makeText(applicationContext,"Successfully Deleted", Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(applicationContext,"Delete Failure",Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<Book>, t: Throwable) {
                            Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
                        }
                    })
                finish()
            }
            setPositiveButton("No"){dialog, which ->  dialog.cancel()}
            show()
        }
    }
}