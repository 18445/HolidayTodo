package com.yan.common.network


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.common.network
 * @ClassName:      ApiGenerator
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 13:17:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    提供retrofit
 */
object ApiGenerator {

    fun <T : Any> getApiService(clazz: KClass<T>,baseUrl : String): T {
        return getNewRetrofit(baseUrl) {}.create(clazz.java)
    }


    private fun getNewRetrofit(
        baseUrl: String,
        config: Retrofit.Builder.() -> Unit
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .apply { config.invoke(this) }
            .build()
    }

    /**
     * 获取 OkHttpClient
     */
    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS)//连接超时时间
            .writeTimeout(DEFAULT_WRITE_TIME, TimeUnit.SECONDS)//设置写操作超时时间
            .readTimeout(DEFAULT_READ_TIME, TimeUnit.SECONDS)//设置读操作超时时间
            .addInterceptor(getHttpLoggingInterceptor())

        return builder.build()
    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
        return logging
    }

}