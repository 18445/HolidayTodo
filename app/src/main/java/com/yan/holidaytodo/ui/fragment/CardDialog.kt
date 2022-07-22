package com.yan.holidaytodo.ui.fragment


import android.app.Dialog
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.yan.holidaytodo.R
import com.yan.holidaytodo.util.RotateAnimation


class CardDialog(context: Context) : Dialog(context) {


    private lateinit var view: View
    private lateinit var container : ConstraintLayout
    private lateinit var openAnimation: RotateAnimation

    private lateinit var closeAnimation: RotateAnimation
    private lateinit var backView : LinearLayout
    private lateinit var frontView : LinearLayout
    private lateinit var frontButton : Button
    private lateinit var backButton : Button

    private var centerX = 0
    private var centerY = 0
    private val depthZ = 700
    private val duration = 300L
    private var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //去掉系统的黑色矩形边框
        window!!.setBackgroundDrawableResource(R.color.white)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        initView()
    }

     private fun initView() {
         val inflater = LayoutInflater.from(context)
         view = inflater.inflate(R.layout.dialog_card, null)
         setContentView(view)
         val dialogWindow = window
         val lp = dialogWindow!!.attributes
         val d = context.resources.displayMetrics // 获取屏幕宽、高用
         lp.width = (d.widthPixels * 0.8).toInt() // 高度设置为屏幕的0.8
         lp.height = (d.heightPixels * 0.6).toInt() // 高度设置为屏幕的0.6
         dialogWindow.attributes = lp
         container = findViewById(R.id.csl_dialog_container)
         backView = findViewById(R.id.back)
         frontView = findViewById(R.id.front)
         setCanceledOnTouchOutside(false)
         setCancelable(true)
         initOpenAnim()
         initCloseAnim()
         frontButton = view.findViewById<Button>(R.id.toBack).apply {
             setOnClickListener {
                 startAnimation()
             }
         }
        backButton = view.findViewById<Button>(R.id.toFront).apply {
            setOnClickListener {
                startAnimation()
            }
        }
    }

    private fun startAnimation() {
        centerX = container.width / 2
        centerY = container.height / 2

        //用作判断当前点击事件发生时动画是否正在执行
        if (openAnimation.hasStarted() && !openAnimation.hasEnded()) {
            return
        }
        if (closeAnimation.hasStarted() && !closeAnimation.hasEnded()) {
            return
        }

        //判断动画执行
        if (isOpen) {
            container.startAnimation(openAnimation)
        } else {
            container.startAnimation(closeAnimation)
        }
        isOpen = !isOpen
    }

    /**
     * 卡牌文本介绍打开效果：注意旋转角度
     */
    private fun initOpenAnim() {
        //从0到90度，顺时针旋转视图，此时reverse参数为true，达到90度时动画结束时视图变得不可见，
        openAnimation = RotateAnimation(0, 90, centerX, centerY, depthZ, true)
        openAnimation.duration = duration
        openAnimation.fillAfter = true
        openAnimation.interpolator = AccelerateInterpolator()
        openAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                backView.visibility = View.GONE
                frontView.visibility = View.VISIBLE
                val rotateAnimation = RotateAnimation(270, 360, centerX, centerY, depthZ, false)
                rotateAnimation.duration = duration
                rotateAnimation.fillAfter = true
                rotateAnimation.interpolator = DecelerateInterpolator()
                container.startAnimation(rotateAnimation)
            }
        })
    }

    /**
     * 卡牌文本介绍关闭效果：旋转角度与打开时逆行即可
     */
    private fun initCloseAnim() {
        closeAnimation = RotateAnimation(360, 270, centerX, centerY, depthZ, true)
        closeAnimation.duration = duration
        closeAnimation.fillAfter = true
        closeAnimation.interpolator = AccelerateInterpolator()
        closeAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                backView.visibility = View.VISIBLE
                frontView.visibility = View.GONE
                val rotateAnimation = RotateAnimation(90, 0, centerX, centerY, depthZ, false)
                rotateAnimation.duration = duration
                rotateAnimation.fillAfter = true
                rotateAnimation.interpolator = DecelerateInterpolator()
                container.startAnimation(rotateAnimation)
            }
        })
    }
}