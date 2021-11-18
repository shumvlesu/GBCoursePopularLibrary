package com.shumikhin.gbcoursepopularlibrary.di.modules.ExampleFilipp

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

//Пример с вызовом внедренной зависимости прописан в App.kt , методе onCreate
class Example {

    //вставь сюда зависимость
    @Inject
    //lateinit var a: String
    lateinit var a: ExampleDependency

}

//1) Модуль который мы потом поместим в компонент даггера
//Несколько зависимостей которые предоставляются через @Provides
@Module
class ExampleModule {

    //Предоставляю зависимость
    @Provides
    @Singleton //Что бы ExampleDependency() не создавался каждый раз новый, указываем один из ключей scope - @Singleton

    //метод долен иметь тот же тип что и иньекция (@Inject, переменная - а)  что мы хотим получить.
    //Название не принципиально a() или exampleProvides()
    fun exampleProvides(): ExampleDependency {
        //return "ABC"
        return ExampleDependency()
    }

}

//2)Компонент который мы заинжектим в класс Example в App
//Применяется на интерфейс
@Component(modules = [ExampleModule::class])
@Singleton
interface ExampleComponent {
    //прописываем методы которые прописывают зависимости
    //мы хотим прописать зависимость для класса Example, поэтому указываем в методе его
    fun inject(exampleInstance: Example)
}


//Это просто класс, который в место строковой переменной в примере ранее, мы теперь передаем в зависимости
class ExampleDependency{
    val exampleField = Math.random()
}