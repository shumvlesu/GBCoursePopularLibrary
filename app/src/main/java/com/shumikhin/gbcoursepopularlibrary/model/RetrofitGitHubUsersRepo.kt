package com.shumikhin.gbcoursepopularlibrary.model

import com.shumikhin.gbcoursepopularlibrary.model.cache.IGithubUsersCache
import com.shumikhin.gbcoursepopularlibrary.model.db.RoomGitHubUser
import com.shumikhin.gbcoursepopularlibrary.model.remote.GitHubUser
import com.shumikhin.gbcoursepopularlibrary.retrofit.IDataSource
import com.shumikhin.gbcoursepopularlibrary.retrofit.IGitHubUsersRepo
import com.shumikhin.gbcoursepopularlibrary.retrofit.UserRepo
import com.shumikhin.gbcoursepopularlibrary.utils.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

//Практическое задание 1 - вытащить кэширование в отдельный класс
//RoomUserCache и внедрить его сюда через интерфейс IUserCache
class RetrofitGitHubUsersRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val usersCache: IGithubUsersCache,
) : IGitHubUsersRepo {

    override fun getUsers(): Single<List<GitHubUser>> = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUsers()
                .flatMap { users ->
                    Single.fromCallable {
                        val roomUsers = users.map { user ->
                            RoomGitHubUser(
                                user.id ?: "",
                                user.login ?: "",
                                user.avatarUrl ?: "",
                                user.reposUrl ?: ""
                            )
                        }
                        usersCache.insertUsersToCache(roomUsers)
                        users
                    }
                }
        } else {
            //Single.fromCallable {
                //соединения нет, тянем из бд
                usersCache.getCachedUsers()
            //}
        }
    }.subscribeOn(Schedulers.io())

    override fun getUserRepos(url: String): Single<List<UserRepo>> {
        return api.getUserRepos(url).subscribeOn(Schedulers.io())
    }

}