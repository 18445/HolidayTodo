package com.yan.common.network

import com.airbnb.lottie.BuildConfig

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.common.network
 * @ClassName:      Api
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 13:19:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    基础BaseUrl
 */

const val DEBUG_URL = "https://timor.tech"
const val RELEASE_URL = DEBUG_URL
const val DEFAULT_CONNECT_TIME = 10L
const val DEFAULT_WRITE_TIME = 30L
const val DEFAULT_READ_TIME = 30L

fun getBaseUrl() = if (BuildConfig.DEBUG) DEBUG_URL else RELEASE_URL