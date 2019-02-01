package com.example.examengenin.app.main

import com.example.examengenin.data.entity.Task
import com.example.examengenin.data.source.TaskDataSource
import com.example.examengenin.data.source.TaskRepository

class MainPresenter(
    private var view: MainContract.View?,
    private val taskRepository: TaskRepository
) : MainContract.Presenter {

    init {

        view?.presenter = this

    }

    override fun obtainLastId() {
        taskRepository.obtainLastId(object : TaskDataSource.TaskLastIdCallback {
            override fun onSuccess(lastId: Int) {
                view?.saveLastIdCache(lastId)
            }
        })
    }

    override fun addLocalTask(lastId: Int, tasks: List<Task>) {
        taskRepository.addLocalTask(lastId, tasks, object : TaskDataSource.TaskCallback {
            override fun onSuccess(tasks: List<Task>) {
                view?.showTaskInUI(tasks)
            }

            override fun onFailure() {
                view?.errorShowingTasks()
            }
        })
    }

    override fun obtainLocalTasks() {
        taskRepository.obtainLocalTasks(object : TaskDataSource.TaskCallback {
            override fun onSuccess(tasks: List<Task>) {
                view?.showTaskInUI(tasks)
            }

            override fun onFailure() {
                view?.errorShowingTasks()
            }
        })
    }

    override fun removeLocalTask(task: Task) {
        taskRepository.removeLocalTask(task, object : TaskDataSource.TaskRemoveCallback {
            override fun onSuccess(task: Task) {
                view?.removeTask(task)
            }

            override fun onFailure() {
                view?.errorRemovingTask()
            }
        })
    }

    override fun start() {
        obtainLastId()
        obtainLocalTasks()
    }

    override fun detach() {
        view = null
    }
}