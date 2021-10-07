package com.shumikhin.gbcoursepopularlibrary

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle


//@AddToEndSingle
@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView
