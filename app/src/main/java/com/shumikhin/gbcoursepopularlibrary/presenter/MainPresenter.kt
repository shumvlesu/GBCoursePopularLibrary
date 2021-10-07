package com.shumikhin.gbcoursepopularlibrary.presenter

import com.github.terrakok.cicerone.Router
import com.shumikhin.gbcoursepopularlibrary.MainView
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter

class MainPresenter (private val router: Router) : MvpPresenter<MainView>() {

    private var disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun back() = router.exit()

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}