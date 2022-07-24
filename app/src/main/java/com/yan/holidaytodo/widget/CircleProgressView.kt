package com.yan.holidaytodo.widget

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.View
import android.view.animation.LinearInterpolator
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.view.AnimPoint
import com.yan.holidaytodo.bean.view.ProgressParameter
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*


/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      CircleProgressView
 * @Author:         Yan
 * @CreateDate:     2022年07月24日 15:36:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    CircleProgressView
 */


@SuppressLint("NewApi", "ViewConstructor")
class CircleProgressView  constructor(
    context: Context,
    private val parentWidth : Int,
    private val parentHeight : Int,
    private val outerShaderWidth : Int,//阴影宽度
    private val circleStrokeWidth : Int,//外边缘宽度
) : View(context) {

    companion object{
        private const val TOTAL_POINTS = 100
    }

    private val mRandom: Random = Random()

    //粒子列表
    private val mPointList : ArrayList<AnimPoint> = ArrayList(TOTAL_POINTS)
    //粒子画笔
    private val mPointPaint = Paint()
    //扇形粒子区域的路径
    private val mArcPath = Path()
    //圆环属性
    private val mCenterX = parentWidth / 2
    private val mCenterY = parentHeight / 2

    //半径和角度
    private val mRadius by lazy {
        mCenterX - outerShaderWidth - circleStrokeWidth / 2
    }
    private var mCurrentAngle = 0f

    //圆环进度 0~3600
    private var lastTimeProgress = 0
    private var currentProgress = 0

    //圆环动画
    private lateinit var progressAnim : ValueAnimator
    //变色动画
    private lateinit var mOutCircleAnim : ValueAnimator
    //圆环动画
    private lateinit var mPointsAnimator: ValueAnimator
    //倒计时动画
    private lateinit var downTimeAnim : ValueAnimator

    //画笔
    private val mBackCirclePaint : Paint = Paint()
    private val mOutCirclePaint : Paint = Paint()
    private val mBackShadePaint : Paint = Paint()
    private val mSweepPaint : Paint= Paint()
    private val mBmpPaint : Paint= Paint()

    //圆环颜色变化范围
    private var mRadialGradientColors: IntArray = IntArray(6)
    private val mRadialGradientStops = floatArrayOf(0f, 0.69f, 0.86f, 0.94f, 0.98f, 1f)
    //圆环渐变色
    private lateinit var mSweepGradient : SweepGradient
    private lateinit var mRadialGradient: RadialGradient

    //计时器
    private lateinit var mTimeDisposable : Disposable
    //目标图层
    private lateinit var mBitmapDST : Bitmap
    //源图层
    private lateinit var mBitmapSRT : Bitmap
    //混合区域
    private lateinit var mPointerRectF : RectF
    //混合模式
    private lateinit var mXfermode : PorterDuffXfermode
    //LayoutId
    private var mPointerLayoutId = 0
    //指针颜色
    private var mIndicatorColor = 0
    //指针是否可见
    private var mPointerVisible = VISIBLE
    //进度动画时间
    private var mAnimDuration = 1500

    var countMode : Boolean = false
    private var pointMode : Boolean = false
    private var keepWare : Boolean = false
    private var mOutCircleAnger : Float = 0f
    /**
     * 温度环的颜色属性
     */
    private val mLinearGradientColor1 = getContext().getColor(R.color.progress_linearGradient_color1)
    private val mLinearGradientColor2 = getContext().getColor(R.color.progress_linearGradient_color2)

    private val insideColor1 = getContext().getColor(R.color.progress_inside_color1)
    private val outsizeColor1 = getContext().getColor(R.color.progress_outsize_color1)
    private val progressColor1 = getContext().getColor(R.color.progress_progress_color1)
    private val pointColor1 = getContext().getColor(R.color.progress_point_color1)
    private val bgCircleColor1 = getContext().getColor(R.color.progress_bg_circle_color1)
    private val indicatorColor1 = getContext().getColor(R.color.progress_indicator_color1)

    private val insideColor2 = getContext().getColor(R.color.progress_inside_color2)
    private val outsizeColor2 = getContext().getColor(R.color.progress_outsize_color2)
    private val progressColor2 = getContext().getColor(R.color.progress_progress_color2)
    private val pointColor2 = getContext().getColor(R.color.progress_point_color2)
    private val bgCircleColor2 = getContext().getColor(R.color.progress_bg_circle_color2)
    private val indicatorColor2 = getContext().getColor(R.color.progress_indicator_color2)

    private val insideColor3 = getContext().getColor(R.color.progress_inside_color3)
    private val outsizeColor3 = getContext().getColor(R.color.progress_outsize_color3)
    private val progressColor3 = getContext().getColor(R.color.progress_progress_color3)
    private val pointColor3 = getContext().getColor(R.color.progress_point_color3)
    private val bgCircleColor3 = getContext().getColor(R.color.progress_bg_circle_color3)
    private val indicatorColor3 = getContext().getColor(R.color.progress_indicator_color3)

    private val insideColor4 = getContext().getColor(R.color.progress_inside_color4)
    private val outsizeColor4 = getContext().getColor(R.color.progress_outsize_color4)
    private val progressColor4 = getContext().getColor(R.color.progress_progress_color4)
    private val pointColor4 = getContext().getColor(R.color.progress_point_color4)
    private val bgCircleColor4 = getContext().getColor(R.color.progress_bg_circle_color4)
    private val indicatorColor4 = getContext().getColor(R.color.progress_indicator_color4)

    private val insideColor5 = getContext().getColor(R.color.progress_inside_color5)
    private val outsizeColor5 = getContext().getColor(R.color.progress_outsize_color5)
    private val progressColor5 = getContext().getColor(R.color.progress_progress_color5)
    private val pointColor5 = getContext().getColor(R.color.progress_point_color5)
    private val bgCircleColor5 = getContext().getColor(R.color.progress_bg_circle_color5)
    private val indicatorColor5 = getContext().getColor(R.color.progress_indicator_color5)

    private val insideColor6 = getContext().getColor(R.color.progress_inside_color_6)
    private val outsizeColor6 = getContext().getColor(R.color.progress_outsize_color_6)
    private val progressColor6 = getContext().getColor(R.color.progress_progress_color_6)
    private val pointColor6 = getContext().getColor(R.color.progress_point_color_6)
    private val bgCircleColor6 = getContext().getColor(R.color.progress_bg_circle_color_6)
    private val indicatorColor6 = getContext().getColor(R.color.progress_indicator_color_6)

    private val mParameter1 : ProgressParameter = ProgressParameter(0,insideColor1,outsizeColor1,progressColor1,pointColor1,bgCircleColor1,indicatorColor1)
    private val mParameter2 : ProgressParameter = ProgressParameter(0,insideColor2,outsizeColor2,progressColor2,pointColor2,bgCircleColor2,indicatorColor2)
    private val mParameter3 : ProgressParameter = ProgressParameter(0,insideColor3,outsizeColor3,progressColor3,pointColor3,bgCircleColor3,indicatorColor3)
    private val mParameter4 : ProgressParameter = ProgressParameter(0,insideColor4,outsizeColor4,progressColor4,pointColor4,bgCircleColor4,indicatorColor4)
    private val mParameter5 : ProgressParameter = ProgressParameter(0,insideColor5,outsizeColor5,progressColor5,pointColor5,bgCircleColor5,indicatorColor5)
    private val mParameter6 : ProgressParameter = ProgressParameter(0,insideColor6,outsizeColor6,progressColor6,pointColor6,bgCircleColor6,indicatorColor6)


    init {
        initView()
        initPaint()
        initBitmap()
        initAnim()
    }
    /**
     * 初始化粒子动画效果
     */
    private fun initAnim(){
        mPointList.clear()
        val mAnimPoint = AnimPoint()
        for(i in 0 until TOTAL_POINTS){
            mAnimPoint.clone().apply {
                reInitPoint(mRandom,mRadius - circleStrokeWidth/2)
                mPointList.add(this)
            }
        }
        mPointsAnimator = ValueAnimator.ofFloat(0F,1F).apply {
            duration = Long.MAX_VALUE
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                for (point in mPointList){
                    point.calPoint(mRandom,mRadius)
                }
                invalidate()
            }
        }
        mPointsAnimator.start()
        mOutCircleAnim = ValueAnimator.ofFloat(-90f, 270f).apply {
            duration = 4000
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { animation: ValueAnimator ->
                mOutCircleAnger = animation.animatedValue as Float
            }
        }
    }

    /**
     * 初始化View相关
     */
    private fun initView(){
        mIndicatorColor = indicatorColor1
        val transparentColor = Color.TRANSPARENT
        mRadialGradientColors[0] = transparentColor
        mRadialGradientColors[1] = transparentColor
        mRadialGradientColors[4] = transparentColor
        mRadialGradientColors[5] = transparentColor
    }

    /**
     * 初始化画笔相关
     */
    private fun initPaint(){
        val mLinearGradientColors = intArrayOf(
            mLinearGradientColor2, mLinearGradientColor2,
            mLinearGradientColor1, mLinearGradientColor1,
            mLinearGradientColor2, mLinearGradientColor2)
        val mLinearGradientPositions = floatArrayOf(0f, 0.05f, 0.12f, 0.88f, 0.95f, 1f)

        mSweepGradient = SweepGradient(0f,0f,mLinearGradientColors,mLinearGradientPositions)
        mSweepPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE
            mRadialGradient = RadialGradient(0f,0f,mCenterX.toFloat(),mRadialGradientColors,mRadialGradientStops,Shader.TileMode.CLAMP)
            shader = mRadialGradient
        }

        mOutCirclePaint.apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = circleStrokeWidth.toFloat()
        }

        mPointPaint.apply {
            //羽化效果
            maskFilter = BlurMaskFilter(1F,BlurMaskFilter.Blur.NORMAL)
        }

        mBackCirclePaint.apply {
            isAntiAlias = true
            strokeWidth = circleStrokeWidth.toFloat()
            style = Paint.Style.STROKE
        }

        mBackShadePaint.apply {
            isAntiAlias = true
            color = bgCircleColor1
        }

    }

    private fun initBitmap(){
        mBitmapDST = BitmapFactory.decodeResource(resources,R.drawable.indicator)
        val mBitmapDstHeight: Float = parentWidth * (130 / 656f)
        val mBitmapDstWidth = mBitmapDstHeight * mBitmapDST.width / mBitmapDST.height
        mXfermode = PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)
        mPointerRectF = RectF(0f, 0f,mBitmapDstWidth,mBitmapDstHeight)
        mBitmapSRT = Bitmap.createBitmap(mBitmapDstWidth.toInt(), mBitmapDstHeight.toInt(),Bitmap.Config.ARGB_8888)
        mBitmapSRT.eraseColor(mIndicatorColor)
    }


    /**
     * 绘制扇形
     */
    private fun getSectorClip(r: Float,startAngle : Float, sweepAngle : Float){
        mArcPath.reset()
        mArcPath.addArc(-r,-r,r,r,startAngle,sweepAngle)
        mArcPath.lineTo(0f,0f)
        mArcPath.close()
    }


    /**
     * 根据不同颜色值返回不同ProgressParameter
     *
     * @param progressValue 0~3600
     */
    private fun getProgressParameter(progressValue : Float) : ProgressParameter{

        val parameter = if(progressValue < 360){
            mParameter1
        }else if(progressValue < 720){
            mParameter2
        }else if(progressValue < 1080){
            mParameter3
        }else if(progressValue < 1440){
            mParameter4
        }else if(progressValue < 1800){
            mParameter5
        }else{
            mParameter6
        }
        return parameter
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画底色圆
        canvas.drawCircle(mCenterX.toFloat(),mCenterY.toFloat(),mRadius.toFloat(),mBackCirclePaint)
        //画扇形运动
        canvas.save()
        canvas.translate(mCenterX.toFloat(),mCenterY.toFloat())
        if(!keepWare){
            canvas.clipPath(mArcPath)
        }
        //画运动粒子
        for (animPoint in mPointList) {
            mPointPaint.alpha = animPoint.alpha
            canvas.drawCircle(animPoint.pointX, animPoint.pointY, animPoint.radius, mPointPaint)
        }
        //画渐变色圆
        canvas.drawCircle(0f, 0f, mCenterX.toFloat(), mSweepPaint)
        //画外层圆环
        if (keepWare) {
            canvas.save()
            canvas.rotate(mOutCircleAnger)
            canvas.drawCircle(0f, 0f, mRadius.toFloat(), mOutCirclePaint)
            canvas.restore()
        } else {
            canvas.drawCircle(0f, 0f, mRadius.toFloat(), mOutCirclePaint)
        }
        canvas.restore()
        //画指针
        if (!keepWare) {
            if (mPointerVisible == VISIBLE) {
                canvas.translate(mCenterX.toFloat(), mCenterY.toFloat())
                canvas.rotate(mCurrentAngle / 10f)
                canvas.translate(-mPointerRectF.width() / 2, (-mCenterY).toFloat())
                mPointerLayoutId = canvas.saveLayer(mPointerRectF, mBmpPaint)
                mBitmapSRT.eraseColor(mIndicatorColor)
                canvas.drawBitmap(mBitmapDST, null, mPointerRectF, mBmpPaint)
                mBmpPaint.xfermode = mXfermode
                canvas.drawBitmap(mBitmapSRT, null, mPointerRectF, mBmpPaint)
                mBmpPaint.xfermode = null
                canvas.restoreToCount(mPointerLayoutId)
            }
        }


    }

    fun setCountMode(second : Int) {
        lastTimeProgress = 0
        currentProgress = 0
        mAnimDuration = 1000
        disposeTimer()
        countMode = true
        pointMode = false
        if (this::downTimeAnim.isInitialized) {
            downTimeAnim.cancel()
            downTimeAnim.removeAllListeners()
        }
        downTimeAnim = ValueAnimator.ofFloat(1F,0F).apply {
            if(second < 1){
                return
            }
            duration = (second - 1) * 1000L
            interpolator = LinearInterpolator()
            addUpdateListener {
                setCurrentProgress(second * it.animatedValue as Float,second.toFloat())
            }
        }
        downTimeAnim.start()
    }

    fun setWareMode(){
        countMode = false
        keepWare = true
        //设置外环颜色渐变
        mOutCirclePaint.shader = mSweepGradient
        //变更进度条的颜色值
        mPointPaint.color = pointColor5
        mOutCirclePaint.color = progressColor5
        mBackCirclePaint.color = bgCircleColor5
        //设置内圈变色圆的shader
        mRadialGradientColors[2] = insideColor5
        mRadialGradientColors[3] = outsizeColor5
        mRadialGradient = RadialGradient(0f, 0f, mCenterX.toFloat(), mRadialGradientColors, mRadialGradientStops, Shader.TileMode.CLAMP)
        mSweepPaint.shader = mRadialGradient
        //开始外圈变色旋转动画
        mOutCircleAnim.start()
        invalidate()
    }

    fun setCurrentProgress(current : Float,target : Float){
        if (this::downTimeAnim.isInitialized && downTimeAnim.isRunning && !countMode) {
            downTimeAnim.cancel()
        }
        keepWare = false
        mOutCirclePaint.shader = null
        if(this::progressAnim.isInitialized && progressAnim.isRunning){
            progressAnim.cancel()
        }
        if (current < target) {
            mPointerVisible = VISIBLE
        }
        currentProgress = (current / target * 3600).toInt()
        progressAnim = ValueAnimator.ofFloat(lastTimeProgress.toFloat(), currentProgress.toFloat()).apply {
            duration = mAnimDuration.toLong()
            addUpdateListener {
                val value = it.animatedValue as Float
                mCurrentAngle = value % 3600
                val parameter = getProgressParameter(value)
                mPointPaint.color = parameter.pointColor
                mOutCirclePaint.color = parameter.progressColor
                mBackCirclePaint.color = parameter.bgCircleColor
                mIndicatorColor = parameter.indicatorColor

                mRadialGradientColors[2] = parameter.insideColor
                mRadialGradientColors[3] = parameter.outsideColor
                mRadialGradient = RadialGradient(0f,0f,mCenterX.toFloat(),mRadialGradientColors,mRadialGradientStops,Shader.TileMode.CLAMP)
                mSweepPaint.shader = mRadialGradient
                getSectorClip(parentWidth / 2f, -90f ,value / 10f)
            }
            addListener (object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    lastTimeProgress = currentProgress
                    if(mCurrentAngle >= 3600){
                        setPointerVisible(GONE)
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                    lastTimeProgress = currentProgress
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

            })
        }
        progressAnim.start()

    }

    /**
     * 设置指针透明度
     * VISIBLE/GONE
     */
    private fun setPointerVisible(visible : Int){
        if (visible == mPointerVisible) {
            return
        }
        if (visible == GONE) {
            mPointerVisible = GONE
        }
    }

    /**
     * 解绑计时器
     */
    private fun disposeTimer() {
        if (this::mTimeDisposable.isInitialized && !mTimeDisposable.isDisposed) {
            mTimeDisposable.dispose()
        }
    }

}