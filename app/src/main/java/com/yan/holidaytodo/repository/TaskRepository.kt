package com.yan.holidaytodo.repository

import android.util.Log
import com.yan.common.network.ApiResponse
import com.yan.holidaytodo.base.BaseRepository
import com.yan.holidaytodo.bean.net.wanandroid.LoginInfo
import com.yan.holidaytodo.bean.net.wanandroid.RegisterInfo
import com.yan.holidaytodo.bean.net.wanandroid.TaskInfo
import com.yan.holidaytodo.bean.net.wanandroid.TaskList
import com.yan.holidaytodo.net.TaskService

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.repository
 * @ClassName:      TodoRepository
 * @Author:         Yan
 * @CreateDate:     2022年07月19日 22:11:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    任务有关的仓库层
 */
object TaskRepository : BaseRepository(){

    private val mTaskService by lazy {
        TaskService.Instance
    }

    suspend fun login(username : String,password:String) : ApiResponse<LoginInfo>{
        return executeTaskHttp {
            mTaskService.login(username, password)
        }
    }

    suspend fun register(username: String,password: String,repassword : String) : ApiResponse<RegisterInfo>{
        return executeTaskHttp {
            mTaskService.register(username,password,repassword)
        }
    }

    suspend fun exit(){
        mTaskService.exit()
    }

    suspend fun addTodo(title: String,content: String,date: String,type: Long,priority: Int = 0) : ApiResponse<TaskInfo>{
        return  executeTaskHttp {
            mTaskService.addTodo(title, content, date, type, priority)
        }
    }

    suspend fun updateTodo(id: Int,title: String,content: String, date : String,status: Int,type: Long,priority: Int = 0) : ApiResponse<TaskInfo>{
        return executeTaskHttp {
            mTaskService.updateTodo(id, title, content, date, status, type, priority)
        }
    }

    suspend fun deleteTodo(id: Int){
        mTaskService.deleteTodo(id)
    }

    suspend fun queryTodoList(status: Int, type: Long, priority: Int, index: Int) : ApiResponse<TaskList>{
        return executeTaskHttp {
            mTaskService.queryTodoList(status, type, priority, index)
        }
    }

    suspend fun queryAllTodoList(index : Int) : ApiResponse<TaskList>{
        return executeTaskHttp {
            mTaskService.queryTodoList(1,index)
        }
    }

}