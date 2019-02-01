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

    private var items: ArrayList<Task> = arrayListOf()
    var listener: TaskListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.row_items, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

        val task = items[p1]
        holder.bindTo(task)

    }

    fun setItems(items: List<Task>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addTodoListItem(lastId: Int) {

        val newId = lastId + 1

        items.add(Task(newId, "TODO $newId"))

        notifyDataSetChanged()

    }

    fun removeTask(task: Task) {

        items.remove(task)

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.m_btn_close.setOnClickListener {

                val task = itemView.tag as Task

                listener?.removeTask(task)

            }

        }

        @SuppressLint("SetTextI18n")
        fun bindTo(task: Task) {

            itemView.apply {
                tag = task

                m_text_todo_name.text = task.title + "$id"
            }

        }
    }

    interface TaskListener {
        fun removeTask(task: Task)
    }
}