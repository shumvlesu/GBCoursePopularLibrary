package com.shumikhin.gbcoursepopularlibrary.view.ui

import com.shumikhin.gbcoursepopularlibrary.model.GithubUser
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle


@AddToEndSingle
//@StateStrategyType(AddToEndSingleStrategy::class)
interface UserDetailsView : MvpView {
    //fun setUserName(name: String)
    fun setUserName(name: GithubUser)

    fun init()
    fun updateList()
}