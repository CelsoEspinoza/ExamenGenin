package com.example.examengenin.data.source

import com.example.examengenin.data.entity.Task

interface TaskDataSource {

    interface TaskCallback {
        fun onSuccess(tasks: List<Task>)
        fun onFailure()
    }
    fun obtainTasks(callback: TaskCallback)
    fun saveTasks(tasks: List<Task>)

    interface TaskLastIdCallback {
        fun onSuccess(lastId: Int)
    }
    fun obtainLastId(callback: TaskLastIdCallback)
    fun saveLastId(lastId: Int)

}