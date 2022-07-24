package com.yan.holidaytodo.bean.view

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean.view
 * @ClassName:      ProgressParameter
 * @Author:         Yan
 * @CreateDate:     2022年07月24日 18:29:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    颜色属性
 */
data class ProgressParameter (

     val progress : Int,//当前进度
     val insideColor : Int,//内发光
     val outsideColor : Int,//外发光
     val progressColor : Int,//进度条颜色
     val pointColor : Int,//粒子颜色
     val bgCircleColor : Int,//粒子背景色
     val indicatorColor : Int,//指针颜色

)