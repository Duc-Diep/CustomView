package com.ducdiep.excustomview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ducdiep.excustomview.R

class CustomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)
    }
}