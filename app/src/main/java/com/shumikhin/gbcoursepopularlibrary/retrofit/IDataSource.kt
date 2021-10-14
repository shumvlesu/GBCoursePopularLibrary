package com.shumikhin.gbcoursepopularlibrary.retrofit

import com.shumikhin.gbcoursepopularlibrary.model.GithubUser
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

//GithubUsersService по уроку
interface IDataSource {
    @GET("/users")
    fun getUsers(): Single<List<GithubUser>>
}