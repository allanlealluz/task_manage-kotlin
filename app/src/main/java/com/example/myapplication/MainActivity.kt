package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels  // ← ADICIONE ESTE IMPORT
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.adapter.TaskAdapter
import com.example.taskapp.viewmodel.TaskViewModel  // ← CORRIJA O IMPORT
import com.example.taskapp.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etTask: EditText = findViewById(R.id.edtTaskTitle)
        val btnAdd: Button = findViewById(R.id.btnAddTask)
        val recycler: RecyclerView = findViewById(R.id.rvTasks)

        // ← PASSE OS CALLBACKS NO CONSTRUTOR
        val adapter = TaskAdapter(
            onChecked = { viewModel.update(it) },
            onDelete = { viewModel.delete(it) }
        )

        recycler.adapter = adapter

        viewModel.allTasks.observe(this) {
            adapter.submitList(it)
        }

        btnAdd.setOnClickListener {
            val text = etTask.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.insert(text)
                etTask.setText("")
            }
        }
    }
}