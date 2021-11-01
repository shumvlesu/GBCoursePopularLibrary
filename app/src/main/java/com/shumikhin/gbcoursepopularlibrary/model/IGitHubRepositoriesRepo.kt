package com.shumikhin.gbcoursepopularlibrary.model

import com.shumikhin.gbcoursepopularlibrary.model.remote.GitHubUser
import com.shumikhin.gbcoursepopularlibrary.retrofit.UserRepo
import io.reactivex.rxjava3.core.Single

interface IGitHubRepositoriesRepo {
    fun getRepositories(user: GitHubUser): Single<List<UserRepo>>
}