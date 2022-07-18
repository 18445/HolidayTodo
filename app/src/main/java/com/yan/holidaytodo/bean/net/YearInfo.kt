package com.yan.holidaytodo.bean.net

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean.net
 * @ClassName:      YearInfo
 * @Author:         Yan
 * @CreateDate:     2022年07月18日 19:50:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    获取指定年份或年月份的所有节假日信息。默认返回当前年份的所有信息。
 */


/**
 * 接口地址: http://timor.tech/api/holiday/year/$date?type=Y&week=Y
 *
 * @params $date: 指定年份或年月份，格式 ‘2019-02’ ‘2019-2’ 或者 ‘2019’。可以省略，则默认服务器当前时间的年份。
 * @params type: 是否返回日期类型，默认不返回。可选值：’Y’ 返回，’N’ 不返回。
 * @params week: 节假日是否包含周末，默认不包含。可选值：’Y’ 包含周末，’N’ 不包含。
 * @return json: 返回指定年份或年月份的所有节假日，以日期作为key，格式：mm-dd。如果没有该年份或月份，则返回空对象。注意目前只配置了最多比当前时间往后一年的节假日。如果包含周末，则节日和周末冲突时，以节日为准。

    {
        "code": 0,               // 0服务正常。-1服务出错
        "holiday": {
        "10-01": {
        "holiday": true,     // 该字段一定为true
        "name": "国庆节",     // 节假日的中文名。
        "wage": 3,           // 薪资倍数，3表示是3倍工资
        "date": "2018-10-01" // 节假日的日期
    },
            "10-02": {
            "holiday": true,     // 该字段一定为true
            "name": "国庆节",     // 节假日的中文名。
            "wage": 3,           // 薪资倍数，3表示是3倍工资
            "date": "2018-10-01" // 节假日的日期
        }
    },
        "type": {                     // 只有明确指定参数 type=Y 时才返回类型信息
            "2018-10-01": {             // 一一对应holiday对象的key，holiday有多少个这里就有多少个
            "type": enum(0, 1, 2, 3), // 节假日类型，分别表示 工作日、周末、节日、调休。
            "name": "周六",            // 节假日类型中文名，可能值为 周一 至 周日、假期的名字、某某调休。
            "week": enum(1 - 7)       // 一周中的第几天。值为 1 - 7，分别表示 周一 至 周日。
            }
        }
    }


 */


data class YearInfo(
    val code: Int,
    val holiday: Map<String,HolidayData>
) {
    /**
     * 以下是自动生成的数据
     * 找了半天解决方法
     * 第一次遇到这种接口
     * 人真的麻了
     */
    /**
//    data class Holiday(
//        val datas : Map<String,Data>
//    ) {
//        data class Data(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0102(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0103(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0129(
//            val after: Boolean,
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val target: String,
//            val wage: Int
//        )
//
//        data class X0130(
//            val after: Boolean,
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val target: String,
//            val wage: Int
//        )
//
//        data class X0131(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0201(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0202(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0203(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0204(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0205(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0206(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0402(
//            val after: Boolean,
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val target: String,
//            val wage: Int
//        )
//
//        data class X0403(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0404(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0405(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0424(
//            val after: Boolean,
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val target: String,
//            val wage: Int
//        )
//
//        data class X0430(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0501(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0502(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0503(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0504(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0507(
//            val after: Boolean,
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val target: String,
//            val wage: Int
//        )
//
//        data class X0603(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0604(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0605(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0910(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0911(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X0912(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X1001(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X1002(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X1003(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X1004(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X1005(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X1006(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X1007(
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val rest: Int,
//            val wage: Int
//        )
//
//        data class X1008(
//            val after: Boolean,
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val target: String,
//            val wage: Int
//        )
//
//        data class X1009(
//            val after: Boolean,
//            val date: String,
//            val holiday: Boolean,
//            val name: String,
//            val target: String,
//            val wage: Int
//        )
//    }
*/
}

data class HolidayData(
    val date: String,
    val holiday: Boolean,
    val name: String,
)