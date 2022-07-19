package com.yan.holidaytodo.bean.net.calendar

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean
 * @ClassName:      DayInfo
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 13:43:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    获得指定日期的节假日信息
 */

/*
接口地址：http://timor.tech/api/holiday/batch?d=$date&type=Y
@params $date: 指定日期的字符串，格式 ‘2018-02-23’。可以省略，则默认服务器的当前时间。
@return json: 如果不是节假日，holiday字段为null。

{
  "code": 0,              // 0服务正常。-1服务出错
  "type": {
    "type": enum(0, 1, 2, 3), // 节假日类型，分别表示 工作日、周末、节日、调休。
    "name": "周六",         // 节假日类型中文名，可能值为 周一 至 周日、假期的名字、某某调休。
    "week": enum(1 - 7)    // 一周中的第几天。值为 1 - 7，分别表示 周一 至 周日。
  },
  "holiday": {
    "holiday": false,     // true表示是节假日，false表示是调休
    "name": "国庆前调休",  // 节假日的中文名。如果是调休，则是调休的中文名，例如'国庆前调休'
    "wage": 1,            // 薪资倍数，1表示是1倍工资
    "after": false,       // 只在调休下有该字段。true表示放完假后调休，false表示先调休再放假
    "target": '国庆节'     // 只在调休下有该字段。表示调休的节假日
  }
}
 */

data class DayInfo(
    val code: Int,
    val holiday: Holiday,
    val type: Type
) {
    data class Holiday(
        val after: Boolean,
        val holiday: Boolean,
        val name: String,
        val target: String,
        val wage: Int
    )

    data class Type(
        val name: String,
        val type: Int,
        val week: Int
    )
}