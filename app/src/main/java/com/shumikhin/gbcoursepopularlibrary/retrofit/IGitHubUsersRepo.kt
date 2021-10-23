package com.shumikhin.gbcoursepopularlibrary.retrofit

import com.shumikhin.gbcoursepopularlibrary.model.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGitHubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
    fun getUserRepos(url: String): Single<List<UserRepo>>
}