package com.shumikhin.gbcoursepopularlibrary.presentation

import com.github.terrakok.cicerone.Router
import com.shumikhin.gbcoursepopularlibrary.model.GithubUser
import com.shumikhin.gbcoursepopularlibrary.model.GithubUsersRepo
import com.shumikhin.gbcoursepopularlibrary.view.UserItemView
import com.shumikhin.gbcoursepopularlibrary.view.ui.UsersView
import moxy.MvpPresenter

class UsersPresenter(val usersRepo: GithubUsersRepo, val router: Router) : MvpPresenter<UsersView>() {
    
    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null
        override fun getCount() = users.size
        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
            //TODO: переход на экран пользователя c помощью router.navigateTo
        }
    }

    fun loadData() {
        val users = usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}
