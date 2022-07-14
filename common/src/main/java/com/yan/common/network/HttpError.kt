package com.yan.common.network

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.common.network
 * @ClassName:      HttpError
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 13:12:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    错误处理类
 */

//返回的不同类型抛出不同结果
enum class HttpError(var code: Int, var errorMsg: String) {
    TOKEN_EXPIRE(3001, "token is expired"),
    PARAMS_ERROR(4003, "params is error")
}