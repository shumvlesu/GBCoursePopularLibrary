package com.shumikhin.gbcoursepopularlibrary

import com.github.terrakok.cicerone.Router
import com.shumikhin.gbcoursepopularlibrary.screens.IScreens
import moxy.MvpPresenter


class MainPresenter(val router: Router, val screens: IScreens) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.users())
    }

    fun backClicked() {
        router.exit()
    }

}
