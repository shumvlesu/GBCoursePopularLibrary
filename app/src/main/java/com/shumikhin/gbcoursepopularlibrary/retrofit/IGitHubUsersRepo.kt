package com.shumikhin.gbcoursepopularlibrary.retrofit

import com.shumikhin.gbcoursepopularlibrary.model.remote.GitHubUser
import io.reactivex.rxjava3.core.Single

interface IGitHubUsersRepo {
    fun getUsers(): Single<List<GitHubUser>>
    fun getUserRepos(url: String): Single<List<UserRepo>>
}