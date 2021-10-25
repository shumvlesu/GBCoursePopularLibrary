package com.shumikhin.gbcoursepopularlibrary.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.rxjava3.subjects.BehaviorSubject

//Реализация интерфейса INetworkStatus определяет, есть ли подключение к сети на устройстве.
class AndroidNetworkStatus(context: Context) : INetworkStatus {

//Мы используем ConnectivityManager и его возможность зарегистрировать слушатель изменения
//статуса сети через registerNetworkCallback, обернув всё в Rx, воспользовавшись BehaviorSubject.
//До срабатывания слушателя будем считать, что сети нет, передавая false в качестве значения по умолчанию.
    private val statusSubject: BehaviorSubject<Boolean> = BehaviorSubject.create()

    init {

        statusSubject.onNext(false)

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()

        connectivityManager.registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    statusSubject.onNext(true)
                }

                override fun onUnavailable() {
                    statusSubject.onNext(false)
                }

                override fun onLost(network: Network) {
                    statusSubject.onNext(false)
                }

            })

    }

    override fun isOnline() = statusSubject
    override fun isOnlineSingle() = statusSubject.first(false)

}