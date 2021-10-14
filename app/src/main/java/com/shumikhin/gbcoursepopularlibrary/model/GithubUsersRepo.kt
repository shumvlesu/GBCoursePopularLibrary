package com.shumikhin.gbcoursepopularlibrary.model


import com.shumikhin.gbcoursepopularlibrary.retrofit.ApiHolder
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.toObservable

//репозиторий с фиктивными данными, которым будем пользоваться, пока не
//реализуем получение данных из сети
class GithubUsersRepo {

//    private val repositories = listOf(
//        GithubUser("login1"),
//        GithubUser("login2"),
//        GithubUser("login3"),
//        GithubUser("login4"),
//        GithubUser("login5")
//    )

//    fun getUsers(): Observable<GithubUser> {
//        //fromIterable, реализованый RxKotlin, похож на just, но в него передаётся не набор, а коллекция элементов
//        return repositories.toObservable()
//    }

    fun getUsers() = ApiHolder.apiService.getUsers()


}