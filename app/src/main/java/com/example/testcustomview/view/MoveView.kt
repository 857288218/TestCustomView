package com.example.testcustomview.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

class MoveView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    private var downX: Float = 0F
    private var downY: Float = 0F
    private var nowX: Float = 0F
    private var nowY: Float = 0F
    // 是否响应点击事件
    private var isCustom = false

    init {
        setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isCustom = false
                    downX = event.rawX
                    downY = event.rawY
                    nowX = downX
                    nowY = downY
                }
                MotionEvent.ACTION_MOVE -> {
                    val moveX = event.rawX
                    val moveY = event.rawY
                    var deltX = moveX - nowX
                    var deltY = moveY - nowY
                    nowX = moveX
                    nowY = moveY
                    if (!isCustom) {
                        isCustom = nowX - downX > 10 || nowY - downY > 10 || nowX - downX < -10 || nowY - downY < -10
                    }

                    // fix x 保证view不滑出其parent
                    if (v.translationX + deltX > (parent as ViewGroup).measuredWidth - measuredWidth) {
                        deltX = (parent as ViewGroup).measuredWidth - measuredWidth - v.translationX
                    }
                    if (v.translationX + deltX < 0) {
                        deltX = - v.translationX
                    }
                    // fix y 保证view不滑出其parent
                    if (v.translationY + deltY > (parent as ViewGroup).measuredHeight - measuredHeight) {
                        deltY = (parent as ViewGroup).measuredHeight - measuredHeight - v.translationY
                    }
                    if (v.translationY + deltY < 0) {
                        deltY = - v.translationY
                    }

                    v.translationX = v.translationX + deltX
                    v.translationY = v.translationY + deltY

                    Log.d("MoveView", "${v.left}  ${v.translationX}")
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // 是否消费掉事件不执行后续的onTouchEvent, 也就不会执行onClickListener
                    return@setOnTouchListener isCustom
                }
            }
            false
        }
        setOnClickListener {
            Toast.makeText(context, "click move view", Toast.LENGTH_SHORT).show()
        }
    }

}