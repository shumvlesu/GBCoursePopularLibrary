package com.shumikhin.gbcoursepopularlibrary.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class Creation {

    fun exec() {
        Consumer(Producer()).exec()
    }

    //Теперь в Producer мы станем создавать Observable разными способами, а в Consumer — на них
    //подписываться. При запуске функции exec внешнего класса Creation, например, из onCreate нашего
    //главного активити всё это будет запускаться и выводить результат. Рассмотрим простейший способ
    //создания Observable — оператор just.
    class Producer {
        fun just(): Observable<String> {
            return Observable.just("1", "2", "3")
        }
    }

    class Consumer(val producer: Producer) {

        val stringObserver = object : Observer<String> {
            var disposable: Disposable? = null
            override fun onComplete() {
                println("onComplete")
            }
            override fun onSubscribe(d: Disposable?) {
                disposable = d
                println("onSubscribe")
            }
            override fun onNext(s: String?) {
                println("onNext: $s")
            }
            override fun onError(e: Throwable?) {
                println("onError: ${e?.message}")
            }
        }


        fun exec() {
        }

    }

}
