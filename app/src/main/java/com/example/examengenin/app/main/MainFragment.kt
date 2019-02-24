package com.example.examengenin.app.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.examengenin.Injection
import com.example.examengenin.R
import com.example.examengenin.data.entity.Task
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), MainContract.View, MainAdapter.TaskListener {

    private val adapter = MainAdapter()
                                override lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // none
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        context?.let {
            MainPresenter(this, Injection.provideTaskRepository(it))
        }

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()

        load()

    }

    private fun init() {

        adapter.listener = this
        m_recycler_items.adapter = adapter
        m_recycler_items.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        m_floating.setOnClickListener {

            addTask()

        }

    }

    private fun load() {

        presenter.start()

    }

    private fun addTask() {

        adapter.addTask()

        showNoTasksAvailable(false)

    }

    private fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showTasksInUI(tasks: List<Task>, lastId: Int) {

        adapter.currentTaskId = lastId

        adapter.setItems(tasks)

    }

    override fun errorShowingTasks() {
        showMessage("No fue posible mostrar los tasks")
    }

    override fun showNoTasksAvailable(show: Boolean) {
        m_text_no_tasks.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun isEmpty(empty: Boolean) {
        showNoTasksAvailable(empty)
    }

    override fun onPause() {
        super.onPause()

        presenter.saveTasks(adapter.tasks, adapter.currentTaskId)
    }

    companion object {
        fun newInstance() =
                MainFragment().apply {
                    arguments = Bundle().apply {
                        // none
                    }
                }
    }
}
