package com.codingblocks.msitnotes

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

class CseContainer: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)



        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container,CseFragment())
                .commit()
    }
}