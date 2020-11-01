package com.example.moonreadproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main_customer.view.*


class FragmentFiction : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var view = inflater.inflate(R.layout.fragment_fiction,container,false)
//        view.btnFiction.setOnClickListener(){
//            var fragment = FragmentFiction()
//            replaceFragment(fragment)
//        }
//        view.btnComic.setOnClickListener(){
//            var fragment = FragmentComic()
//            replaceFragment(fragment)
//        }
//        view.btnEbook.setOnClickListener(){
//            var fragment = FragmentEbook()
//            replaceFragment(fragment)
//        }
        return view
    }

//    fun replaceFragment(someFragment: Fragment){
//        var transection = fragmentManager!!.beginTransaction()
//        transection.replace(R.id.recycler_view_top,someFragment)
//        transection.addToBackStack(null)
//        transection.commit()
//    }
}