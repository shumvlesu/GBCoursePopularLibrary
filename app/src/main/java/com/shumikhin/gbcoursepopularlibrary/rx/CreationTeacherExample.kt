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

        fun getJustObservable(): @NonNull Observable<String> {
            //В оператор just передаётся набор элементов
            return Observable.just("a", "b", "c", "d", "e")
        }

        fun getFromIterableObserver(): Observable<String> {
            //похож на just, но в него передаётся не набор, а коллекция элементов
            return Observable.fromIterable(arrayListOf("1", "2", "3"))
        }

        fun getIntervalObserver(): Observable<Long> {
            //используется тогда когда какие-то оперции надо повторять один раз в определенный промежуток
            return Observable.interval(3, TimeUnit.SECONDS) //каждые три секунды передает чило тип Long
            //2021-09-28 16:34:40.650 6324-6324/com.shumikhin.gbcoursepopularlibrary D/TAG: onSubscribe
            //2021-09-28 16:34:43.652 6324-6350/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 0
            //2021-09-28 16:34:46.652 6324-6350/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 1
            //2021-09-28 16:34:49.652 6324-6350/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 2
            //2021-09-28 16:34:52.651 6324-6350/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 3
            //2021-09-28 16:34:55.652 6324-6350/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 4
            // и так далее
        }

        fun getTimerObserver(): Observable<Long> {
            //Похож на interval но делается единоразово
            //выполнит dispose() после первого раза
            return Observable.timer(5, TimeUnit.SECONDS)
            //2021-09-28 16:46:40.983 7283-7283/com.shumikhin.gbcoursepopularlibrary D/TAG: onSubscribe
            //2021-09-28 16:46:45.986 7283-7313/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 0
            //2021-09-28 16:46:45.986 7283-7313/com.shumikhin.gbcoursepopularlibrary D/TAG: onComplete
        }

        fun getRangeObservable(): @NonNull Observable<Int> {
            //Последовательно выдаст числа от первого аргумента,
            //итерируая их столько сколько указано во втором параметре (-1)
            return Observable.range(10, 5)
            //2021-09-28 16:52:54.421 7458-7458/com.shumikhin.gbcoursepopularlibrary D/TAG: +++++++++++getRangeObservable()
            //2021-09-28 16:52:54.421 7458-7458/com.shumikhin.gbcoursepopularlibrary D/TAG: onSubscribe
            //2021-09-28 16:52:54.421 7458-7458/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 10
            //2021-09-28 16:52:54.421 7458-7458/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 11
            //2021-09-28 16:52:54.421 7458-7458/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 12
            //2021-09-28 16:52:54.421 7458-7458/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 13
            //2021-09-28 16:52:54.421 7458-7458/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 14
            //2021-09-28 16:52:54.421 7458-7458/com.shumikhin.gbcoursepopularlibrary D/TAG: onComplete
        }


        fun getFromCallableObservable(): Observable<Boolean> {
            //Чаще всего нам будет недостаточно операторов создания, принимающих заведомо известные
            //значения. Потребуется создавать источник из какой-либо процедуры, который выдаст нам результат
            //её выполнения в качестве значения в onNext. Для этого используется оператор fromCallable. Сначала
            //в Producer создадим функцию, которая имитирует длительные вычисления с некоторым случайным
            //результатом - randomResult()
            return Observable.fromCallable {
                //Симуляция длительной операции со случайным исходом
                randomResult()
                //2021-09-28 17:03:07.290 7631-7631/com.shumikhin.gbcoursepopularlibrary D/TAG: +++++++++++getRangeObservable()
                //2021-09-28 17:03:07.291 7631-7631/com.shumikhin.gbcoursepopularlibrary D/TAG: onSubscribe
                //2021-09-28 17:03:08.234 7631-7631/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: false
                //2021-09-28 17:03:08.235 7631-7631/com.shumikhin.gbcoursepopularlibrary D/TAG: onComplete
            }
            //fromCallable отличается от just тем что, just будет возвращать всем подписчикам один и тотже результат
            //полученый однажды, а fromCallable может возвращать разные результаты разным подписчикам.
            //Например функция возвращающая время в just будет возарщать одно и тоже время всем подписчикам
            //А fromCallable будет возвращать обновленное время для каждого подписчика
        }

        //Симуляция длительной операции со случайным исходом
        //Функция будет «спать» случайное время до секунды. После этого в одном случае из трёх она выдаст
        //нам false, а в остальных — true.
        private fun randomResult(): Boolean {
            Thread.sleep(Random.nextLong(1000))
            return listOf(true, false, true)[Random.nextInt(2)]
        }






//        fun getJustObservable(): @NonNull Observable<String> {
//            return Observable.just("1", "2", "1", "4")
//        }

//        fun kotlinToObservable(): Observable<String> {
//            return arrayListOf("1", "2", "3").toObservable()
//        }

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
            val stringObserver = object : Observer<Any> {

                var disposable: Disposable? = null

                //выполнится когда отработает subscribe(stringObserver) в 111 строке
                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe")
                    //Для onComplete
                    disposable = d
                }

                //Вызовится тогда когда будет выдаваться новое значение в потоке, "а" затем когда "b" и т.д.
                override fun onNext(t: Any) {
                    Log.d(TAG, "onNext: $t")
                    if (t==5.toLong()) disposable?.dispose()//что бы getIntervalObserver() бесконечно не собачил

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
            //producer.getJustObservable().subscribe(stringObserver)

            //Log.d(TAG, "+++++++++++getFromIterableObserver()")
            //producer.getFromIterableObserver().subscribe(stringObserver)

            //Log.d(TAG, "+++++++++++getIntervalObserver()")
            //producer.getIntervalObserver().subscribe(stringObserver)

            //Log.d(TAG, "+++++++++++getTimerObserver()")
            //producer.getTimerObserver().subscribe(stringObserver)

            //Log.d(TAG, "+++++++++++getRangeObservable()")
            //producer.getRangeObservable().subscribe(stringObserver)

            Log.d(TAG, "+++++++++++getRangeObservable()")
            producer.getFromCallableObservable().subscribe(stringObserver)




        }
    }
}