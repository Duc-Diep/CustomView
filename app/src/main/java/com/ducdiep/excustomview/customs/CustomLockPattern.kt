package com.ducdiep.excustomview.customs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.ducdiep.excustomview.R
import kotlin.math.pow

class CustomLockPattern : View {
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )

    var sharedPreferences = context.getSharedPreferences("LockPattern", Context.MODE_PRIVATE)
    var paint = Paint()
    private var mPadding = 0f
    private var mPaddingTextLeft = 0f
    private var mPaddingTextTop = 0f
    private var startPoint: Point? = null
    private var endPoint: Point? = null
    private var isInit = false
    private var isDrawing = false
    private var isCorrect = false
    private var isCheck = false
    private var isFirstTime: Boolean = true
    private var points = ArrayList<Point>()
    private var password = ArrayList<Int>()
    private var listSavedPass = ArrayList<Int>()
    var fontSize: Float = 0f


    private fun init() {
        mPadding = width.coerceAtMost(height) / 6f
        mPaddingTextLeft = (width / 2).toFloat()
        mPaddingTextTop = (mPadding+100)/2
        add9Point()
        isFirstTime = sharedPreferences.getBoolean("isFirstTime", true)
        listSavedPass = ArrayList()
        val size = sharedPreferences.getInt("size", 0)

        for (i in 0..size)
            listSavedPass.add(sharedPreferences.getInt("key$i", 0))
        isInit = true
    }

    private fun add9Point() {
        points.add(Point(mPadding.toInt(), mPadding.toInt() + 100))
        points.add(Point(width / 2, mPadding.toInt() + 100))
        points.add(Point(width - mPadding.toInt(), mPadding.toInt() + 100))

        points.add(Point(mPadding.toInt(), height / 2 + 50))
        points.add(Point(width / 2, height / 2 + 50))
        points.add(Point(width - mPadding.toInt(), height / 2 + 50))

        points.add(Point(mPadding.toInt(), height - mPadding.toInt()))
        points.add(Point(width / 2, height - mPadding.toInt()))
        points.add(Point(width - mPadding.toInt(), height - mPadding.toInt()))
    }

    override fun onDraw(canvas: Canvas?) {
        if (!isInit) {
            init()
            paint.color = Color.BLUE
            fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20f,
                    resources.displayMetrics)
            paint.textSize = fontSize
            paint.textAlign = Paint.Align.CENTER
            if (isFirstTime) {
                canvas?.drawText(context.getString(R.string.create_password), mPaddingTextLeft, mPaddingTextTop, paint)
            } else {
                canvas?.drawText(context.getString(R.string.enter_password), mPaddingTextLeft, mPaddingTextTop, paint)
            }
        }
        draw9Point(canvas!!)
        drawPointToPoint(canvas)
        if (isFirstTime) {
            paint.textSize = fontSize
            if (isCheck) {
                if (isCorrect) {
                    paint.color = Color.GREEN
                    canvas.drawText(context.getString(R.string.create_success), mPaddingTextLeft, mPaddingTextTop, paint)
                    isFirstTime = false
                    sharedPreferences.edit().putBoolean("isFirstTime", false).apply()
                } else {
                    paint.color = Color.RED
                    canvas.drawText(context.getString(R.string.request_password), mPaddingTextLeft, mPaddingTextTop, paint)
                }
            }
            isCheck = false
        } else {
            if (isCheck) {
                paint.textSize = fontSize
                if (isCorrect) {
                    paint.color = Color.GREEN
                    canvas.drawText(context.getString(R.string.enter_success), mPaddingTextLeft, mPaddingTextTop, paint)
                } else {
                    paint.color = Color.RED
                    canvas.drawText(context.getString(R.string.incorrect_password), mPaddingTextLeft, mPaddingTextTop, paint)
                }
                isCheck = false
            }
        }
    }

    private fun savePassword() {
        isCorrect = if (password.size < 4) {
            password.clear()
            false
        } else {
            listSavedPass.clear()
            listSavedPass.addAll(password)
            sharedPreferences.edit().putInt("size", listSavedPass.size).apply()
            for (i in listSavedPass.indices) {
                sharedPreferences.edit().putInt("key$i", listSavedPass[i]).apply()
            }
            password.clear()
            true
        }
    }

    private fun checkPassword() {
        if (password.size < 4) {
            isCorrect = false
            return
        }
        for (i in password.indices) {
            if (password[i] != listSavedPass[i]) {
                isCorrect = false
                password.clear()
                return
            }
        }
        password.clear()
        isCorrect = true
    }

    private fun draw9Point(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        for (element in points) {
            canvas.drawCircle(element.x.toFloat(), element.y.toFloat(), 12f, paint)
        }
    }

    private fun drawPointToPoint(canvas: Canvas) {
        if (isDrawing && endPoint != null) {
            paint.color = Color.BLUE
            paint.strokeWidth = 5f
            paint.style = Paint.Style.STROKE
            for (i in 0..password.size) {
                if (i < password.size - 1) {
                    canvas.drawLine(points[password[i]].x.toFloat(),
                            points[password[i]].y.toFloat(),
                            points[password[i + 1]].x.toFloat(),
                            points[password[i + 1]].y.toFloat(),
                            paint)
                    canvas.drawCircle(points[password[i]].x.toFloat(), points[password[i]].y.toFloat(),70f,paint)
                }
            }
            canvas.drawCircle(points[password[password.size-1]].x.toFloat(), points[password[password.size-1]].y.toFloat(),70f,paint)
            canvas.drawLine(startPoint!!.x.toFloat(),
                    startPoint!!.y.toFloat(), endPoint!!.x.toFloat(), endPoint!!.y.toFloat(), paint)
            startPoint = endPoint
            endPoint = null
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                for (i in points) {
                    val x = (event.x - i.x).toDouble().pow(2.0)
                    val y = (event.y - i.y).toDouble().pow(2.0)
                    if (x + y < 80.0.pow(2.0) && !password.contains(points.indexOf(i))) {
                        password.add(points.indexOf(i))
                        startPoint = i
                        isDrawing = true
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (startPoint != null) {
                    for (i in points) {
                        val x = (event.x - i.x).toDouble().pow(2.0)
                        val y = (event.y - i.y).toDouble().pow(2.0)
                        if (x + y < 70.0.pow(2.0) && !password.contains(points.indexOf(i))) {
                            endPoint = i
                            password.add(points.indexOf(i))
                            invalidate()
                        }
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                startPoint = null
                endPoint = null
                isDrawing = false
                isCheck = true
                if (!isFirstTime)
                    checkPassword()
                else
                    savePassword()
                invalidate()
            }
        }
        return true
    }

}