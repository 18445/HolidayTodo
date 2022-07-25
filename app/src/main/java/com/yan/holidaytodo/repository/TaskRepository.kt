package com.yan.holidaytodo.repository

import android.util.Log
import com.yan.common.network.ApiEmptyResponse
import com.yan.common.network.ApiResponse
import com.yan.common.network.ApiSuccessResponse
import com.yan.holidaytodo.base.BaseRepository
import com.yan.holidaytodo.bean.db.TaskDB
import com.yan.holidaytodo.bean.net.wanandroid.LoginInfo
import com.yan.holidaytodo.bean.net.wanandroid.RegisterInfo
import com.yan.holidaytodo.bean.net.wanandroid.TaskInfo
import com.yan.holidaytodo.bean.net.wanandroid.TaskList
import com.yan.holidaytodo.db.AppDatabase
import com.yan.holidaytodo.net.EmptyResponse
import com.yan.holidaytodo.net.SuccessResponse
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
        val response = executeTaskHttp {
            mTaskService.addTodo(title, content, date, type, priority)
        }
        if (response is ApiSuccessResponse){
            val task = response.data!!
            AppDatabase.INSTANCE.taskDao().insertAll(TaskDB(task.id,"",task.completeDateStr,task.content,task.date,task.dateStr,task.priority,task.status,task.title,task.type,task.userId))
        }
        return response
    }

    suspend fun updateTodo(id: Int,title: String,content: String, date : String,status: Int,type: Long,priority: Int = 0) : ApiResponse<TaskInfo>{
        val response =  executeTaskHttp {
            mTaskService.updateTodo(id, title, content, date, status, type, priority)
        }
        if (response is ApiSuccessResponse){
            val task = response.data!!
            AppDatabase.INSTANCE.taskDao().insertAll(TaskDB(task.id,"",task.completeDateStr,task.content,task.date,task.dateStr,task.priority,task.status,task.title,task.type,task.userId))
        }
        return response
    }

    suspend fun deleteTodo(id: Int){
        mTaskService.deleteTodo(id)
        AppDatabase.INSTANCE.taskDao().deleteTaskById(id.toString())
    }

    suspend fun queryTodoList(status: Int, type: Long, priority: Int, index: Int) : ApiResponse<TaskList>{
        return executeTaskHttp {
            mTaskService.queryTodoList(status, type, priority, index)
        }
    }

    suspend fun queryAllTodoList(index : Int) : ApiResponse<TaskList>{
        val response =  executeTaskHttp {
            mTaskService.queryTodoList(1,index)
        }
        if (response is ApiSuccessResponse){
            for(task in response.data!!.datas)
            AppDatabase.INSTANCE.taskDao().insertAll(TaskDB(task.id,"",task.completeDateStr,task.content,task.date,task.dateStr,task.priority,task.status,task.title,task.type,task.userId))
            return response
        }
        val localResponse = AppDatabase.INSTANCE.taskDao().getAllTasks()
        if(localResponse != null){
            val taskList = mutableListOf<TaskInfo>()
            for(task in localResponse){
                taskList.add(TaskInfo(task.id,task.completeDate,task.completeDateStr,task.content,task.date,task.dateStr,task.priority,task.status,task.title,task.type,task.userId))
            }
            return ApiSuccessResponse(TaskList(1,taskList,0,true,1,20,taskList.size))
        }
        return ApiEmptyResponse()
    }

}