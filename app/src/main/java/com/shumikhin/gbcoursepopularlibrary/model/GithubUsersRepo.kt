package com.shumikhin.gbcoursepopularlibrary.model

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.toObservable

//репозиторий с фиктивными данными, которым будем пользоваться, пока не
//реализуем получение данных из сети
class GithubUsersRepo {

    private val repositories = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

//    fun getUsers() : List<GithubUser> {
//        return repositories
//    }

    fun getUsers(): Observable<List<Any>> {
        //fromIterable, реализованый RxKotlin, похож на just, но в него передаётся не набор, а коллекция элементов
        return repositories.toObservable()
    }

}