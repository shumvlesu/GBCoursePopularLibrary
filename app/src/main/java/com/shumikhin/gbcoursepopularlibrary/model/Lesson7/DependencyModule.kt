package com.shumikhin.gbcoursepopularlibrary.model.Lesson7

import dagger.Module
import dagger.Provides

//@Module помечает класс с набором методов, которые отмечаются аннотациями @Provides.
@Module
class DependencyModule {
    // @Provides указывает на метод, который предоставляет (возвращает) зависимость для
    //дальнейшего внедрения.
    @Provides
    fun dependency(): IDependency {
        return ExampleDependency()
    }

}