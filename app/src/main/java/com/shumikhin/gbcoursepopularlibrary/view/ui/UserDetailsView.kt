package com.shumikhin.gbcoursepopularlibrary.view.ui

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType


//@AddToEndSingle
@StateStrategyType(AddToEndSingleStrategy::class)
interface UserDetailsView : MvpView {
    fun setUserName(name: String)
}