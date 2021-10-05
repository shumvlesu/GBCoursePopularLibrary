package com.shumikhin.gbcoursepopularlibrary.rx

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

//В Android мы можем назначить колбэк для отслеживания статуса сетевого подключения. Делается это
//посредством ConnectivityManager и его функции registerNetworkCallback:
class NetworkStatus(context: Context) {

    //Превращаем это в Observable<Boolean>, который отдает нам true, если сеть появилась,
    //и false, когда пропала. Обернув это в простой класс и воспользовавшись PublishSubject, получим следующее
    private val statusSubject = PublishSubject.create<String>()
    private val connectivityManager = context.getSystemService<ConnectivityManager>()
    private val request = NetworkRequest.Builder().build()

    init {
        connectivityManager?.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    //Вызывается когда сеть есть сразу после запроса, либо когда появилась после отсутствия.
                    super.onAvailable(network)
                    statusSubject.onNext("Доступна")
                }

                override fun onLost(network: Network) {
                    //Вызывается, когда сеть потеряна
                    super.onLost(network)
                    statusSubject.onNext("Не доступна")
                }

                override fun onUnavailable() {
                    //Вызывается, когда сеть не обнаружена после запроса
                    super.onUnavailable()
                    statusSubject.onNext("Не доступна")
                }

            })
    }

fun status(): Observable<String> = statusSubject

}