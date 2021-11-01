package com.shumikhin.gbcoursepopularlibrary.model.cache

import com.shumikhin.gbcoursepopularlibrary.model.db.RoomGitHubUser
import com.shumikhin.gbcoursepopularlibrary.model.remote.GitHubUser
import io.reactivex.rxjava3.core.Single

interface IUsersCache {
    fun getCachedUsers(): Single<List<GitHubUser>>
    fun insertUsersToCache(users: List<RoomGitHubUser>)
}