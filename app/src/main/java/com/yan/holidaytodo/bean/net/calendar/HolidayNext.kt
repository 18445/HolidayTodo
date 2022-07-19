package com.yan.holidaytodo.bean.net.calendar

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean
 * @ClassName:      HolidayNext
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 14:46:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    获取指定日期的下一个节假日（如果在放假前有调休，也会返回）
 */


/*

接口地址: http://timor.tech/api/holiday/next/$date?type=Y&week=Y

@params $date: 指定日期的字符串，格式 ‘2018-02-23’。可以省略，则默认服务器的当前时间。
@params type: 是否返回日期类型，默认不返回。可选值：’Y’ 返回，’N’ 不返回。
@params week: 节假日是否包含周末，默认不包含。可选值：’Y’ 包含周末，’N’ 不包含。
@return json: 返回指定日期的下一个最近的节假日，如果在放假之前要调休，则会一起返回调休的信息。如果包含周末，则节日和周末冲突时，以节日为优先级。 比如特殊情况：周六是调休，则holiday返回周日，workday返回周六。其它情况以此类推，holiday永远会返回下一个节日。

 {
  "code": 0,              // 0服务正常。-1服务出错
  "holiday": {
    "holiday": true,      // 该字段一定为true
    "name": "国庆节",      // 节假日的中文名。
    "wage": 3,            // 薪资倍数，3表示是3倍工资
    "date": "2018-10-01"  // 节假日的日期
    "rest": 3             // 表示当前时间距离目标还有多少天。比如今天是 2018-09-28，距离 2018-10-01 还有3天
  },
  "workday": {             // 如果节假日前没调休，则此字段为null
    "holiday": false,      // 此字段一定为false
    "name": "国庆前调休",   // 调休的中文名
    "wage": 1,            // 薪资倍数，3表示是3倍工资
    "after": false,       // true表示放完假后调休，false表示先调休再放假
    "target": '国庆节',    // 表示调休的节假日
    "date": "2018-09-29"  // 表示要调休的日期
    "rest": 1             // 同上。表示当前时间距离目标还有多少天。
  },
  "type": {                     // 只有明确指定参数 type=Y 时才返回类型信息
    "holiday": {                // 对应 holiday 中的日期类型，只有返回 holiday 信息时才存在此日期类型
      "type": enum(0, 1, 2, 3), // 节假日类型，分别表示 工作日、周末、节日、调休。
      "name": "周六",            // 节假日类型中文名，可能值为 周一 至 周日、假期的名字、某某调休。
      "week": enum(1 - 7)       // 一周中的第几天。值为 1 - 7，分别表示 周一 至 周日。
    },
    "workday": {                // 对应 workday 中的日期类型，只有返回 workday 信息时才存在此日期类型
      "type": enum(0, 1, 2, 3), // 节假日类型，分别表示 工作日、周末、节日、调休。
      "name": "周六",            // 节假日类型中文名，可能值为 周一 至 周日、假期的名字、某某调休。
      "week": enum(1 - 7)       // 一周中的第几天。值为 1 - 7，分别表示 周一 至 周日。
    }
  }
}
* */

data class HolidayNext(
    val code: Int,
    val holiday: Holiday,
    val type: Type,
    val workday: Workday
) {
    data class Holiday(
        val date: String,
        val holiday: Boolean,
        val name: String,
        val rest: Int,
        val wage: Int
    )

    data class Type(
        val holiday: Holiday,
        val workday: Workday
    ) {
        data class Holiday(
            val name: String,
            val type: Int,
            val week: Int
        )

        data class Workday(
            val name: String,
            val type: Int,
            val week: Int
        )
    }

    data class Workday(
        val after: Boolean,
        val date: String,
        val holiday: Boolean,
        val name: String,
        val rest: Int,
        val target: String,
        val wage: Int
    )
}