package com.shumikhin.gbcoursepopularlibrary.utils

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


//интерфейс INetworkStatus нужен для того что-бы определять, есть ли подключение к сети на устройстве.
interface INetworkStatus {
    //Предусмотрим возможность подписаться на изменения статуса сети через isOnline() или получить его
    //один раз посредством isOnlineSingle()
    fun isOnline(): Observable<Boolean>
    fun isOnlineSingle(): Single<Boolean>
}