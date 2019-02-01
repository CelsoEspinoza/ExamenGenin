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

    private var lastId: Int? = null
    private var tasks: List<Task> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // TODO
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

            addTodoItem()

        }

    }

    private fun load() {

        presenter.start()

    }

    private fun addTodoItem() {

        if (lastId != null)
            presenter.addLocalTask(lastId!!, tasks)
        else
            showMessage("Intente nuevamente")

    }

    private fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun saveLastIdCache(lastId: Int) {

        this.lastId = lastId

    }

    override fun showTaskInUI(tasks: List<Task>) {
        this.tasks = tasks
        adapter.setItems(tasks)
    }

    override fun errorShowingTasks() {
        showMessage("No fue posible mostrar los tasks")
    }

    override fun removeTask(task: Task) {
        adapter.removeTask(task)
    }

    override fun errorRemovingTask() {
        showMessage("No fue posible remover el item")
    }

    companion object {
        fun newInstance() =
                MainFragment().apply {
                    arguments = Bundle().apply {
                        //TODO
                    }
                }
    }
}
