package com.example.maheshbabu.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.example.maheshbabu.R
import kotlinx.android.synthetic.main.layout_info_screen.*

class InfoScreenActivity : AppCompatActivity() {
    private var titleData = ""
    private var posterData = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_info_screen)


        info_back_btn.setOnClickListener {
            onBackPressed()
        }
        val extras = intent.extras
        if (extras != null) {
            posterData = intent.extras?.getString("poster").toString()
            titleData = intent.extras?.getString("title").toString()
        }
        Glide.with(info_poster).load(posterData).error(R.color.light_dark).into(info_poster)
        info_title.text = titleData
    }
}
