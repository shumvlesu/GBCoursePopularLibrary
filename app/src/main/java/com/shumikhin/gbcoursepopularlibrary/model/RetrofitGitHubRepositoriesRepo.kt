package com.shumikhin.gbcoursepopularlibrary.model

import com.shumikhin.gbcoursepopularlibrary.model.cache.IRepositoriesCache
import com.shumikhin.gbcoursepopularlibrary.model.db.RoomGitHubRepository
import com.shumikhin.gbcoursepopularlibrary.model.remote.GitHubUser
import com.shumikhin.gbcoursepopularlibrary.retrofit.IDataSource
import com.shumikhin.gbcoursepopularlibrary.retrofit.UserRepo
import com.shumikhin.gbcoursepopularlibrary.utils.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

//Практическое задание 1 - вытащить кэширование в отдельный класс
//RoomRepositoriesCache и внедрить его сюда через интерфейс IRepositoriesCache
class RetrofitGitHubRepositoriesRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val reposCache: IRepositoriesCache,
) : IGitHubRepositoriesRepo {

    override fun getRepositories(user: GitHubUser) : Single<List<UserRepo>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                user.reposUrl?.let { url ->
                    api.getUserRepos(url)
                        .flatMap { repositories ->
                            Single.fromCallable {
                                val roomUser = user.login?.let {
                                //val roomUser = user.id?.let {
                                    reposCache.getUserByLogin(it)
                                } ?: throw RuntimeException("No such user in cache")
                                val roomRepos = repositories.map {
                                    RoomGitHubRepository(
                                        it.id?: "",
                                        it.name?: "",
                                        //it.forksCount ?: 0,
                                        roomUser.id
                                    )
                                }
                                reposCache.insertRepositoriesToCache(roomRepos)
                                repositories
                            }
                        }
                } ?: Single.error<List<UserRepo>>(RuntimeException("User has no repos url"))
                    .subscribeOn(Schedulers.io())
            } else {
                //Single.fromCallable {
                //соединеия нет, грузимся из бд
                reposCache.getCachedRepositoriesByUser(user)
                //}
            }
        }.subscribeOn(Schedulers.io())

}
