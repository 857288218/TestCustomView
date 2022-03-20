package com.example.testcustomview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class RectPointView : View {

    private var mX: Int = -1
    private var mY: Int = -1
    private val mRect = Rect(100, 10, 300, 100)
    private val mPaint: Paint by lazy(LazyThreadSafetyMode.NONE) {
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mX = event.x.toInt()
        mY = event.y.toInt()
        if (event.action == MotionEvent.ACTION_DOWN) {
            invalidate()
            return true
        } else if (event.action == MotionEvent.ACTION_UP) {
            mX = -1
            mY = -1
        }
        postInvalidate()
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mRect.contains(mX, mY)) {
            mPaint.color = Color.RED
        } else {
            mPaint.color = Color.GREEN
        }
        canvas?.drawRect(mRect, mPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}