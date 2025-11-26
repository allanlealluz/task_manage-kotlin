package com.example.myapplication.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.TaskEntity

class TaskAdapter(
    private val onChecked: (TaskEntity) -> Unit,
    private val onDelete: (TaskEntity) -> Unit
) : ListAdapter<TaskEntity, TaskAdapter.TaskViewHolder>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // ← ID corrigido aqui para coincidir com o XML (tvTaskTitle)
        private val title: TextView = itemView.findViewById(R.id.tvTaskTitle)
        private val check: CheckBox = itemView.findViewById(R.id.cbTaskCompleted)
        private val delete: ImageButton = itemView.findViewById(R.id.btnDeleteTask)

        fun bind(task: TaskEntity) {

            title.text = task.title
            check.isChecked = task.isCompleted

            // Atualiza estilo do texto
            title.paintFlags =
                if (task.isCompleted)
                    title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                else
                    title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            // Evitar callback duplicado
            check.setOnCheckedChangeListener(null)

            check.setOnCheckedChangeListener { _, isChecked ->
                onChecked(task.copy(isCompleted = isChecked))
            }

            delete.setOnClickListener {
                onDelete(task)
            }
        }
    }

    class Diff : DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity) =
            oldItem == newItem
    }
}
