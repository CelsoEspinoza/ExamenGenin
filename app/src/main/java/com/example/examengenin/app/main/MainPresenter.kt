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
                obtainTasks(lastId)
            }
        })
    }

    override fun obtainTasks(lastId: Int) {
        taskRepository.obtainTasks(object : TaskDataSource.TaskCallback {
            override fun onSuccess(tasks: List<Task>) {
                view?.showNoTasksAvailable(tasks.isEmpty())
                view?.showTasksInUI(tasks, lastId)
            }

            override fun onFailure() {
                view?.errorShowingTasks()
            }
        })
    }

    override fun saveTasks(tasks: List<Task>, lastId: Int) {
        taskRepository.saveTasks(tasks)
        saveLastId(lastId)
    }

    private fun saveLastId(lastId: Int) {
        taskRepository.saveLastId(lastId)
    }

    override fun start() {
        obtainLastId()
    }

    override fun detach() {
        view = null
    }
}