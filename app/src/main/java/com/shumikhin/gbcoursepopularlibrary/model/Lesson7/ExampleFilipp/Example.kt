package com.shumikhin.gbcoursepopularlibrary.model.Lesson7.ExampleFilipp

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject


class Example {

    //вставь сюда зависимость
    @Inject
    lateinit var a: String

}

//Несколько зависимостей которые предоставляются через @Provides
@Module
class ExampleModule {

    //Предоставляю зависимость
    @Provides
    //метод долен иметь тот же тип что и иньекция (@Inject, переменная - а)  что мы хотим получить.
    //Название не принципиально a() или exampleProvides()
    fun exampleProvides(): String {
        return "ABC"
    }

}

//Применяется на интерфейс
@Component(modules = [ExampleModule::class])
interface ExampleComponent {
    //прописываем методы которые прописывают зависимости
    //мы хотим прописать зависимость для класса Example, поэтому указываем в методе его
    fun inject(exampleInstance: Example)
}