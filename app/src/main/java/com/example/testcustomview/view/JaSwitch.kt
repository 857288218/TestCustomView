package com.example.testcustomview.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.example.testcustomview.R
import com.example.testcustomview.util.dp2px

class JaSwitch : FrameLayout {

    private var thumb: View? = null
    private var track: View? = null
    private var switchWidth = context.dp2px(44F)
    private var thumbWidth = context.dp2px(18F)
    private var thumbStroke = context.dp2px(4F)

    var isChecked = false
        set(value) {
            changeUiByCheck(value)
            field = value
        }

    var onCheckedChangeListener: ((JaSwitch, Boolean) -> Unit)? = null

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
        thumbStroke =
            typedArray.getDimension(R.styleable.JaSwitch_thumbStroke, thumbStroke.toFloat()).toInt()
        typedArray.recycle()

        track = View(context).apply {
            // 滑道高度 = 滑块高度 + 滑块上下margin
            layoutParams = LayoutParams(switchWidth, thumbWidth + thumbStroke * 2)
            setBackgroundColor(ContextCompat.getColor(context, R.color.gray_300))
        }
        addView(track)
        thumb = View(context).apply {
            layoutParams = MarginLayoutParams(thumbWidth, thumbWidth).apply {
                this.setMargins(thumbStroke, thumbStroke, thumbStroke, thumbStroke)
            }
            setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
        addView(thumb)

        setOnClickListener {
            isChecked = !isChecked
        }

        isChecked = checked
    }

    private fun changeUiByCheck(isChecked: Boolean) {
        if (this.isChecked != isChecked) {
            track?.setBackgroundColor(
                if (isChecked) ContextCompat.getColor(context, R.color.purple_500) else ContextCompat.getColor(
                    context,
                    R.color.gray_300
                )
            )
            onCheckedChangeListener?.invoke(this, isChecked)
            translateThumb(isChecked)
        }
    }

    private fun translateThumb(isChecked: Boolean) {
        var fromXValue = 0F
        var toXValue = (switchWidth - thumbStroke * 2 - thumbWidth).toFloat()
        if (!isChecked) {
            fromXValue = toXValue
            toXValue = 0F
        }
        val translateAnimation = TranslateAnimation(
            Animation.ABSOLUTE,
            fromXValue,
            Animation.ABSOLUTE,
            toXValue,
            0,
            0F,
            0,
            0F
        )
        translateAnimation.fillAfter = true
        translateAnimation.repeatCount = 0
        translateAnimation.duration = 200
        thumb?.startAnimation(translateAnimation)
    }
}