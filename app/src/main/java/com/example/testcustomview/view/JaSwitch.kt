package com.example.testcustomview.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.example.testcustomview.R
import com.example.testcustomview.util.dp2px
import com.example.testcustomview.util.setMargin

class JaSwitch : FrameLayout {

    private var thumb: View? = null
    private var track: View? = null

    private var switchWidth = context.dp2px(60F)
    private var thumbWidth = context.dp2px(24F)
    private var thumbHeight = context.dp2px(8F)
    private var thumbStroke = context.dp2px(6F)
    private var trackRadius = context.dp2px(2f)
    private var thumbRadius = context.dp2px(1f)
    private var valueAnimator: ValueAnimator? = null

    private var fromUser = false
    var isCheckAble = true

    var isChecked = false
        set(value) {
            if (value != field) {
                changeUiByCheck(value, fromUser)
                field = value
            }
        }

    fun setIsChecked(check: Boolean) {
        isChecked = check
    }

    fun setIsAvailable(available: Boolean) {
        isCheckAble = available
    }

    var onAvailableListener: ((isAvailable: Boolean) -> Unit)? = null

    var onCheckedChangeListener: ((JaSwitch, isChecked: Boolean, fromUser: Boolean) -> Unit)? = null

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.JaSwitch)
        val checked = typedArray.getBoolean(R.styleable.JaSwitch_checked, false)
        val width =
            typedArray.getLayoutDimension(R.styleable.JaSwitch_android_layout_width, switchWidth)
        if (width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            switchWidth = width
        }
        thumbWidth =
            typedArray.getDimension(R.styleable.JaSwitch_thumbWidth, thumbWidth.toFloat()).toInt()
        thumbHeight =
            typedArray.getDimension(R.styleable.JaSwitch_thumbHeight, thumbHeight.toFloat()).toInt()
        thumbStroke =
            typedArray.getDimension(R.styleable.JaSwitch_thumbStroke, thumbStroke.toFloat()).toInt()
        thumbRadius =
            typedArray.getDimension(R.styleable.JaSwitch_thumbRadius, thumbRadius.toFloat()).toInt()
        trackRadius =
            typedArray.getDimension(R.styleable.JaSwitch_trackCornerRadius, trackRadius.toFloat())
                .toInt()
        typedArray.recycle()

        track = View(context).apply {
            // 滑道高度 = 滑块高度 + 滑块上下margin
            layoutParams = LayoutParams(switchWidth, thumbHeight + thumbStroke * 2)
            background = GradientDrawable().apply {
                val bgColor = context.getColor(R.color.gray_300)
                setColor(bgColor)
                cornerRadius = trackRadius.toFloat()
            }
        }
        addView(track)
        thumb = View(context).apply {
            layoutParams = MarginLayoutParams(thumbWidth, thumbHeight).apply {
                this.setMargins(thumbStroke, thumbStroke, thumbStroke, thumbStroke)
            }
            background = GradientDrawable().apply {
                val bgColor = context.getColor(R.color.white)
                setColor(bgColor)
                cornerRadius = thumbRadius.toFloat()
            }
        }
        addView(thumb)

        setOnClickListener {
            if (!isCheckAble) {
                onAvailableListener?.invoke(false)
                return@setOnClickListener
            }
            fromUser = true
            isChecked = !isChecked
            onAvailableListener?.invoke(true)
        }

        isChecked = checked
    }

    private fun changeUiByCheck(isChecked: Boolean, fromUser: Boolean) {
        track?.background = GradientDrawable().apply {
            val bgColor = if (isChecked) {
                context.getColor(R.color.purple_500)
            } else {
                context.getColor(R.color.gray_300)
            }
            setColor(bgColor)
            cornerRadius = trackRadius.toFloat()
        }
        onCheckedChangeListener?.invoke(this, isChecked, fromUser)
        this.fromUser = false
        translateThumb(isChecked, fromUser)
    }

    private fun translateThumb(isChecked: Boolean, fromUser: Boolean) {
        if (valueAnimator?.isRunning == true) {
            valueAnimator?.cancel()
        }
        var fromXValue = 0F
        var toXValue = (switchWidth - thumbStroke * 2 - thumbWidth).toFloat()
        if (!isChecked) {
            fromXValue = toXValue
            toXValue = 0F
        }
        valueAnimator = ValueAnimator.ofFloat(fromXValue, toXValue)
        valueAnimator?.addUpdateListener {
            thumb?.setMargin(
                thumbStroke.toFloat(),
                (it.animatedValue as Float).toFloat() + thumbStroke.toFloat(),
                thumbStroke.toFloat(),
                thumbStroke.toFloat()
            )
        }
        valueAnimator?.duration = if (fromUser) 200 else 0
        valueAnimator?.start()
    }
}