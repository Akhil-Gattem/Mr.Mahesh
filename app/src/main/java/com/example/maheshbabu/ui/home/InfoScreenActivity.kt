package com.example.maheshbabu.ui.home

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.maheshbabu.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.layout_info_screen.*


class InfoScreenActivity : AppCompatActivity() {
    private var titleData = ""
    private var posterData = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_info_screen)
        info_back_btn.setOnClickListener {
            animation(info_back_btn, R.animator.back_btn_animator)
            onBackPressed()
        }
        val extras = intent.extras
        if (extras != null) {
            posterData = intent.extras?.getString("poster").toString()
            titleData = intent.extras?.getString("title").toString()
        }
        val imageTransitionName = extras?.getString("poster_transition")
        info_poster.transitionName = imageTransitionName
        info_title.text = titleData
        Glide.with(info_poster).load(posterData).error(R.color.light_dark).into(info_poster)
        info_title.isSelected = true
        getMovieSummary()

    }

    private fun animation(imageView: View, animator: Int) {
        val set = AnimatorInflater.loadAnimator(applicationContext, animator) as AnimatorSet
        set.setTarget(imageView)
        set.start()
    }

    private fun getMovieSummary() {
        val databaseReference = FirebaseDatabase.getInstance().reference
        val query = databaseReference.child("movielist")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var count = 1
                while (count <= dataSnapshot.childrenCount.toInt() + 1) {
                    val movieNameData = dataSnapshot.child(count.toString()).child("title").value.toString()
                    if (movieNameData == titleData) {
                        val summaryText = dataSnapshot.child(count.toString()).child("summary").value.toString()
                        summary.text = summaryText
                        break
                    }
                    count++
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

}
