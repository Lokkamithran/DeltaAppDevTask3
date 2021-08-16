package com.example.pawsome.activities

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pawsome.*
import com.example.pawsome.fragments.FirstFragment
import com.example.pawsome.fragments.UploadFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val homeFragment: Fragment = FirstFragment()
    private val uploadFragment: Fragment = UploadFragment()

    private fun setCurrentFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCurrentFragment(homeFragment)
        bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.upload -> setCurrentFragment(uploadFragment)
            }
            true
        }
    }
}