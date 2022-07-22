package com.yan.holidaytodo.util

import android.graphics.Camera
import android.view.animation.Animation
import android.view.animation.Transformation

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.util
 * @ClassName:      RotateAnimation
 * @Author:         Yan
 * @CreateDate:     2022年07月23日 02:20:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    自定义旋转动画
 */
class RotateAnimation(private val mFromDegrees : Int, private val mEndDegrees : Int,
                      private val mCenterX : Int,private val mCenterY : Int,
                      private val mDepthZ : Int,private val mReverse : Boolean ) : Animation() {

    private lateinit var mCamera : Camera

    //初始化
    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
        mCamera = Camera()
    }

    //自定义动画
    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val degrees = mFromDegrees + ((mEndDegrees - mFromDegrees) * interpolatedTime)
        mCamera.save()
        val matrix = t.matrix
        if(mReverse){
            mCamera.translate(0f,0f,mDepthZ * interpolatedTime)
        }else{
            mCamera.translate(0f,0f,mDepthZ * (1.0f - interpolatedTime))
        }
        mCamera.rotateY(degrees)
        mCamera.getMatrix(matrix)
        mCamera.restore()
        //旋转中心移到控件中心
        matrix.preTranslate((-mCenterX).toFloat(), (-mCenterY).toFloat())
        matrix.postTranslate(mCenterX.toFloat(), mCenterY.toFloat())
        super.applyTransformation(interpolatedTime, t)
    }


}