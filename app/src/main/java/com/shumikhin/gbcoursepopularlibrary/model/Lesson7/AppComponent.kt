package com.shumikhin.gbcoursepopularlibrary.model.Lesson7

import dagger.Component

// @Component отмечает интерфейс, который позже используется для генерации кода. В нём
//определяется, куда что-либо внедрять, а также методы для прямого доступа к зависимостям.
@Component(modules = [DependencyModule::class])
interface AppComponent {
    fun inject(exampleInstance: ExampleClass)
}