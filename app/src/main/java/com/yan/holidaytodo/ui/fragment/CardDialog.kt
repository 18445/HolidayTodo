package com.yan.holidaytodo.ui.fragment


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.yan.common.extension.toast
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.net.wanandroid.TaskInfo
import com.yan.holidaytodo.util.RotateAnimation


class CardDialog(
    context: Context,
    private val mTaskInfo: TaskInfo,
    private val onTaskChange: (taskInfo: TaskInfo, changedTitle: String, changedContent: String) -> Unit,
) : Dialog(context) {

    private lateinit var mRootView: View
    private lateinit var mContainer: ConstraintLayout
    private lateinit var mOpenAnimation: RotateAnimation
    private lateinit var mCloseAnimation: RotateAnimation

    private lateinit var mBackView: LinearLayout
    private lateinit var mFrontView: LinearLayout
    private lateinit var mFrontClicker: ImageView
    private lateinit var mBackClicker: ImageView

    private lateinit var mFrontTitle: TextView
    private lateinit var mFrontCreateTime: TextView
    private lateinit var mFrontContent: TextView

    private lateinit var mBackTitle: EditText
    private lateinit var mBackContent: EditText
    private lateinit var mSaveButton: MaterialButton


    private var centerX = 0
    private var centerY = 0
    private val depthZ = 800
    private val duration = 350L
    private var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //去掉系统的黑色矩形边框
        window!!.setBackgroundDrawableResource(R.drawable.ic_card_bg_window)
        window!!.setWindowAnimations(R.style.dialog_animation)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        initView()
    }

    private fun initView() {
        val inflater = LayoutInflater.from(context)
        mRootView = inflater.inflate(R.layout.dialog_card, null)
        setContentView(mRootView)
        val dialogWindow = window
        val lp = dialogWindow!!.attributes
        val d = context.resources.displayMetrics // 获取屏幕宽、高用
        lp.width = (d.widthPixels * 0.8).toInt() // 高度设置为屏幕的0.8
        lp.height = (d.heightPixels * 0.6).toInt() // 高度设置为屏幕的0.6
        dialogWindow.attributes = lp
        mContainer = findViewById(R.id.csl_dialog_container)
        mBackView = findViewById(R.id.back)
        mFrontView = findViewById(R.id.front)
        setCanceledOnTouchOutside(false)
        setCancelable(true)
        initOpenAnim()
        initCloseAnim()
        initFrontView()
        initBackView()
        mFrontClicker = mRootView.findViewById<ImageView>(R.id.iv_card_more).apply {
            setOnClickListener {
                startAnimation()
            }
        }
        mBackClicker = mRootView.findViewById<ImageView>(R.id.iv_card_back).apply {
            setOnClickListener {
                startAnimation()
            }
        }
    }

    private fun initFrontView() {
        mFrontTitle = findViewById<TextView?>(R.id.tv_card_front_title).apply {
            text = mTaskInfo.title
        }
        mFrontCreateTime = findViewById<TextView?>(R.id.tv_card_front_create_time).apply {
            text = mTaskInfo.dateStr
        }
        mFrontContent = findViewById<TextView?>(R.id.tv_card_front_content).apply {
            text = mTaskInfo.content
        }
    }

    private fun initBackView() {
        mBackTitle = findViewById(R.id.et_card_title)
        mBackContent = findViewById(R.id.et_card_content)
        mSaveButton = findViewById<MaterialButton?>(R.id.btn_card_save).apply {
            setOnClickListener {
                if (mBackTitle.text.isEmpty()) {
                    toast("标题不可为空")
                    return@setOnClickListener
                }
                if (mBackContent.text.isEmpty()) {
                    toast("内容不可为空")
                    return@setOnClickListener
                }
                onTaskChange(mTaskInfo, mBackTitle.text.toString(), mBackContent.text.toString())
            }
        }
    }

    private fun startAnimation() {
        centerX = mContainer.width / 2
        centerY = mContainer.height / 2

        //用作判断当前点击事件发生时动画是否正在执行
        if (mOpenAnimation.hasStarted() && !mOpenAnimation.hasEnded()) {
            return
        }
        if (mCloseAnimation.hasStarted() && !mCloseAnimation.hasEnded()) {
            return
        }

        //判断动画执行
        if (isOpen) {
            mContainer.startAnimation(mOpenAnimation)
        } else {
            mContainer.startAnimation(mCloseAnimation)
        }
        isOpen = !isOpen
    }

    /**
     * 卡牌文本介绍打开效果：注意旋转角度
     */
    private fun initOpenAnim() {
        //从0到90度，顺时针旋转视图，此时reverse参数为true，达到90度时动画结束时视图变得不可见，
        mOpenAnimation = RotateAnimation(0, 90, centerX, centerY, depthZ, true)
        mOpenAnimation.duration = duration
        mOpenAnimation.fillAfter = true
        mOpenAnimation.interpolator = AccelerateInterpolator()
        mOpenAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                mBackView.visibility = View.VISIBLE
                mFrontView.visibility = View.GONE
                val rotateAnimation = RotateAnimation(270, 360, centerX, centerY, depthZ, false)
                rotateAnimation.duration = duration
                rotateAnimation.fillAfter = true
                rotateAnimation.interpolator = DecelerateInterpolator()
                mContainer.startAnimation(rotateAnimation)
            }
        })
    }

    /**
     * 卡牌文本介绍关闭效果：旋转角度与打开时逆行即可
     */
    private fun initCloseAnim() {
        mCloseAnimation = RotateAnimation(360, 270, centerX, centerY, depthZ, true)
        mCloseAnimation.duration = duration
        mCloseAnimation.fillAfter = true
        mCloseAnimation.interpolator = AccelerateInterpolator()
        mCloseAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                mBackView.visibility = View.GONE
                mFrontView.visibility = View.VISIBLE
                val rotateAnimation = RotateAnimation(90, 0, centerX, centerY, depthZ, false)
                rotateAnimation.duration = duration
                rotateAnimation.fillAfter = true
                rotateAnimation.interpolator = DecelerateInterpolator()
                mContainer.startAnimation(rotateAnimation)
            }
        })
    }
}