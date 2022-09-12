package com.example.testcustomview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.View
import com.example.testcustomview.R

// 仿抖音视频loadingView
class VideoLoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mWidth = 0
    private var mHeight = 0
    private var mProgressWidth: Int
    private val mMinProgressWidth: Int
    private val mPaint: Paint
    private var mColor: String? = null
    private val mHandler: Handler
    private var mTimePeriod = 10L

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VideoLoadingView)
        mColor = typedArray.getString(R.styleable.VideoLoadingView_progressColor)
        mMinProgressWidth = typedArray.getDimension(R.styleable.VideoLoadingView_minProgressWidth, 100f).toInt()
        mTimePeriod = typedArray.getInt(R.styleable.VideoLoadingView_timePeriod, 10).toLong()
        mProgressWidth = mMinProgressWidth
        typedArray.recycle()

        mPaint = Paint()
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
        mHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                invalidate()
                sendEmptyMessageDelayed(1, mTimePeriod)
            }
        }
        mHandler.sendEmptyMessageDelayed(1, mTimePeriod)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        require(mWidth >= mProgressWidth) {
            //如果宽度小于进度条的宽度
            "the progressWidth must less than mWidth"
        }
        mPaint.strokeWidth = mHeight.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //首先判断进度条的宽度是否大于view宽度
        if (mProgressWidth < mWidth) {
            //如果不大于，将进度条宽度增加10
            mProgressWidth += 10 //注意执行此步骤是mProgressWidth值有可能大于view宽度
        } else {
            //如果进度条宽度大于view宽度将进度条宽度设置为初始宽度
            mProgressWidth = mMinProgressWidth
        }
        //计算颜色透明度
        //mProgressWidth/mWidth 计算当前进度条宽度占总宽度的比例
        //255*mProgressWidth/mWidth 计算当前比例下对应的透明度值
        //由于是由不透明变成全透明，所以使用255减去其值
        var currentColorValue = 255 - 255 * mProgressWidth / mWidth
        if (currentColorValue > 255) {
            //由于mProgressWidth有可能大于view的宽度，要保证颜色值不能大于255
            currentColorValue = 255
        }
        if (currentColorValue < 30) {
            //此处是为了限制让其不成为全透明，如果设置为全透明，在最后阶段进度宽度渐变观察不到
            currentColorValue = 30
        }
        //将透明度转换为16进制
        val s = Integer.toHexString(currentColorValue)
        //拼接颜色字符串并转化为颜色值
        val color = Color.parseColor("#" + s + mColor?.substring(1, mColor?.length ?: 1))
        mPaint.color = color
        //使用canvas画进度条
        canvas.drawLine((mWidth / 2 - mProgressWidth / 2).toFloat(), (mHeight / 2).toFloat(), (mWidth / 2 + mProgressWidth / 2).toFloat(), (mHeight / 2).toFloat(), mPaint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeMessages(1)
    }
}