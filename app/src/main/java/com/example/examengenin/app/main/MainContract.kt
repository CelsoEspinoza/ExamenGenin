package com.example.examengenin.app.main

import com.example.examengenin.BasePresenter
import com.example.examengenin.BaseView
import com.example.examengenin.data.entity.Task

interface MainContract {

    interface View : BaseView<Presenter> {
        fun showTasksInUI(tasks: List<Task>, lastId: Int = 0)
        fun errorShowingTasks()
        fun showNoTasksAvailable(show: Boolean)
    }

    interface Presenter : BasePresenter {

        fun obtainLastId()
        fun obtainTasks(lastId: Int)
        fun saveTasks(tasks: List<Task>, lastId: Int)

    }

}