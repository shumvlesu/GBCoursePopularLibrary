package com.shumikhin.gbcoursepopularlibrary

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.shumikhin.gbcoursepopularlibrary.databinding.ActivityMainBinding
import com.shumikhin.gbcoursepopularlibrary.screens.AndroidScreens
import com.shumikhin.gbcoursepopularlibrary.view.ui.BackButtonListener
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

//class MainActivity : AppCompatActivity(), MainView {
//Заменяем AppCompatActivity(), подключая активити к мокси MvpAppCompatActivity()
class MainActivity : MvpAppCompatActivity(), MainView {

    private var vb: ActivityMainBinding? = null
    //navigator берем из встроеного класса cicerone
    val navigator = AppNavigator(this, R.id.container)

    private val presenter by moxyPresenter {
        MainPresenter(
            App.instance.router,
            AndroidScreens()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb?.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
    }

    //из активити отсележиваем нажатие навигации назад
    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClicked()
    }

}
