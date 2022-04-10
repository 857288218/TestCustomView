package com.example.testcustomview.activity

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.testcustomview.R

class ThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three)

        val options1 = BitmapFactory.Options()
        options1.inSampleSize = 2
        val bitmapOrigin = BitmapFactory.decodeResource(resources, R.drawable.ic_work_nangua, options1)
        findViewById<ImageView>(R.id.iv_origin).setImageBitmap(bitmapOrigin)

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.ic_work_nangua, options)
        //压缩图片, 当图片的宽高(px)大于imageView的宽高时，可以选择压缩图片以减少内存占用
        //在设置inSampleSizes时应该注意使得缩放后的图片尺寸(px)尽量>=相应的imageView大小
        //按上面注释使用inSampleSize压缩，肉眼不容易看出图片质量下降
        options.inJustDecodeBounds = false
        options.inSampleSize = calSampleSize(options, 500, 100)
        val bitmapSample = BitmapFactory.decodeResource(resources, R.drawable.ic_work_nangua, options)
        findViewById<ImageView>(R.id.iv_sample).setImageBitmap(bitmapSample)

        //见《Android自定义控件开发入门与实战》394页
        val densityBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_work_nangua)
        findViewById<ImageView>(R.id.img1).setImageBitmap(densityBitmap)
        Log.d("rjq", "density:" + densityBitmap.density + " width:"+densityBitmap.width+" height:"+densityBitmap.height)

        densityBitmap.density = densityBitmap.density * 2
        findViewById<ImageView>(R.id.img2).setImageBitmap(densityBitmap)
        Log.d("rjq", "density:" + densityBitmap.density + " width:"+densityBitmap.width+" height:"+densityBitmap.height)

        //见《Android自定义控件开发入门与实战》375页
        val optionsDensity = BitmapFactory.Options()
        optionsDensity.inDensity = 400
        optionsDensity.inTargetDensity = 400
        val bitmapDensity = BitmapFactory.decodeResource(resources, R.drawable.ic_work_nangua, optionsDensity)
        findViewById<ImageView>(R.id.iv_density).setImageBitmap(bitmapDensity)
        Log.d("rjq", " width:"+bitmapDensity.width+" height:"+bitmapDensity.height)
    }

    fun calSampleSize(options: BitmapFactory.Options, dstWidth: Int, dstHeight: Int): Int {
        val rawWidth = options.outWidth
        val rawHeight = options.outHeight
        var sampleSize = 1
        if (rawWidth > dstWidth || rawHeight > dstHeight) {
            val ratioHeight = rawHeight.toFloat() / dstHeight
            val ratioWidth = rawWidth.toFloat() / dstWidth
            sampleSize = ratioHeight.coerceAtLeast(ratioWidth).toInt()
        }
        return sampleSize
    }
}