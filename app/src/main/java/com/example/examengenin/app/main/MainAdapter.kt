package com.example.examengenin.app.main

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.examengenin.R
import com.example.examengenin.data.entity.Task
import kotlinx.android.synthetic.main.row_items.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var listener: TaskListener? = null

    var tasks: ArrayList<Task> = arrayListOf()
    var currentTaskId = 0

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.row_items, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

        val task = tasks[p1]
        holder.bindTo(task)

    }

    fun setItems(items: List<Task>) {
        this.tasks.clear()
        this.tasks.addAll(items)
        notifyDataSetChanged()
    }

    fun addTask() {
        this.tasks.add(Task(++currentTaskId, "Task"))
        notifyDataSetChanged()
    }

    fun removeTask(task: Task) {

        tasks.remove(task)

        listener?.isEmpty(tasks.isEmpty())

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.m_btn_close.setOnClickListener {

                val task = tasks[adapterPosition]

                removeTask(task)

            }

        }

        @SuppressLint("SetTextI18n")
        fun bindTo(task: Task) {

            itemView.apply {
                m_text_todo_name.text = "${task.title}-${task.id}"
            }

        }
    }

    interface TaskListener {
        fun isEmpty(empty: Boolean)
    }
}