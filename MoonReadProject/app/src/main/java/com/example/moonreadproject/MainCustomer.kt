package com.example.moonreadproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main_customer.*
import kotlinx.android.synthetic.main.activity_main_customer.button_nav
import kotlinx.android.synthetic.main.activity_main_customer.view.*
import kotlinx.android.synthetic.main.activity_more_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainCustomer : AppCompatActivity() {


    val bookList = arrayListOf<Book>()
    val createClient = BookAPI.create()


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.home->{
                val intent = Intent(this,MainCustomer::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.cart->{
                val intent = Intent(this,CartActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.mail->{
                val intent = Intent(this,CustomerMailActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_customer)

        recycler_view_top.layoutManager = GridLayoutManager(this,2)
        recycler_view_top.addItemDecoration(
            DividerItemDecoration(recycler_view_top.context,
                DividerItemDecoration.VERTICAL)
        )

//        supportFragmentManager.beginTransaction().add(
//            R.id.recycler_view_top,FragmentFiction()
//        ).commit()
        val customer_id = intent.getStringExtra("customer_id")
        //Toast.makeText(applicationContext,"Cus Id is : "+customer_id,Toast.LENGTH_LONG).show()

        val actionBar = supportActionBar
        actionBar!!.title = "Home"

        button_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cus_option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.logout ->{
                customerID.setData(0)
                customerID.setOrderID(0)
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                return true
            }
            else ->  return super.onOptionsItemSelected(item)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        callBookdata()
    }

    fun callBookdata(){
        bookList.clear()
        val serv: BookAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookAPI::class.java)

        serv.retrieveBook()
            .enqueue(object : Callback<List<Book>>{
                override fun onResponse(
                    call: Call<List<Book>>,
                    response: Response<List<Book>>
                ) {
                    response.body()?.forEach{
                        bookList.add(Book(it.book_id,it.book_name,it.book_price,it.img_book,it.book_type,it.book_detail))
                    }
                    recycler_view_top.adapter= BookAdapter(bookList,applicationContext)
                }

                override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
    }

    fun clickSearch(v:View) {
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
                    recycler_view_top.adapter= BookAdapter(bookList,applicationContext)
                }

            })
    }

    fun searchFiction(v:View){
        bookList.clear()
        createClient.getFiction().enqueue(object :Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                response.body()?.forEach{
                    bookList.add(Book(it.book_id,it.book_name,it.book_price,it.img_book,it.book_type,it.book_detail))
                }
                recycler_view_top.adapter= BookAdapter(bookList,applicationContext)
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Toast.makeText(applicationContext,"Can't search Fiction",Toast.LENGTH_LONG).show()
            }

        })
    }

    fun searchComic(v: View){
        bookList.clear()
        createClient.getComic().enqueue(object :Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                response.body()?.forEach{
                    bookList.add(Book(it.book_id,it.book_name,it.book_price,it.img_book,it.book_type,it.book_detail))
                }
                recycler_view_top.adapter= BookAdapter(bookList,applicationContext)
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Toast.makeText(applicationContext,"Can't search Comic",Toast.LENGTH_LONG).show()
            }

        })
    }

    fun Ebooksearch(v:View){
        bookList.clear()
        createClient.getEbook().enqueue(object :Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                response.body()?.forEach{
                    bookList.add(Book(it.book_id,it.book_name,it.book_price,it.img_book,it.book_type,it.book_detail))
                }
                recycler_view_top.adapter= BookAdapter(bookList,applicationContext)
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Toast.makeText(applicationContext,"Can't search Ebook",Toast.LENGTH_LONG).show()
            }

        })
    }

}