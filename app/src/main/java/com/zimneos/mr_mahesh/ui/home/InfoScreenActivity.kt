package com.zimneos.mr_mahesh.ui.home

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.zimneos.mr_mahesh.R
import com.google.firebase.database.*
import com.zimneos.mr_mahesh.common.MotionOnClickListener
import kotlinx.android.synthetic.main.layout_info_screen.*
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.play_button_layout.*


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
        posterData = extras?.getString("poster").toString()
        titleData = extras?.getString("title").toString()
        info_poster.transitionName = extras?.getString("poster_transition")
        info_title.text = titleData
        Glide.with(info_poster).load(posterData).error(R.color.light_dark).into(info_poster)
        info_title.isSelected = true
        getMovieSummary()
        setMovieRating()
        playButtonPressed(true)
        play.setOnTouchListener(MotionOnClickListener(this.applicationContext) {
            playButtonPressed(false)
        })
    }

    private fun setMovieRating() {
        val databaseReference = FirebaseDatabase.getInstance().reference
        val query = databaseReference.child("movielist")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var count = 1
                while (count <= dataSnapshot.childrenCount.toInt() + 1) {
                    val movieNameData = dataSnapshot.child(count.toString()).child("title").value.toString()
                    if (movieNameData == titleData) {
                        val ratingImg = dataSnapshot.child(count.toString()).child("rating").value.toString()
                        Glide.with(rating_info).load(ratingImg).error(R.color.light_dark).into(rating_info)
                        break
                    }
                    count++
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }


    private fun playButtonPressed(isOnCreateFetch: Boolean) {
        var movieUrl: Uri?
        val databaseReference = FirebaseDatabase.getInstance().reference
        val query = databaseReference.child("movielist")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var count = 1
                while (count <= dataSnapshot.childrenCount.toInt() + 1) {
                    val movieNameData = dataSnapshot.child(count.toString()).child("title").value.toString()
                    if (movieNameData == titleData) {
                        movieUrl = dataSnapshot.child(count.toString()).child("link").value.toString().toUri()
                        when (movieUrl) {
                            "null".toUri() -> {
                                play.visibility = View.INVISIBLE
                            }
                            else -> {
                                when {
                                    !isOnCreateFetch -> {
                                        play.visibility = View.VISIBLE
                                        val intent = Intent(Intent.ACTION_VIEW, movieUrl)
                                        startActivity(intent)
                                    }
                                }
                            }
                        }
                        break
                    }
                    count++
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
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
                        summary.text = dataSnapshot.child(count.toString()).child("summary").value.toString()
                        break
                    }
                    count++
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun animation(imageView: View, animator: Int) {
        val set = AnimatorInflater.loadAnimator(applicationContext, animator) as AnimatorSet
        set.setTarget(imageView)
        set.start()
    }
}
