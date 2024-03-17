package com.example.testcustomview.util

import android.graphics.Rect
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristics
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.ViewGroupUtils

// 增大View点击区域
fun View.expand(dx: Int, dy: Int) {
    // 将刚才定义代理类放到方法内部，调用方不需要了解这些细节
    class MultiTouchDelegate(bound: Rect? = null, delegateView: View) : TouchDelegate(bound, delegateView) {
        val delegateViewMap = mutableMapOf<View, Rect>()
        private var delegateView: View? = null

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val x = event.x.toInt()
            val y = event.y.toInt()
            var handled = false
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    delegateView = findDelegateViewUnder(x, y)
                }
                MotionEvent.ACTION_CANCEL -> {
                    delegateView = null
                }
            }
            delegateView?.let {
                event.setLocation(it.width / 2f, it.height / 2f)
                handled = it.dispatchTouchEvent(event)
            }
            return handled
        }

        private fun findDelegateViewUnder(x: Int, y: Int): View? {
            delegateViewMap.forEach { entry -> if (entry.value.contains(x, y)) return entry.key }
            return null
        }
    }

    // 获取当前控件的父控件
    val parentView = parent as? ViewGroup
    // 若父控件不是 ViewGroup, 则直接返回
    parentView ?: return

    // 若父控件未设置触摸代理，则构建 MultiTouchDelegate 并设置给它
    if (parentView.touchDelegate == null) parentView.touchDelegate = MultiTouchDelegate(delegateView = this)
    post {
        val rect = Rect()
        // 获取子控件在父控件中的区域
        ViewGroupUtils.getDescendantRect(parentView, this, rect)
        // 将响应区域扩大
        rect.inset(-dx, -dy)
        // 将子控件作为代理控件添加到 MultiTouchDelegate 中
        (parentView.touchDelegate as? MultiTouchDelegate)?.delegateViewMap?.put(this, rect)
    }
}

fun TextView.getStaticLayout(text: CharSequence?, width: Int): StaticLayout {
    val builder = StaticLayout.Builder.obtain(
        text ?: getText(),
        0,
        text?.length ?: getText().length,
        this.paint,
        width
    )
        .setAlignment(Layout.Alignment.ALIGN_NORMAL)
        .setTextDirection(TextDirectionHeuristics.FIRSTSTRONG_LTR)
        .setLineSpacing(this.lineSpacingExtra, this.lineSpacingMultiplier)
        .setIncludePad(this.includeFontPadding)
        .setBreakStrategy(this.breakStrategy)
        .setHyphenationFrequency(this.hyphenationFrequency)
        .setMaxLines(if (this.maxLines == -1) Integer.MAX_VALUE else this.maxLines)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        builder.setJustificationMode(this.justificationMode)
    }
    if (this.ellipsize != null && this.keyListener == null) {
        builder.setEllipsize(this.ellipsize).setEllipsizedWidth(width)
    }
    return builder.build()
}

/**
 * 获取view当前占据屏幕的百分比
 */
//private int getVisibilityPercents(View currentView) {
//    int percents = 100;
//    Rect rect = new Rect();
//    //防止出现视频已不在可见得范围之内仍然返回100（完全可见）
//    boolean isVisible = currentView.getLocalVisibleRect(rect);
//    if (isVisible) { //可见时做百分比的计算
//        int height = currentView.getHeight();
//        if(viewIsPartiallyHiddenTop(rect)){
//            // view is partially hidden behind the top edge
//            percents = (height - rect.top) * 100 / height;
//        } else if(viewIsPartiallyHiddenBottom(rect, height)){
//            percents = rect.bottom * 100 / height;
//        }
//    }else { //View已经不可见
//        percents = 0;
//    }
//    return percents;
//}
//
////view底部部分不可见
//private boolean viewIsPartiallyHiddenBottom(Rect rect, int height) {
//    return rect.bottom > 0 && rect.bottom < height;
//}
//
////view顶部部分不可见
//private boolean viewIsPartiallyHiddenTop(Rect rect) {
//    return rect.top > 0;
//}