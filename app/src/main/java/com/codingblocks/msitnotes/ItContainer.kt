package com.codingblocks.msitnotes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class ItContainer: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container,ItFragment())
                .commit()
    }
}