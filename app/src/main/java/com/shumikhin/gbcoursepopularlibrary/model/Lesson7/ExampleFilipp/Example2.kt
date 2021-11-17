package com.shumikhin.gbcoursepopularlibrary.model.Lesson7.ExampleFilipp

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

//Пример с вызовом внедренной зависимости прописан в App.kt , методе onCreate

//Нередко нам требуется предоставлять несколько разных экземпляров одного и того же класса для
//разных целей. Например, часто это происходит со строками: url точки доступа к api, различные
//локальные пути и т. д.
class Example2 {

    //вставь сюда зависимость
    @Inject
    //То имя что дали зависимости в ExampleModule2 нужно дать и полю
    @Named("String1")
    lateinit var abc: String

    @Inject
    @Named("String2")
    lateinit var cde: String

}

//Несколько зависимостей которые предоставляются через @Provides
@Module
class ExampleModule2 {

    @Provides
    //ставим аннтоацию Named которая позволяет делать несколько методов для внедрения зависимостей
    @Named("String1")
    fun exampleProvides(): String {
        return "ABC"
    }

    @Provides
    @Named("String2")
    fun exampleProvides2(): String {
        return "CDE"
    }

}

//2)Компонент который мы заинжектим в класс Example в App
//Применяется на интерфейс
@Component(modules = [ExampleModule2::class])
interface ExampleComponent2 {
    fun inject(exampleInstance: Example2)
}
