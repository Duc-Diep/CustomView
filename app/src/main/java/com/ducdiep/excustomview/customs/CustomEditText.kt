package com.ducdiep.excustomview.customs

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import com.ducdiep.excustomview.R
import com.ducdiep.excustomview.dialog.ChooseColorDialog


class CustomEditText : androidx.appcompat.widget.AppCompatEditText {

    private var mHeight = 0
    private var mWidth = 0
    var widthRect = 0
    private var mPaint: Paint? = null
    private var mRect: Rect = Rect()
    private var isInit = false
    var color:String = "#000000"


    fun initAttr(){
        if (!isInit){
            mPaint = Paint()
            mHeight = height
            mWidth = width
            widthRect = height
            isInit=true
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText)
        color = typedArray.getString(R.styleable.CustomEditText_defaut_color).toString()
        typedArray.recycle()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initAttr()
        setTextColor(Color.parseColor(color))
        mPaint?.reset()
        mPaint?.color = Color.parseColor(color)
        mPaint?.style = Paint.Style.FILL
        canvas?.drawRect((mWidth - widthRect).toFloat() + 15, 15f, mWidth.toFloat() - 15, mHeight.toFloat() - 15, mPaint!!)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (h!=oldh){
            mHeight = h
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var action = event?.action
        var x = event?.x
        var y = event?.y
        when(action){
            MotionEvent.ACTION_DOWN -> {
                if (x!! > (mWidth - widthRect).toFloat() + 15 && x < mWidth.toFloat() - 15 && y!! > 15f && y < mHeight.toFloat() - 15) {
//                    Toast.makeText(context, "x=$x,y=$y", Toast.LENGTH_SHORT).show()
                    var dialog = ChooseColorDialog(context, R.style.Theme_Dialog)
                    dialog.setCanceledOnTouchOutside(true)
                    dialog.setOnChooseItem {
//                        Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
                        color = it
                        invalidate()
                    }
                    dialog.show()
                } else {
                    requestFocus()
                    val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                }
            }

        }


        return true
    }

}