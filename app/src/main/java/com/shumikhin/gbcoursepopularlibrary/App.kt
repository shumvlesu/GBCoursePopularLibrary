package com.shumikhin.gbcoursepopularlibrary

import android.app.Application
import android.util.Log
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.shumikhin.gbcoursepopularlibrary.model.Lesson7.ExampleFilipp.DaggerExampleComponent
import com.shumikhin.gbcoursepopularlibrary.model.Lesson7.ExampleFilipp.DaggerExampleComponent2
import com.shumikhin.gbcoursepopularlibrary.model.Lesson7.ExampleFilipp.Example
import com.shumikhin.gbcoursepopularlibrary.model.Lesson7.ExampleFilipp.Example2
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
        Database.create(this)


        //Инициализируем компонент
        //DaggerExampleComponent не появится пока не сбилдится проект (даггер должен сгенерировать свой код)
        val exampleComponent = DaggerExampleComponent.builder().build()

        val example = Example()
        val example2 = Example()
        val example3 = Example()

        exampleComponent.inject(example)
        exampleComponent.inject(example2)
        exampleComponent.inject(example3)

        Log.d("DP_Example", "${example.a.exampleField}")
        Log.d("DP_Example", "${example2.a.exampleField}")
        Log.d("DP_Example", "${example3.a.exampleField}")
        //Благодаря аннотации @Singleton у нас не создается новый объект класса ExampleDependency а передается тот же уже созданный объект
        //2021-11-15 16:39:27.438 6923-6923/com.shumikhin.gbcoursepopularlibrary D/DP_Example: 0.04127815502235199
        //2021-11-15 16:39:27.438 6923-6923/com.shumikhin.gbcoursepopularlibrary D/DP_Example: 0.04127815502235199
        //2021-11-15 16:39:27.438 6923-6923/com.shumikhin.gbcoursepopularlibrary D/DP_Example: 0.04127815502235199



        val exampleComponent2 = DaggerExampleComponent2.builder().build()
        val exampleQualifier = Example2()
        exampleComponent2.inject(exampleQualifier)
        Log.d("DP_Example2", "${exampleQualifier.abc}")
        Log.d("DP_Example2", "${exampleQualifier.cde}")

    }

}