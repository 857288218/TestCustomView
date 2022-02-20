package com.example.testcustomview.activity

import android.animation.*
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.BounceInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.testcustomview.CharEvaluator
import com.example.testcustomview.R
import com.example.testcustomview.databinding.ActivityMainBinding
import event.StickyStringEvent
import event.StringEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import test.Child
import test.Parent
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var valueAnimator: ValueAnimator? = null
    private var animatorSet: AnimatorSet? = null
    private var animationSet: AnimationSet = AnimationSet(true)
    private var isMenuOpen = false
    private val imgIdLive = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        EventBus.getDefault().register(this)
        Looper.getMainLooper().setMessageLogging { x ->
            if (!x.contains("Choreographer\$FrameHandler"))
                Log.d("rjqLooper", x)
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.onClickListener = this
        binding.imgId = imgIdLive
        binding.lifecycleOwner = this
        //向flowLayout中动态添加view
        val view = View(this)
        val viewLayoutParams1 = LinearLayout.LayoutParams(100, 30)
        view.layoutParams = viewLayoutParams1
        view.setBackgroundColor(resources.getColor(R.color.black))
        binding.flowLayout.addView(view)
        Handler(Looper.getMainLooper()).postDelayed({ EventBus.getDefault().register(this) }, 3000)
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
            imgIdLive.value = R.drawable.ic_work_mudan
            doValueAnimation()
            EventBus.getDefault().post(StringEvent("event bus test"))
        }
        binding.tvTranslation.setOnClickListener {
            imgIdLive.value = R.drawable.ic_work_nangua
            Toast.makeText(this, "click me", Toast.LENGTH_SHORT).show()
            valueAnimator?.removeAllUpdateListeners()
            animatorSet?.cancel()
            //不生效，调用binding.tvScan.clearAnimation()
            animationSet.cancel()
            binding.tvScan.clearAnimation()

            startActivity(Intent(this, TelescopeActivity::class.java))
        }

        Thread {
            x()
        }.start()
        Thread {
            y()
        }.start()

        val child = Child<Int>()
        val parent = Parent<Int>()
        child.func(1)   // Child#func(Object);
        parent.func(1)  // Parent#func(Object);

        binding.clipView.setOnClickListener {
            startActivity(Intent(this@MainActivity, LongTextRecyclerViewActivity::class.java))
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LongTextRecyclerViewActivity::class.java))
            }, 3000)
        }

        val uri = Uri.parse("douban://douban.com/group/:id/join")
        Log.d("rjquri", uri.pathSegments.toString())

        findViewById<Button>(R.id.test_btn).setOnClickListener {
            Toast.makeText(this, "test btn", Toast.LENGTH_SHORT).show()
        }

        val num = 100
        val str: String? = num as? String
        Log.d("rjqas", str + "")
    }
    private val monitor = Object()
    private var k = 0
    private val monitor1 = Object()
    private val monitor2 = Object()

    fun x() {
        synchronized(monitor) {
            monitor.wait()
            k = 1
        }
    }

    fun y() {
        synchronized(monitor) {
            monitor.notify()
            Thread.sleep(500)
            Log.d("rjqmonitor", "$k")
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
        animatorSet.playTogether(
            ObjectAnimator.ofFloat(view, "translationX", 0F, translationX.toFloat()),
            ObjectAnimator.ofFloat(view, "translationY", 0F, translationY.toFloat()),
            ObjectAnimator.ofFloat(view, "scaleX", 0F, 1F),
            ObjectAnimator.ofFloat(view, "scaleY", 0f, 1F)
        )
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
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat("translationX", translationX.toFloat(), 0F),
            PropertyValuesHolder.ofFloat("translationY", translationY.toFloat(), 0F),
            PropertyValuesHolder.ofFloat("scaleX", 1F, 0F),
            PropertyValuesHolder.ofFloat("scaleY", 1F, 0F)
        )
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
        EventBus.getDefault().unregister(this)
        valueAnimator?.cancel()
    }

    @Subscribe
    fun onStringEvent(event: StringEvent) {
        Toast.makeText(this, event.msg, Toast.LENGTH_SHORT).show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onStickyStringEvent(event: StickyStringEvent) {
        Toast.makeText(this, "sticky: ${event.msg}", Toast.LENGTH_SHORT).show()
        val stickyStringEvent = EventBus.getDefault().getStickyEvent(StickyStringEvent::class.java)
        // 判断此粘性事件是否存在
        if (stickyStringEvent != null) {
            EventBus.getDefault().removeStickyEvent(stickyStringEvent)
        }
    }
}