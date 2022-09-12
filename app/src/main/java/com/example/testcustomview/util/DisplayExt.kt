package com.example.testcustomview.util

import android.content.Context
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowInsets
import android.view.WindowManager


/**
 * 应用程序的显示宽度
 */
inline fun Context.getDisplayWidth(): Int {
    val windowManager: WindowManager =
        this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        return windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
//        val heightPixels = outMetrics.heightPixels
//        val densityDpi = outMetrics.densityDpi
//        val density = outMetrics.density
//        val scaledDensity = outMetrics.scaledDensity
    }
}

/**
 * 应用程序的显示高度
 */
inline fun Context.getDisplayHeight(): Int {
    val windowManager: WindowManager =
        this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        return windowMetrics.bounds.height() - insets.top - insets.bottom
    } else {
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
//        val heightPixels = outMetrics.heightPixels
//        val densityDpi = outMetrics.densityDpi
//        val density = outMetrics.density
//        val scaledDensity = outMetrics.scaledDensity
    }
}

/**
 * 应用程序的显示宽度
 */
inline fun Context.getRealDisplayWidth(): Int {
    val windowManager: WindowManager =
        this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        return windowMetrics.bounds.width()
    } else {
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(outMetrics)
        return outMetrics.widthPixels
//        val heightPixels = outMetrics.heightPixels
//        val densityDpi = outMetrics.densityDpi
//        val density = outMetrics.density
//        val scaledDensity = outMetrics.scaledDensity
    }
}

/**
 * 应用程序的显示高度
 */
inline fun Context.getRealDisplayHeight(): Int {
    val windowManager: WindowManager =
        this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        return windowMetrics.bounds.height()
    } else {
        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(outMetrics)
        return outMetrics.heightPixels
//        val heightPixels = outMetrics.heightPixels
//        val densityDpi = outMetrics.densityDpi
//        val density = outMetrics.density
//        val scaledDensity = outMetrics.scaledDensity
    }
}

/**
 * dp转px
 *
 * @param dpVal dp value
 * @return px value
 */
fun Context.dp2px(dpVal: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dpVal,
        resources.displayMetrics
    ).toInt()
}

fun Context.sp2px(spVal: Float): Int {
    val fontScale = resources.displayMetrics.scaledDensity
    return (spVal * fontScale + 0.5f).toInt()
}



