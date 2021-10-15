package com.shumikhin.gbcoursepopularlibrary.presentation.detailsuser

import com.github.terrakok.cicerone.Router
import com.shumikhin.gbcoursepopularlibrary.model.GithubUser
import com.shumikhin.gbcoursepopularlibrary.view.ui.UserDetailsView
import moxy.MvpPresenter

class UserDetailsPresenter (private val router: Router, private val user: GithubUser) : MvpPresenter<UserDetailsView>() {

    fun setUserData () {
        user.id.let {name->
            name?.let { viewState.setUserName(it) }
        }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}