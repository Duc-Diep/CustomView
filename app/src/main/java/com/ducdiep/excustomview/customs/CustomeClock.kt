package com.ducdiep.excustomview.customs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import kotlinx.coroutines.*
import java.util.*
import kotlin.math.cos
import kotlin.math.sin


class CustomeClock : View {
    // height, width of the clock's view
    private var mHeight = 0
    private var mWidth = 0

    //hours
    private val mClockHours = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)

    //spacing and padding of the clock-hands around the clock round
    private var mPadding = 0
    private var mNumeralSpacing = 0

    // truncation of the heights of the clock-hands,
//     hour clock-hand will be smaller comparetively to others
    private var mHandTruncation = 0
    private var mHourHandTruncation: Int = 0

    // attributes to calculate the locations of hour-points
    private var mRadius = 0
    private var mPaint: Paint? = null
    private var mRect: Rect = Rect()
    private var isInit // it will be true once the clock will be initialized.
            = false

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )

    var calendar:Calendar? = null
    lateinit var job:Job
    var isStop = false

    fun setTime(hour:Int,minute:Int,second:Int){
        calendar?.set(Calendar.HOUR_OF_DAY,hour)
        calendar?.set(Calendar.MINUTE,minute)
        calendar?.set(Calendar.SECOND,second)
    }
    fun initAttr(){
        if (!isInit) {
            mPaint = Paint()
            mHeight = height
            mWidth = width
            mPadding = mNumeralSpacing + 50
            var minAttr = mHeight.coerceAtMost(mWidth)
            mRadius = minAttr / 2 - mPadding
            //setup width clock hand
            mHandTruncation = minAttr / 20
            mHourHandTruncation = minAttr / 17
            if (calendar==null){
                calendar = Calendar.getInstance()
                var hour  = calendar!!.get(Calendar.HOUR_OF_DAY)
                var minutes  = calendar!!.get(Calendar.MINUTE)
                var second  = calendar!!.get(Calendar.SECOND)
                setTime(hour,minutes,second)//setup Calendar
            }
            isInit = true
        }
    }
    fun onTimeChange(onChanged: (calendar:Calendar)->Unit){
        job = CoroutineScope(Dispatchers.Default).launch {
            while (true){
                if (!isStop){
                    delay(1000)
                    calendar?.add(Calendar.SECOND,1)
                    Log.d("timee", "onTimeChange: ${calendar?.get(Calendar.SECOND)}")
                    withContext(Dispatchers.Main){
                        onChanged(calendar!!)
                    }
                }else{
                    delay(1000)
                    Log.d("timee", "onTimeChange: stop")
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        initAttr()
        canvas?.drawColor(Color.BLUE)
        //draw circle bound
        mPaint?.reset()
        mPaint?.color = Color.WHITE
        mPaint?.style = Paint.Style.STROKE
        mPaint?.strokeWidth = 4f
        mPaint?.isAntiAlias = true
        canvas?.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), (mRadius + mPadding - 10).toFloat(), mPaint!!)
        //circle center
        mPaint?.style = Paint.Style.FILL
        canvas?.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), 10f, mPaint!!)
        //draw number
        val fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics).toInt()
        mPaint!!.textSize = fontSize.toFloat()

        for (hour in mClockHours) {
            var text = hour.toString()
            mPaint?.getTextBounds(text, 0, text.length, mRect)

            //tim vi tri cua cac so
            var angle = Math.PI / 6 * (hour - 3)
            var x = mWidth / 2 + cos(angle) * mRadius - mRect.width() / 2
            var y = mHeight / 2 + sin(angle) * mRadius + mRect.height() / 2
            canvas?.drawText(hour.toString(), x.toFloat(), y.toFloat(), mPaint!!)
        }
        //ve kim dong ho
        var hour = calendar!!.get(Calendar.HOUR_OF_DAY)
        hour = if (hour > 12) hour - 12 else hour
        drawHandLine(canvas, (hour + ((calendar!!.get(Calendar.MINUTE)).toFloat() / 60)) * 5f, true, false) //kim gio
        drawHandLine(canvas, calendar!!.get(Calendar.MINUTE).toFloat(), false, false) //kim phut
        drawHandLine(canvas, calendar!!.get(Calendar.SECOND).toFloat(), false, true)//kim giay
        postInvalidateDelayed(1000)
        invalidate()
    }

    fun drawHandLine(canvas: Canvas?, moment: Float, isHour: Boolean, isSecond: Boolean) {
        var angle = Math.PI * moment / 30 - Math.PI / 2
        var handRadius = if (isHour) mRadius - mHandTruncation - mHourHandTruncation else mRadius - mHandTruncation
        if (isSecond) mPaint?.color = Color.YELLOW
        canvas?.drawLine((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), (mWidth / 2 + cos(angle) * handRadius).toFloat(), (mHeight / 2 + sin(angle) * handRadius).toFloat(), mPaint!!)
    }

    override fun onDetachedFromWindow() {
        job.cancel()
        super.onDetachedFromWindow()
    }
}