package com.shumikhin.gbcoursepopularlibrary.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.shumikhin.gbcoursepopularlibrary.App
import com.shumikhin.gbcoursepopularlibrary.databinding.FragmentUsersBinding
import com.shumikhin.gbcoursepopularlibrary.model.RetrofitGitHubUsersRepo
import com.shumikhin.gbcoursepopularlibrary.model.cache.UsersCache
import com.shumikhin.gbcoursepopularlibrary.model.db.Database
import com.shumikhin.gbcoursepopularlibrary.presentation.UsersPresenter
import com.shumikhin.gbcoursepopularlibrary.retrofit.ApiHolder
import com.shumikhin.gbcoursepopularlibrary.utils.AndroidNetworkStatus
import com.shumikhin.gbcoursepopularlibrary.view.ui.images.GlideImageLoader
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {

    private var vb: FragmentUsersBinding? = null
    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            //GithubUsersRepo(ApiHolder.apiService),
            //Теперь, отсюда мы проверяем есть связь и если связь есть кешируем данные (заносим в БД) , если нет тянем из кэша (БД)
            RetrofitGitHubUsersRepo(ApiHolder.apiService, AndroidNetworkStatus(requireContext()), UsersCache(
                Database.getInstance())),
            App.instance.router
        )
    }
    private var adapter: UsersRVAdapter? = null

    companion object {
        fun newInstance() = UsersFragment()
    }

    //В функции init() инициализируем адаптер и передаём туда Presenter
    //списка, а в функции updateList() командуем адаптеру обновить список.
    override fun init() {
        //vb?.rvUsers?.layoutManager = LinearLayoutManager(context)
        vb?.rvUsers?.layoutManager = LinearLayoutManager(requireContext())
        adapter = UsersRVAdapter(presenter.usersListPresenter, GlideImageLoader())
        vb?.rvUsers?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUsersBinding.inflate(inflater, container, false).also { vb = it }.root

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

}
