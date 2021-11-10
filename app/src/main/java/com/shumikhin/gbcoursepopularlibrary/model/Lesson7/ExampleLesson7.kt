package com.shumikhin.gbcoursepopularlibrary.model.Lesson7

class ExampleLesson7 {

    val appComponent = DaggerAppComponent.builder().build()
    val exampleInstance = ExampleClass()
    appComponent.inject(exampleInstance)

}