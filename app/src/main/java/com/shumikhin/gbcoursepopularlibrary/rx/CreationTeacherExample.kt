package com.shumikhin.gbcoursepopularlibrary.rx

import android.util.Log
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class CreationTeacherExample {

    fun init() {
        Consumer(Producer()).execute()
    }

    /** Производитель данных */
    class Producer {

//        fun getJustObservable(): @NonNull Observable<String> {
//            return Observable.just("1", "2", "1", "4")
//        }

        fun getJustObservable(): @NonNull Observable<String> {
            return Observable.just("a", "b", "c", "d", "e")
        }

        fun getFromIterableObserver(): Observable<String> {
            return Observable.fromIterable(arrayListOf("1", "2", "3"))
        }

//        fun kotlinToObservable(): Observable<String> {
//            return arrayListOf("1", "2", "3").toObservable()
//        }

        fun getIntervalObserver(): Observable<Long> {
            return Observable.interval(3, TimeUnit.SECONDS)
        }

        fun getTimerObserver(): Observable<Long> {
            return Observable.timer(5, TimeUnit.SECONDS)
        }

        fun getRangeObservable(): @NonNull Observable<Int> {
            return Observable.range(10, 5)
        }

        private fun randomResult(): Boolean {
            Thread.sleep(Random.nextLong(1000))
            return listOf(true, false, true)[Random.nextInt(2)]
        }

        fun getFromCallableObservable(): Observable<Boolean> {
            return Observable.fromCallable {
                randomResult()
            }
        }

        fun getCreateObservable() = Observable.create<String> { emitter ->
            try {
                for (i in 0..10) {
                    val result = randomResult()
                    if (result) {
                        emitter.onNext("Success")
                    } else {
                        emitter.onError(IllegalStateException("Can't be false"))
                    }
                }
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

    }

    /** Потребитель данных */
    class Consumer(private val producer: Producer) {

        private val TAG = "TAG"

        /** Выполнение «потребления» */
        fun execute() {

            //создаем Observer который у  нас потребляет стринги из продюсера
            val stringObserver = object : Observer<String> {

                var disposable: Disposable? = null

                //выполнится когда отработает subscribe(stringObserver) в 111 строке
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe")
                    //Для onComplete
                    disposable = d
                }

                //Вызовится тогда когда будет выдаваться новое значение в потоке, "а" затем когда "b" и т.д.
                override fun onNext(t: String) {
                    Log.d(TAG, "onNext: $t")

                    //Можно диспоузить (отисываться) и тут например по условию
                    //if (t=="c") disposable?.dispose()
                    //Тогда результат будет такой и onComplete() не вызовится
                    //2021-09-28 15:55:55.739 5630-5630/com.shumikhin.gbcoursepopularlibrary D/TAG: onSubscribe
                    //2021-09-28 15:55:55.739 5630-5630/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: a
                    //2021-09-28 15:55:55.739 5630-5630/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: b
                    //2021-09-28 15:55:55.739 5630-5630/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: c

                }

                //Если вызвалась ошибка
                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError")
                }

                //вызывится после onNext когда будет последнее значение в потоке, у нас это после "e"
                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                    //Прекращаем подписку на прослушивание getJustObservable()
                    //Короче говоря отписываемся. Ведь когда поток приносит свое последнее значение
                    // мы прродолжаем слушать его, а тут благодоря .dispose() слушать перестаем
                    disposable?.dispose()
                }

            }

            //Подписываемся на метод getJustObservable продюсера методом subscribe
            //используя в качестве параметра метода наш stringObserver
            //getJustObservable начнет только что-то одавать когда мы на него подпишемся (.subscribe)
            producer.getJustObservable().subscribe(stringObserver)



            // val observable = producer.getJust2Observable()
            // val observable2 = producer.getJustObservable()

            //observable2.cast<String>().subscribe(stringObserver)
        }
    }
}