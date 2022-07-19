package com.yan.holidaytodo.net

import com.yan.common.network.ApiGenerator
import com.yan.common.network.getHolidayBaseUrl
import retrofit2.http.*

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.net
 * @ClassName:      TodoService
 * @Author:         Yan
 * @CreateDate:     2022年07月19日 22:15:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    任务Api
 */
interface TodoService {

    companion object {
        val Instance by lazy {
            ApiGenerator.getApiService(TodoService::class, getHolidayBaseUrl())
        }
    }

    /**
     * 登录
     * https://www.wanandroid.com/user/login
     *
     * 方法：POST
     * 参数：username，password
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username : String,
        @Field("password") password : String,
    )

    /**
     * 注册
     * https://www.wanandroid.com/user/register
     *
     * 方法：POST
     * 参数：username,password,repassword
     */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username:String,
        @Field("password") password: String,
        @Field("repassword") repassword : String,
    )

    /**
     * 退出
     * https://www.wanandroid.com/user/logout/json
     *
     * 方法：GET
     */
    @GET
    suspend fun exit()

    /**
     *  新建一个TODO
     *  https://www.wanandroid.com/lg/todo/add/json
     *
     * 方法：POST
     * 参数：
     * title: 新增标题（必须）
     * content: 新增详情（必须）
     * date: 2018-08-01 预定完成时间（不传默认当天，建议传）
     * type: 大于0的整数（可选）；
     * priority 大于0的整数（可选）；
     *
     * type 可以用于，在app 中预定义几个类别：例如
     *
     * 工作1；
     * 生活2；
     * 娱乐3；
     * 新增的时候传入1，2，3，查询的时候，传入type 进行筛选；
     *
     * 如果不设置type则为 0，未来无法做 type=0的筛选，会显示全部（筛选 type 必须为大于 0 的整数）
     *
     * priority 主要用于定义优先级，在app 中预定义几个优先级：
     *
     * 重要（1）
     * 一般（2）等
     * 查询的时候，传入priority 进行筛选；
     *
     */
    @FormUrlEncoded
    @POST("lg/todo/add/json")
    suspend fun addTodo(
        @Field("title") title: String,
        @Field("content") content: String,
        @Field("date") date: String,
        @Field("type") type: Long,
        @Field("priority") priority: Int = 0
    )

    /**
     * 更新一个Todo
     *
     * https://www.wanandroid.com/lg/todo/update/83/json
     *
     * 方法：POST
     * 参数：
     * id: 拼接在链接上，为唯一标识，列表数据返回时，每个todo 都会有个id标识 （必须）
     * title: 更新标题 （必须）
     * content: 新增详情（必须）
     * date: 2018-08-01（必须）
     * status: 0 // 0为未完成，1为完成
     * type: ；
     * priority: ；
     *
     * 如果有当前状态没有携带，会被默认值更新，比如当前 todo status=1，更新时没有带上，会认为被重置。
     *
     * 注意：当更新 status=1时，会自动设置服务器当前时间为完成时间。
     */
    @FormUrlEncoded
    @POST("lg/todo/update/83/json")
    suspend fun updateTodo(
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("content") content: String,
        @Field("date") date : String,
        @Field("status") status: Int,
        @Field("type") type: Long,
        @Field("priority") priority: Int = 0,
    )

    /**
     * 删除一个TODO
     * https://www.wanandroid.com/lg/todo/delete/83/json
     *
     * 方法：POST
     * 参数：
     * id: 拼接在链接上，为唯一标识
     */
    @FormUrlEncoded
    @POST("lg/todo/delete/83/json")
    suspend fun deleteTodo(@Field("id") id: Int)

    /**
     * TODO列表
     * https://www.wanandroid.com/lg/todo/v2/list/页码/json
     * 页码从1开始，拼接在url 上
     * status 状态， 1-完成；0未完成; 默认全部展示；
     * type 创建时传入的类型, 默认全部展示
     * priority 创建时传入的优先级；默认全部展示
     * orderby 1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；
     * page 从1开始
     */
    @FormUrlEncoded
    @POST("lg/todo/v2/list/{index}/json")
    suspend fun queryTodoList(
        @Field("status") status: Int,
        @Field("type") type: Long,
        @Field("priority") priority: Int,
        @Path("index") index: Int
    )


}