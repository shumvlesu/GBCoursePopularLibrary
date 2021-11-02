package com.shumikhin.gbcoursepopularlibrary.view.ui

import com.shumikhin.gbcoursepopularlibrary.model.remote.GitHubUser
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle


@AddToEndSingle
//@StateStrategyType(AddToEndSingleStrategy::class)
interface UserDetailsView : MvpView {
    //fun setUserName(name: String)
    fun setUserName(name: GitHubUser)

    fun init()
    fun updateList()
}