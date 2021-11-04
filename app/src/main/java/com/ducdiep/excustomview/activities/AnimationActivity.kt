package com.ducdiep.excustomview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ducdiep.excustomview.R
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var ani:Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        btn_shake.setOnClickListener(this)
        btn_translate.setOnClickListener(this)
        btn_rotate.setOnClickListener(this)
        btn_scale.setOnClickListener(this)
        btn_blink.setOnClickListener(this)
        btn_fade.setOnClickListener(this)
        btn_bounce.setOnClickListener(this)
        btn_move.setOnClickListener(this)
        btn_sequential.setOnClickListener(this)
        btn_cancel_animation.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            btn_shake -> {
                ani = AnimationUtils.loadAnimation(this,R.anim.shake_animation)
                img_test.startAnimation(ani)
            }
            btn_scale -> {
                ani = AnimationUtils.loadAnimation(this,R.anim.scale_animation)
                img_test.startAnimation(ani)
            }
            btn_rotate -> {
                ani = AnimationUtils.loadAnimation(this,R.anim.rotate_animation)
                img_test.startAnimation(ani)
            }
            btn_translate -> {
                ani = AnimationUtils.loadAnimation(this,R.anim.slide_in_right)
                img_test.startAnimation(ani)
            }
            btn_blink -> {
                ani = AnimationUtils.loadAnimation(this,R.anim.blink_animation)
                img_test.startAnimation(ani)
            }
            btn_fade ->{
                ani = AnimationUtils.loadAnimation(this,R.anim.fade_animation)
                img_test.startAnimation(ani)
            }
            btn_bounce ->{
                ani = AnimationUtils.loadAnimation(this,R.anim.bounce_animation)
                img_test.startAnimation(ani)
            }
            btn_move -> {
                ani = AnimationUtils.loadAnimation(this,R.anim.move_animation)
                img_test.startAnimation(ani)
            }
            btn_sequential -> {
                ani = AnimationUtils.loadAnimation(this,R.anim.sequential_animation)
                img_test.startAnimation(ani)
            }
            btn_cancel_animation ->{
                img_test.clearAnimation()
            }
        }
    }
}