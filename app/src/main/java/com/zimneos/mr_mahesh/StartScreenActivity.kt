package com.zimneos.mr_mahesh

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.zimneos.mr_mahesh.R
import kotlinx.android.synthetic.main.layout_start_screen.*

@Suppress("DEPRECATION")
class StartScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.start_page_status_color)
        setContentView(R.layout.layout_start_screen)
        start_page_bg_back.post {
            animation(start_page_bg_top, R.animator.start_screen_animator)
            animation(start_page_bg_back, R.animator.start_screen_animator_two)
        }
    }

    private fun animation(imageView: View, animator: Int) {
        val set = AnimatorInflater.loadAnimator(applicationContext, animator) as AnimatorSet
        set.setTarget(imageView)
        set.start()
    }

    override fun onStart() {
        super.onStart()
        intent = Intent(this, MainScreenActivity::class.java)
        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 3000)
    }
}
