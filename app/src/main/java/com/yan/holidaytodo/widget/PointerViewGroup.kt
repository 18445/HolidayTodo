package com.yan.holidaytodo.widget

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.animation.BounceInterpolator
import android.widget.LinearLayout
import com.yan.holidaytodo.util.calcOffset

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      PointerViewGroup
 * @Author:         Yan
 * @CreateDate:     2022年07月24日 01:04:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    随手势滑动 ViewGroup
 */
class PointerViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes){

    companion object{

        private const val MAX_DEGREE = 20
        private const val DURATION = 1000

        private const val propertyNameRotateX = "mCanvasRotateX"
        private const val propertyNameRotateY = "mCanvasRotateY"

    }

    private lateinit var mSteadyAnim : ValueAnimator

    private val mCenterX by lazy {
        width / 2f
    }
    private val mCenterY by lazy {
        height / 2f
    }

    private var mCanvasRotateX = 0f
    private var mCanvasRotateY = 0f

    private val mMatrixCanvas = Matrix()
    private val mCamera = Camera()

    override fun dispatchDraw(canvas: Canvas) {

        mMatrixCanvas.reset()
        mCamera.save()
        mCamera.rotateX(mCanvasRotateX)
        mCamera.rotateY(mCanvasRotateY)
        mCamera.getMatrix(mMatrixCanvas)
        mCamera.restore()
        mMatrixCanvas.preTranslate(-mCenterX,-mCenterY)
        mMatrixCanvas.postTranslate(mCenterX,mCenterY)
        canvas.save()
        canvas.setMatrix(mMatrixCanvas)
        super.dispatchDraw(canvas)
        canvas.restore()

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                cancelSteadyAnimIfNeed()
                rotateCanvasWhenMove(x,y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                rotateCanvasWhenMove(x,y)
                return true
            }
            MotionEvent.ACTION_UP -> {
                startNewSteadyAnim()
                return true
            }
        }

        return super.onTouchEvent(event)
    }

    private fun cancelSteadyAnimIfNeed(){
        if(this::mSteadyAnim.isInitialized){
            Log.e("mSteadyAnim","beCanceld")
            mSteadyAnim.cancel()
        }
    }

    private fun startNewSteadyAnim(){
        val holderRotateX = PropertyValuesHolder.ofFloat(propertyNameRotateX,mCanvasRotateX,0f)
        val holderRotateY = PropertyValuesHolder.ofFloat(propertyNameRotateY,mCanvasRotateY,0f)
        mSteadyAnim = ValueAnimator.ofPropertyValuesHolder(holderRotateX,holderRotateY).apply {
            duration = DURATION.toLong()
            interpolator = BounceInterpolator()
            addUpdateListener {
                mCanvasRotateX = it.getAnimatedValue(propertyNameRotateX) as Float
                mCanvasRotateY = it.getAnimatedValue(propertyNameRotateY) as Float
                invalidate()
            }
        }
        mSteadyAnim.start()
    }

    private fun rotateCanvasWhenMove(positionX : Float,positionY: Float){
        val dx = positionX - mCenterX
        val dy = positionY - mCenterY

        val percentX = calcOffset(dx / (width / 2),-1f,1f)
        val percentY = calcOffset(dy / (height / 2),-1f,1f)

        mCanvasRotateY = MAX_DEGREE * percentX
        mCanvasRotateX = -(MAX_DEGREE * percentY)

        invalidate()
    }



}