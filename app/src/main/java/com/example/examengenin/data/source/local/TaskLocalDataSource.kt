package com.example.examengenin.data.source.local

import android.content.SharedPreferences
import com.example.examengenin.BuildConfig
import com.example.examengenin.data.entity.Task
import com.example.examengenin.data.source.TaskDataSource
import com.example.examengenin.util.fromJson
import com.google.gson.Gson
import java.lang.Exception

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

    private fun getLocalTasks(): List<Task>? {
        val tasksString = sharedPreferences.getString(KEY_TASK, "[]")
        return if (tasksString != null) {
            try {
                Gson().fromJson<List<Task>>(tasksString)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    override fun obtainTasks(callback: TaskDataSource.TaskCallback) {
        val tasks = getLocalTasks()
        if (tasks != null)
            callback.onSuccess(tasks)
        else
            callback.onFailure()
    }

    override fun saveTasks(tasks: List<Task>) {
        val editor = sharedPreferences.edit()

        val tasksString = Gson().toJson(tasks)
        editor.putString(KEY_TASK, tasksString)

        editor.apply()
    }

    override fun saveLastId(lastId: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_LAST_ID, lastId)
        editor.apply()
    }
}