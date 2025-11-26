package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.TaskDatabase
import com.example.myapplication.repository.TaskRepository

class TaskApplication:Application() {
    val database by lazy{TaskDatabase.getDatabase(this)}
    val repository by lazy{TaskRepository(database.taskDao())}
}