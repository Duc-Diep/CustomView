package com.ducdiep.excustomview.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ducdiep.excustomview.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClick()
    }

    private fun setClick() {
        btn_animation.setOnClickListener {
            startActivity(Intent(this,AnimationActivity::class.java))
        }
        btn_clock.setOnClickListener {
            startActivity(Intent(this,ClockActivity::class.java))
        }
        btn_custome_view.setOnClickListener {
            startActivity(Intent(this,CustomActivity::class.java))
        }
        btn_lock_pattern.setOnClickListener {
            startActivity(Intent(this,LockPatternActivity::class.java))
        }
    }
}