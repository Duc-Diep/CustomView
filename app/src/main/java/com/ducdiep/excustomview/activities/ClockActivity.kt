package com.ducdiep.excustomview.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ducdiep.excustomview.R
import com.ducdiep.excustomview.viewmodels.ClockViewModel
import kotlinx.android.synthetic.main.activity_clock.*
import kotlinx.coroutines.*
import java.util.*

class ClockActivity : AppCompatActivity() {
    lateinit var viewModel:ClockViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock)
        viewModel = ViewModelProvider(this).get(ClockViewModel::class.java)
        my_clock.onTimeChange {
            tv_hours.text = it.get(Calendar.HOUR_OF_DAY).toString()
            tv_minutes.text = it.get(Calendar.MINUTE).toString()
            tv_seconds.text = it.get(Calendar.SECOND).toString()
        }
        time_picker.setIs24HourView(true)
        time_picker.setOnTimeChangedListener { timePicker, hours, minutes ->
            my_clock.isStop = true
            tv_hours.text = hours.toString()
            tv_minutes.text = minutes.toString()
            tv_seconds.text = "0"
        }

        btn_set_time.setOnClickListener {
            var hours = tv_hours.text.toString()
            var minutes = tv_minutes.text.toString()
            var second = tv_seconds.text.toString()
            if (my_clock.isStop&&viewModel.validateData(hours, minutes, second)){
                my_clock.setTime(hours.toInt(),minutes.toInt(),second.toInt())
                my_clock.isStop = false
            }else{
                Toast.makeText(this, "Dữ liệu không hợp lệ để thực hiện thao tác", Toast.LENGTH_SHORT).show()
            }
        }
    }
}