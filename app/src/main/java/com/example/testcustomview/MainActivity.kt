package com.example.testcustomview

import android.animation.*
import android.content.Intent
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testcustomview.databinding.ActivityMainBinding
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var valueAnimator: ValueAnimator? = null
    private var animatorSet: AnimatorSet? = null
    private var animationSet: AnimationSet = AnimationSet(true)
    private var isMenuOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.onClickListener = this

        binding.button.setOnClickListener {
//            animationSet = AnimationSet(true)
//            animationSet.setAnimationListener(object : Animation.AnimationListener {
//                override fun onAnimationRepeat(animation: Animation?) {
//                    Log.d("Animation", "onAnimationRepeat")
//                }
//
//                override fun onAnimationEnd(animation: Animation?) {
//                    Log.d("Animation", "onAnimationEnd")
//                }
//
//                override fun onAnimationStart(animation: Animation?) {
//                    Log.d("Animation", "onAnimationStart")
//                }
//            })
//            if (binding.tvScan.visibility == View.VISIBLE) {
//                val animation = AnimationUtils.loadAnimation(this, R.anim.translation_dismiss)
//                animationSet.addAnimation(animation)
//                animationSet.repeatCount = Animation.INFINITE
//                binding.tvScan.startAnimation(animationSet)
//                binding.tvScan.visibility = View.GONE
//            } else {
//                val animation = AnimationUtils.loadAnimation(this, R.anim.translate_in)
//                animationSet.addAnimation(animation)
//                animationSet.repeatCount = Animation.INFINITE
//                binding.tvScan.startAnimation(animation)
//                binding.tvScan.visibility = View.VISIBLE
//            }
            val translateY = ObjectAnimator.ofFloat(binding.tvScan, "translationY", 0f, 200f, 0f)
            val translateY2 = ObjectAnimator.ofFloat(binding.btn, "translationY", 0f, 200f, 0f)
//            translateY2.startDelay = 2000
//            translateY.startDelay = 2000
            val bg = ObjectAnimator.ofArgb(binding.tvScan, "backgroundColor", 0xffff0000.toInt(), 0xff00ff00.toInt())
            val animatorSet = AnimatorSet()
            animatorSet.play(translateY2).after(translateY)
            animatorSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    Log.d("Animation", "onAnimationStart")
                }

                override fun onAnimationRepeat(animation: Animator?) {
                    Log.d("Animation", "onAnimationRepeat")
                }

                override fun onAnimationEnd(animation: Animator?) {
                    Log.d("Animation", "onAnimationEnd")
                }

                override fun onAnimationCancel(animation: Animator?) {
                    Log.d("Animation", "onAnimationCancel")
                }
            })
            animatorSet.duration = 2000
//            animatorSet.startDelay = 2000
            animatorSet.start()
            this.animatorSet = animatorSet
        }

        binding.btn.setOnClickListener {
            doValueAnimation()
        }
        binding.tvTranslation.setOnClickListener {
            Toast.makeText(this, "click me", Toast.LENGTH_SHORT).show()
            valueAnimator?.removeAllUpdateListeners()
            animatorSet?.cancel()
            //不生效，调用binding.tvScan.clearAnimation()
            animationSet.cancel()
            binding.tvScan.clearAnimation()

            startActivity(Intent(this, TelescopeActivity::class.java))
        }

    }

    private fun doValueAnimation() {
//        val valueAnimation = ValueAnimator.ofObject(CharEvaluator(), Character('A'), Character('Z'))
//        valueAnimation.duration = 10000
//        valueAnimation.addUpdateListener {
//            val curValue = (it.animatedValue as Char)
//            binding.tvTranslation.text = curValue.toString()
//        }
//        valueAnimation.repeatCount = ValueAnimator.INFINITE
//        valueAnimation.repeatMode = ValueAnimator.REVERSE
//        valueAnimation.interpolator = AccelerateInterpolator()
//        valueAnimation.start()
//        valueAnimator = valueAnimation

        val keyFrame0 = Keyframe.ofObject(0f, Character('A'))
        val keyFrame1 = Keyframe.ofObject(0.5f, Character('L'))
        val keyFrame2 = Keyframe.ofObject(1f, Character('Z'))
        //keyFrame0——keyFrame1之间是使用BounceInterpolator
        keyFrame1.interpolator = BounceInterpolator()
        val frameHolder = PropertyValuesHolder.ofKeyframe("CharText", keyFrame0, keyFrame1, keyFrame2)
        //Keyframe的必须设置估值器，也可以直接给ObjectAnimator设置
        frameHolder.setEvaluator(CharEvaluator())
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(binding.tvTranslation, frameHolder)
        objectAnimator.duration = 15000
        objectAnimator.start()
    }

    private fun doAnimationOpen(view: View, index: Int, radius: Int) {
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }
        val degree = Math.toRadians(90.0) / 4 * index
        val translationX = - (radius * sin(degree)).toInt()
        val translationY = - (radius * cos(degree)).toInt()
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, "translationX", 0F, translationX.toFloat()),
            ObjectAnimator.ofFloat(view, "translationY", 0F, translationY.toFloat()),
            ObjectAnimator.ofFloat(view, "scaleX", 0F, 1F),
            ObjectAnimator.ofFloat(view, "scaleY", 0f, 1F))
        animatorSet.duration = 500
        animatorSet.start()
    }

    private fun doAnimationClose(view: View, index: Int, radius: Int) {
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }
        val degree = Math.toRadians(90.0) / 4 * index
        val translationX = -radius * sin(degree)
        val translationY = -radius * cos(degree)
        //等同于上面animatorSet.playTogether
        val animator = ObjectAnimator.ofPropertyValuesHolder(view,
            PropertyValuesHolder.ofFloat("translationX", translationX.toFloat(), 0F),
            PropertyValuesHolder.ofFloat("translationY", translationY.toFloat(), 0F),
            PropertyValuesHolder.ofFloat("scaleX", 1F, 0F),
            PropertyValuesHolder.ofFloat("scaleY", 1F, 0F))
        animator.duration = 500
        animator.start()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.menu, R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5 -> {
                if (!isMenuOpen) {
                    isMenuOpen = true
                    openMenu()
                } else {
                    isMenuOpen = false
                    closeMenu()
                }
            }
        }
    }

    private fun openMenu() {
        doAnimationOpen(binding.item1, 0, 300)
        doAnimationOpen(binding.item2, 1, 300)
        doAnimationOpen(binding.item3, 2, 300)
        doAnimationOpen(binding.item4, 3, 300)
        doAnimationOpen(binding.item5, 4, 300)
    }

    private fun closeMenu() {
        doAnimationClose(binding.item1, 0, 300)
        doAnimationClose(binding.item2, 1, 300)
        doAnimationClose(binding.item3, 2, 300)
        doAnimationClose(binding.item4, 3, 300)
        doAnimationClose(binding.item5, 4, 300)
    }

    override fun onDestroy() {
        super.onDestroy()
        valueAnimator?.cancel()
    }
}