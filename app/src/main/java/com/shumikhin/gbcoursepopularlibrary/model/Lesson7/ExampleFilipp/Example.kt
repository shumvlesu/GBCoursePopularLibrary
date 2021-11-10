package com.shumikhin.gbcoursepopularlibrary.model.Lesson7.ExampleFilipp

import dagger.Module
import dagger.Provides
import javax.inject.Inject


class Example {

    //вставь сюда зависимость
    @Inject
    lateinit var a:String

}

//Несколько зависимостей которые предоставляются через @Provides
@Module
class ExampleModule{

    //Предоставляю зависимость
    @Provides
    fun a(): String{//метод долен иметь тот же тип что и иньекция (@Inject) что мы хотим получить.

    }

}
