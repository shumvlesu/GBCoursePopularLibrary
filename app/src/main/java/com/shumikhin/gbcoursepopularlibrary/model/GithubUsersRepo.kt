package com.shumikhin.gbcoursepopularlibrary.model


import com.shumikhin.gbcoursepopularlibrary.retrofit.ApiHolder.apiService
import com.shumikhin.gbcoursepopularlibrary.retrofit.IDataSource
import com.shumikhin.gbcoursepopularlibrary.retrofit.IGitHubUsersRepo
import com.shumikhin.gbcoursepopularlibrary.retrofit.UserRepo
import io.reactivex.rxjava3.core.Single

class GithubUsersRepo(private val api: IDataSource) : IGitHubUsersRepo {

//    fun getUsers(): Observable<GithubUser> {
//        //fromIterable, реализованый RxKotlin, похож на just, но в него передаётся не набор, а коллекция элементов
//        return repositories.toObservable()
//    }

    //fun getUsers() = ApiHolder.apiService.getUsers()

    override fun getUsers(): Single<List<GithubUser>> {
        //return apiService.getUsers().subscribeOn(Schedulers.io())
        return apiService.getUsers()
    }

    override fun getUserRepos(url: String): Single<List<UserRepo>> {
        //return apiService.getUserRepos(url).subscribeOn(Schedulers.io())
        return apiService.getUserRepos(url)
    }

}