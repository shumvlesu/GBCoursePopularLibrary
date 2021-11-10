package com.shumikhin.gbcoursepopularlibrary.model.Lesson7

import javax.inject.Inject

class ExampleClass {
    //@Inject указывает на метод, конструктор или поле класса, которые (или в которые) надо
    //что-то внедрить.
    @Inject
    lateinit var dependency: IDependency

    fun doSomethingWithDependency() {
        dependency.doSomething()
    }

}