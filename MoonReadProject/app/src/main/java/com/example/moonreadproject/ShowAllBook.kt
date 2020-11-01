package com.example.moonreadproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_show_all_book.*

class ShowAllBook : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_book)

        val actionBar = supportActionBar
        actionBar!!.title = "More Information"

//        more.setOnClickListener(){
//            val intent = Intent(this,MoreInfo::class.java)
//            startActivity(intent)
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

//    fun clickMore(v:View){
//        val intent1 = Intent(this,MoreInfo::class.java)
//        startActivity(intent1)
//    }
}