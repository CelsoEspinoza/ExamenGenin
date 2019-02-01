package com.example.examengenin.data.source

import com.example.examengenin.data.entity.Task

interface TaskDataSource {

    interface TaskCallback {
        fun onSuccess(tasks: List<Task>, newId: Int = 0)
        fun onFailure()
    }

    interface TaskLastIdCallback {
        fun onSuccess(lastId: Int)
    }

    interface TaskRemoveCallback {
        fun onSuccess(task: Task, tasks: List<Task>)
        fun onFailure()
    }

    fun obtainLastId(callback: TaskLastIdCallback)

    fun addLocalTask(lastId: Int, tasks: List<Task>, callback: TaskCallback)
    fun obtainLocalTasks(callback: TaskCallback)
    fun removeLocalTask(task: Task, callback: TaskRemoveCallback)

}