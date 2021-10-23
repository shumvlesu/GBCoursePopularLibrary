package com.shumikhin.gbcoursepopularlibrary.presentation.detailsuser

import android.util.Log
import com.github.terrakok.cicerone.Router
import com.shumikhin.gbcoursepopularlibrary.model.GithubUser
import com.shumikhin.gbcoursepopularlibrary.retrofit.IGitHubUsersRepo
import com.shumikhin.gbcoursepopularlibrary.retrofit.UserRepo
import com.shumikhin.gbcoursepopularlibrary.view.ui.UserDetailsView
import com.shumikhin.gbcoursepopularlibrary.view.ui.detailsuser.IRepoListPresenter
import com.shumikhin.gbcoursepopularlibrary.view.ui.detailsuser.RepoItemView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter

class UserDetailsPresenter(
    private val router: Router,
    private val user: GithubUser,
    private val usersRepo: IGitHubUsersRepo
) : MvpPresenter<UserDetailsView>() {

    class RepoListPresenter : IRepoListPresenter {
        val userRepos = mutableListOf<UserRepo>()
        override var itemClickListener: ((RepoItemView) -> Unit)? = null
        override fun bindView(view: RepoItemView) {
            val repo = userRepos[view.pos]
            repo.name?.let { view.setRepoName(it) }
        }

        override fun getCount(): Int {
            return userRepos.size
        }
    }

    fun setUserData() {
//        user.id.let {name->
//            name?.let { viewState.setUserName(it) }
//        }
        user.let { name ->
            name.let { viewState.setUserName(it) }
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
    }


    val repoListPresenter = RepoListPresenter()

    private fun loadData() {
        val userReposR = usersRepo.getUserRepos("/users/${user.id}/repos")
        userReposR
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                repoListPresenter.userRepos.apply {
                    clear()
                    addAll(it)
                }
                viewState.updateList()
            },
                {
                    Log.e("UsersPresenter", "Ошибка получения репозиториев пользователя!", it)
                })
    }


}