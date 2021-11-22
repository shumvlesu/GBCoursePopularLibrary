package com.shumikhin.gbcoursepopularlibrary.model.cache

import com.shumikhin.gbcoursepopularlibrary.model.db.Database
import com.shumikhin.gbcoursepopularlibrary.model.db.RoomGitHubUser
import com.shumikhin.gbcoursepopularlibrary.model.remote.GitHubUser
import io.reactivex.rxjava3.core.Single

class UsersCache(private val db: Database): IGithubUsersCache {

    override fun getCachedUsers(): Single<List<GitHubUser>> =
        Single.fromCallable {
            db.userDao.getAll().map { roomUser ->
                GitHubUser(roomUser.id, roomUser.login, roomUser.avatarUrl, roomUser.reposUrl)
            }
        }

    override fun insertUsersToCache(users: List<RoomGitHubUser>) {
        db.userDao.insert(users)
    }
}