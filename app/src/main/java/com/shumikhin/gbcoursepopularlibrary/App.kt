package com.shumikhin.gbcoursepopularlibrary

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.shumikhin.gbcoursepopularlibrary.model.db.Database

//App нужен для чичероне и для синг активити
class App : Application() {

    //Эта переменная - instance, нужна что бы получить конкретный app где лежат конкретные navigatorHolder и router от чичероне
    companion object {
        lateinit var instance: App
    }

    //Временно до даггера положим это тут
    //Инициализируем чичероне
    private val cicerone: Cicerone<Router> by lazy { Cicerone.create() }

    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}