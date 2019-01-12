package com.codingblocks.msitnotes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_view_pager.*
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager




class viewPager : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        val w = window // in Activity's onCreate() for instance
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val toolbar = findViewById(R.id.toolbar) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbar)
        if (getSupportActionBar() != null) getSupportActionBar()!!.setTitle("Share Space")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val s=intent.getIntExtra("syllabus",0)

        val fragmentAdapter = FragmentAdapter(supportFragmentManager, this,s)

        viewpager.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewpager)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}
