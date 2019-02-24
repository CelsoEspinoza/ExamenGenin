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

    override fun obtainTasks(callback: TaskDataSource.TaskCallback) {
        taskLocalDataSource.obtainTasks(object : TaskDataSource.TaskCallback {
            override fun onSuccess(tasks: List<Task>) {
                callback.onSuccess(tasks)
            }

            override fun onFailure() {
                callback.onFailure()
            }
        })
    }

    override fun saveTasks(tasks: List<Task>) {
        taskLocalDataSource.saveTasks(tasks)
    }

    override fun saveLastId(lastId: Int) {
        taskLocalDataSource.saveLastId(lastId)
    }
}