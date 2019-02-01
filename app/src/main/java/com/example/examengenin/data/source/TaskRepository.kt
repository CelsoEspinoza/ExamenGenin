package com.example.examengenin.data.source

import com.example.examengenin.data.entity.Task

class TaskRepository(
        private val taskLocalDataSource: TaskDataSource
) : TaskDataSource {

    companion object {

        private var INSTANCE: TaskRepository? = null

        @JvmStatic fun getInstance(taskLocalDataSource: TaskDataSource)
            = INSTANCE ?: TaskRepository(taskLocalDataSource).apply { INSTANCE = this }
    }

    override fun obtainLastId(callback: TaskDataSource.TaskLastIdCallback) {
        taskLocalDataSource.obtainLastId(object : TaskDataSource.TaskLastIdCallback {
            override fun onSuccess(lastId: Int) {
                callback.onSuccess(lastId)
            }
        })
    }

    override fun addLocalTask(lastId: Int, tasks: List<Task>, callback: TaskDataSource.TaskCallback) {
        taskLocalDataSource.addLocalTask(lastId, tasks, object : TaskDataSource.TaskCallback {
            override fun onSuccess(tasks: List<Task>) {
                callback.onSuccess(tasks)
            }

            override fun onFailure() {
                callback.onFailure()
            }
        })
    }

    override fun obtainLocalTasks(callback: TaskDataSource.TaskCallback) {
        taskLocalDataSource.obtainLocalTasks(object : TaskDataSource.TaskCallback {
            override fun onSuccess(tasks: List<Task>) {
                callback.onSuccess(tasks)
            }

            override fun onFailure() {
                callback.onFailure()
            }
        })
    }

    override fun removeLocalTask(task: Task, callback: TaskDataSource.TaskRemoveCallback) {
        taskLocalDataSource.removeLocalTask(task, object : TaskDataSource.TaskRemoveCallback {
            override fun onSuccess(task: Task) {
                callback.onSuccess(task)
            }

            override fun onFailure() {
                callback.onFailure()
            }
        })
    }
}