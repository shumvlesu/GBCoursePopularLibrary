package com.shumikhin.gbcoursepopularlibrary

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.shumikhin.gbcoursepopularlibrary.databinding.ActivityMainBinding
import com.shumikhin.gbcoursepopularlibrary.model.GithubUsersRepo
import com.shumikhin.gbcoursepopularlibrary.view.ui.UsersRVAdapter
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

//class MainActivity : AppCompatActivity(), MainView {
//Заменяем AppCompatActivity(), подключая активити к мокси MvpAppCompatActivity()
class MainActivity : MvpAppCompatActivity(), MainView {

    private val presenter by moxyPresenter { MainPresenter(GithubUsersRepo()) }
    private var adapter: UsersRVAdapter? = null
    private var vb: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)
    }

    //В функции init() инициализируем адаптер и передаём туда Presenter
    //списка, а в функции updateList() командуем адаптеру обновить список.
    override fun init() {
        vb?.rvUsers?.layoutManager = LinearLayoutManager(this)
        adapter = UsersRVAdapter(presenter.usersListPresenter)
        vb?.rvUsers?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

}