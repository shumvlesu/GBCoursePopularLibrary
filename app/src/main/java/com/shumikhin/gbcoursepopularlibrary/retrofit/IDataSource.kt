package com.shumikhin.gbcoursepopularlibrary.retrofit

import com.shumikhin.gbcoursepopularlibrary.model.GithubUser
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

//GithubUsersService по уроку
interface IDataSource {
    @GET("/users")
    fun getUsers(): Single<List<GithubUser>>

    //запрос на список репозиториев пользователя
    @GET
    fun getUserRepos(@Url url: String): Single<List<UserRepo>>
}