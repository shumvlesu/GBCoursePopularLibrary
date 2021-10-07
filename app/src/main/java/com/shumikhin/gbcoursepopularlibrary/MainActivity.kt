package com.shumikhin.gbcoursepopularlibrary

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.shumikhin.gbcoursepopularlibrary.App.Navigation.router
import com.shumikhin.gbcoursepopularlibrary.databinding.ActivityMainBinding
import com.shumikhin.gbcoursepopularlibrary.presenter.ConvertScreen
import com.shumikhin.gbcoursepopularlibrary.presenter.MainPresenter
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private var vb: ActivityMainBinding? = null
   // private val vb: ActivityMainBinding by viewBinding()

//     val presenter by moxyPresenter { MainPresenter(router) }
//     val navigator = AppNavigator(this, R.id.container)

//    override fun onResumeFragments() {
//        super.onResumeFragments()
//        navigatorHolder.setNavigator(navigator)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        navigatorHolder.removeNavigator()
//    }
//
//    override fun onBackPressed() {
//        supportFragmentManager.fragments.forEach {
//            if (it is BackButtonListener && it.backPressed()) {
//                return
//            }
//        }
//        presenter.back()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)
        router.replaceScreen(ConvertScreen().create())
    }

}