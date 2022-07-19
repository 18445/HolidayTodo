package com.yan.holidaytodo.bean.net.wanandroid

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean.net
 * @ClassName:      RegisterInfo
 * @Author:         Yan
 * @CreateDate:     2022年07月19日 22:45:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    登录信息
 */

data class RegisterInfo(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val coinCount: Int,
    val collectIds: List<Any>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)
