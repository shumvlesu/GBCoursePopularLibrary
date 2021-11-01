package com.shumikhin.gbcoursepopularlibrary.model.cache

import com.shumikhin.gbcoursepopularlibrary.model.db.RoomGitHubRepository
import com.shumikhin.gbcoursepopularlibrary.model.db.RoomGitHubUser
import com.shumikhin.gbcoursepopularlibrary.model.remote.GitHubUser
import com.shumikhin.gbcoursepopularlibrary.retrofit.UserRepo
import io.reactivex.rxjava3.core.Single

interface IRepositoriesCache {
    fun getCachedRepositoriesByUser(user: GitHubUser): Single<List<UserRepo>>
    fun insertRepositoriesToCache(repos: List<RoomGitHubRepository>)
    fun getUserByLogin(text: String): RoomGitHubUser?
}