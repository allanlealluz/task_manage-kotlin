package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.adapter.TaskAdapter
import com.example.taskapp.viewmodel.TaskViewModel
import com.example.taskapp.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            TaskViewModelFactory((application as TaskApplication).repository)
        )[TaskViewModel::class.java]

        val elTask: EditText = findViewById(R.id.edtTaskTitle)
        val btnAdd: Button = findViewById(R.id.btnAddTask)
        val recycler: RecyclerView = findViewById(R.id.rvTasks)

        val adapter = TaskAdapter(
            onChecked = { viewModel.update(it) },
            onDelete = { viewModel.delete(it) }
        )
        recycler.adapter = adapter

        viewModel.allTasks.observe(this) {
            adapter.submitList(it)
        }

        btnAdd.setOnClickListener {
            val text = elTask.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.insert(text)
                elTask.setText("")
            }
        }
    }
}
