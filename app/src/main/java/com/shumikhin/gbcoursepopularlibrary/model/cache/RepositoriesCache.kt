package com.shumikhin.gbcoursepopularlibrary.model.cache

import com.shumikhin.gbcoursepopularlibrary.model.db.Database
import com.shumikhin.gbcoursepopularlibrary.model.db.RoomGitHubRepository
import com.shumikhin.gbcoursepopularlibrary.model.db.RoomGitHubUser
import com.shumikhin.gbcoursepopularlibrary.model.remote.GitHubUser
import com.shumikhin.gbcoursepopularlibrary.retrofit.UserRepo
import io.reactivex.rxjava3.core.Single
import java.lang.RuntimeException

class RepositoriesCache (private val db: Database): IRepositoriesCache {

    override fun getCachedRepositoriesByUser(user: GitHubUser): Single<List<UserRepo>> =
        Single.fromCallable {
            val roomUser = user.login?.let { db.userDao.findByLogin(it) } ?: throw RuntimeException("no such user in cache")
            db.repositoryDao.findForUser(roomUser.id).map { UserRepo(it.id, it.name) }
        }

    override fun insertRepositoriesToCache(repos: List<RoomGitHubRepository>) {
        db.repositoryDao.insert(repos)
    }

    override fun getUserByLogin(text: String): RoomGitHubUser? {
        return db.userDao.findByLogin(text)
    }
}