package com.shumikhin.gbcoursepopularlibrary.di.modules

import com.shumikhin.gbcoursepopularlibrary.model.RetrofitGitHubUsersRepo
import com.shumikhin.gbcoursepopularlibrary.retrofit.IDataSource
import com.shumikhin.gbcoursepopularlibrary.retrofit.IGitHubUsersRepo
import com.shumikhin.gbcoursepopularlibrary.utils.INetworkStatus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun usersRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IGithubUsersCache
    ): IGitHubUsersRepo = RetrofitGitHubUsersRepo(api, networkStatus, cache)

}