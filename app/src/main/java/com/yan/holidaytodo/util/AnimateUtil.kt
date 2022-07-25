package com.yan.holidaytodo.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.util
 * @ClassName:      AnimateUtil
 * @Author:         Yan
 * @CreateDate:     2022年07月25日 22:58:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    运动动画
 */



fun doAnimateOpen(view : View, index : Int, total : Int, radius : Int,time : Long){
    if (view.visibility != View.VISIBLE){
        view.visibility = View.VISIBLE
    }
    val degree = Math.toRadians(90.0)/(total - 1) * index
    val translationX = radius * sin(degree).toFloat()
    val translationY = - radius * cos(degree).toFloat()
    AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(view,"translationX",0f,translationX),
            ObjectAnimator.ofFloat(view,"translationY",0f,translationY),
            ObjectAnimator.ofFloat(view,"scaleX",0f,1f),
            ObjectAnimator.ofFloat(view,"scaleY",0f,1f),
            ObjectAnimator.ofFloat(view,"alpha",0f,1f),
        )
        duration = time
    }.start()
}

fun doAnimateClose(view : View, index : Int, total : Int, radius: Int,time : Long){
    if (view.visibility != View.VISIBLE){
        view.visibility = View.VISIBLE
    }
    val degree = Math.toRadians(90.0)/(total - 1) * index
    val translationX = radius * sin(degree).toFloat()
    val translationY = - radius * cos(degree).toFloat()
    AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(view,"translationX",translationX,0f),
            ObjectAnimator.ofFloat(view,"translationY",translationY,0f),
            ObjectAnimator.ofFloat(view,"scaleX",1f,0f),
            ObjectAnimator.ofFloat(view,"scaleY",1f,0f),
            ObjectAnimator.ofFloat(view,"alpha",1f,0f),
        )
        duration = time
    }.start()
}