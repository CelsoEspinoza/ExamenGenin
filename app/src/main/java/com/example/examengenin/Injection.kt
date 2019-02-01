package com.example.examengenin

import android.content.Context
import com.example.examengenin.data.source.TaskRepository
import com.example.examengenin.data.source.local.TaskLocalDataSource
import com.example.examengenin.data.source.local.TaskLocalDataSource.Companion.KEY_TASK

object Injection {

    fun provideTaskRepository(context: Context): TaskRepository {
        val sharedPreferences = context.getSharedPreferences(KEY_TASK, Context.MODE_PRIVATE)
        return TaskRepository.getInstance(TaskLocalDataSource.getInstance(sharedPreferences))
    }

}