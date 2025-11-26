package com.example.myapplication.repository

import com.example.myapplication.data.TaskDao
import com.example.myapplication.data.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    val allTasks: Flow<List<TaskEntity>> = dao.getAllTasks()

    suspend fun insert(task:TaskEntity){
        dao.insert(task)
    }

    suspend fun update(task:TaskEntity){
        dao.update(task)
    }

    suspend fun delete(task: TaskEntity){
        dao.delete(task)
    }
}