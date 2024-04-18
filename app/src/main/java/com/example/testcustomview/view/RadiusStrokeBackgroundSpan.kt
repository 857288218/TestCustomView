package com.example.testcustomview.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.text.style.ReplacementSpan

@Suppress("LongParameterList")
class RadiusStrokeBackgroundSpan(
    private val strokeColor: Int,
    private val strokeWidth: Float,
    private val textColor: Int,
    private val textSize: Float,
    private val radius: Float,
    private val paddingHorizontal: Int,
    private val paddingVertical: Int,
    private val rightTransparentBlockWidth: Int = 0
) : ReplacementSpan() {

    private var size: Int = 0

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        paint.textSize = textSize
        paint.typeface = Typeface.DEFAULT
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        size = (paint.measureText(text, start, end) + paddingHorizontal * 2).toInt() + rightTransparentBlockWidth
        return size
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        paint.color = strokeColor
        // 绘制矩形向右偏移strokeWidth，否则左边框有一部分显示不出来
        val oval = RectF(
            x + strokeWidth,
            y + paint.ascent() - paddingVertical * 2,
            x + size - rightTransparentBlockWidth,
            y + paint.descent()
        )
        canvas.drawRoundRect(oval, radius, radius, paint)

        paint.color = textColor
        paint.textSize = textSize
        paint.typeface = Typeface.DEFAULT
        paint.style = Paint.Style.FILL
        canvas.drawText(text ?: "", start, end, oval.left + paddingHorizontal, y.toFloat() - paddingVertical, paint)

        if (rightTransparentBlockWidth > 0) {
            paint.color = Color.TRANSPARENT
            val block = RectF(oval.right, y + paint.ascent() - paddingVertical * 2, x + size, y + paint.descent())
            canvas.drawRect(block, paint)
        }
    }
}
