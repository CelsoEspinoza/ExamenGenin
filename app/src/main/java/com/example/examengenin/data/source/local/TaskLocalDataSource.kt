package com.example.examengenin.data.source.local

import android.content.SharedPreferences
import com.example.examengenin.BuildConfig
import com.example.examengenin.data.entity.Task
import com.example.examengenin.data.source.TaskDataSource
import com.example.examengenin.util.fromJson
import com.google.gson.Gson

class TaskLocalDataSource(
        private val sharedPreferences: SharedPreferences
) : TaskDataSource {

    companion object {

        const val KEY_TASK = "${BuildConfig.APPLICATION_ID}:TASK"
        const val KEY_LAST_ID = "${BuildConfig.APPLICATION_ID}:KEY_LAST_ID"

        private var INSTANCE: TaskLocalDataSource? = null

        @JvmStatic fun getInstance(sharedPreferences: SharedPreferences)
            = INSTANCE ?: TaskLocalDataSource(sharedPreferences).apply { INSTANCE = this }

    }

    override fun obtainLastId(callback: TaskDataSource.TaskLastIdCallback) {
        val id = sharedPreferences.getInt(KEY_LAST_ID, 0)
        callback.onSuccess(id)
    }

    override fun addLocalTask(lastId: Int, tasks: List<Task>, callback: TaskDataSource.TaskCallback) {
        val editor = sharedPreferences.edit()

        val tasksToSave = ArrayList<Task>()
        tasksToSave.addAll(tasks)
        val newId = lastId + 1
        tasksToSave.add(Task(newId, "TODO $newId"))

        val tasksString = Gson().toJson(tasksToSave)
        editor.putString(KEY_TASK, tasksString)
        editor.putInt(KEY_LAST_ID, newId)

        editor.apply()

        callback.onSuccess(tasksToSave, newId)
    }

    private fun getLocalTasks(): List<Task>? {
        val tasksString = sharedPreferences.getString(KEY_TASK, "")
        return if (tasksString != null && tasksString != "") {
            Gson().fromJson(tasksString)
        } else {
            null
        }
    }

    override fun obtainLocalTasks(callback: TaskDataSource.TaskCallback) {
        val tasks = getLocalTasks()
        if (tasks != null)
            callback.onSuccess(tasks)
        else
            callback.onFailure()
    }

    override fun removeLocalTask(task: Task, callback: TaskDataSource.TaskRemoveCallback) {
        val tasks = getLocalTasks()
        if (tasks != null) {
            val array = ArrayList<Task>()
            array.addAll(tasks)
            array.remove(task)

            if (array.size != tasks.size) {

                val editor = sharedPreferences.edit()

                val tasksString = Gson().toJson(array)
                editor.putString(KEY_TASK, tasksString)

                editor.apply()

                callback.onSuccess(task, array)

            } else {
                callback.onFailure()
            }
        } else {
            callback.onFailure()
        }
    }
}