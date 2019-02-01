package com.example.examengenin.app.main

import com.example.examengenin.BasePresenter
import com.example.examengenin.BaseView
import com.example.examengenin.data.entity.Task

interface MainContract {

    interface View : BaseView<Presenter> {
        fun showTaskInUI(tasks: List<Task>)
        fun errorShowingTasks()
        fun saveLastIdCache(lastId: Int)
        fun removeTask(task: Task)
        fun errorRemovingTask()
    }

    interface Presenter : BasePresenter {

        fun obtainLastId()
        fun obtainLocalTasks()
        fun addLocalTask(lastId: Int, tasks: List<Task>)
        fun removeLocalTask(task: Task)

    }

}