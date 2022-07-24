package com.yan.holidaytodo.bean.view

import android.util.Log
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean.view
 * @ClassName:      AnimPoint
 * @Author:         Yan
 * @CreateDate:     2022年07月24日 16:10:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    运动的粒子
 */
class AnimPoint : Cloneable{


    //粒子的坐标
    var pointX : Float = 0f
    var pointY : Float = 0f
    //粒子相关半径与移动属性
    var radius : Float = 0f
    private var anger : Double = 0.0
    //移动速度
    private var velocity : Float = 0f
    //移动帧数
    private var frame : Float = 0f
    //透明度 0~255
    var alpha : Int = 128
    //运动偏移角度
    private var randomAnger = 0.0

    companion object{
        private const val MAX_FRAME = 400
        private const val MAX_DISTANCE = 180
    }

    /**
     * 重新初始化粒子属性表
     */
    fun reInitPoint(random: Random, viewRadius: Int){
        anger = Math.toRadians(random.nextInt(360).toDouble())
        velocity = random.nextFloat() * 2f
        radius = random.nextInt(6) * 1f + 5
        pointX = (viewRadius * cos(anger)).toFloat()
        pointY = (viewRadius * sin(anger)).toFloat()
        randomAnger = Math.toRadians(30.0 - random.nextInt(60))
        alpha = 128 + random.nextInt(127)
    }

    /**
     * 计算粒子的移动
     */
    fun calPoint(random: Random, viewRadius: Int){
        val distance = 1f
        val moveAnger = anger + randomAnger
        pointX = (pointX - distance * cos(moveAnger) * velocity).toFloat()
        pointY = (pointY - distance * sin(moveAnger) * velocity).toFloat()
        radius -= 0.02f * velocity
        frame ++
        if(velocity * frame > MAX_DISTANCE || frame > MAX_FRAME){
            frame = 0f
            reInitPoint(random,viewRadius)
        }
    }


    /**
     * 防止重复创建对象申请内存，直接clone对象
     */
    public override fun clone(): AnimPoint {
        return super.clone() as AnimPoint
    }

}