package com.example.moonreadproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_admin_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminMain : AppCompatActivity() {

    var bookList = arrayListOf<Book>()
    val createClient = BookAPI.create()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.home->{
                return@OnNavigationItemSelectedListener true
            }
            R.id.mail->{
                val intent = Intent(this,MailActivity::class.java)
                startActivity(intent)

                return@OnNavigationItemSelectedListener true
            }

            else -> return@OnNavigationItemSelectedListener false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        admin_bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        recycler_view.adapter = EditDeleteBookAdpter(this.bookList,applicationContext)
        recycler_view.layoutManager = GridLayoutManager(this,2)
        recycler_view.addItemDecoration(
            DividerItemDecoration(recycler_view.context,
                DividerItemDecoration.VERTICAL))

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.insert ->{
                val intent = Intent(this,InsertActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.logout ->{
                customerID.setData(0)
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                return true
            }
            else ->  return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        callBookData() }

    fun clickSearch(v: View) {
        bookList.clear()
        createClient.searchbook("%" + edt_search.text.toString() + "%")
            .enqueue(object : Callback<List<Book>> {
                override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                    response.body()?.forEach{
                        bookList.add(Book(it.book_id,it.book_name,it.book_price,it.img_book,it.book_type,it.book_detail))
                    }
                    recycler_view.adapter= BookAdapter(bookList,applicationContext)
                }

            })
    }



    fun callBookData(){
        bookList.clear()
        createClient.retrieveBook()
            .enqueue(object : Callback<List<Book>> {
                override fun onResponse(
                    call: Call<List<Book>>,
                    response: Response<List<Book>>
                ) {
                    response.body()?.forEach {
                        bookList.add(Book(it.book_id, it.book_name, it.book_price, it.img_book,it.book_type,it.book_detail))
                    }
                    recycler_view.adapter = EditDeleteBookAdpter(bookList, applicationContext)
                }
                override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                    return t.printStackTrace()
                    Toast.makeText(applicationContext, "ERROR", Toast.LENGTH_LONG).show()
                }
            })


    }
}