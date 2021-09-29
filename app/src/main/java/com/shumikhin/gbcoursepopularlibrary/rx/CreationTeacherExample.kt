package com.shumikhin.gbcoursepopularlibrary.rx

import android.util.Log
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
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

        fun getJustObservable2(): @NonNull Observable<String> {
            return Observable.just("1", "2", "3", "3")
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


        //create используя излучаетль (emitter) вызвает разные методы у подписчиков: onNext, onError, onComplete.
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
        //2021-09-29 13:40:57.416 9535-9535/com.shumikhin.gbcoursepopularlibrary D/TAG: +++++++++++getRangeObservable()
        //2021-09-29 13:40:57.417 9535-9535/com.shumikhin.gbcoursepopularlibrary D/TAG: onSubscribe
        //2021-09-29 13:40:57.650 9535-9535/com.shumikhin.gbcoursepopularlibrary D/TAG: onError
        //2021-09-29 13:41:02.152 9590-9590/com.shumikhin.gbcoursepopularlibrary D/TAG: +++++++++++getRangeObservable()
        //2021-09-29 13:41:02.153 9590-9590/com.shumikhin.gbcoursepopularlibrary D/TAG: onSubscribe
        //2021-09-29 13:41:02.528 9590-9590/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: Success
        //2021-09-29 13:41:02.653 9590-9590/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: Success
        //2021-09-29 13:41:03.348 9590-9590/com.shumikhin.gbcoursepopularlibrary D/TAG: onError
        //
        // с onErrorComplete() будет onComplete если произошла ошибка
        //2021-09-29 14:00:37.803 10108-10108/com.shumikhin.gbcoursepopularlibrary D/TAG: +++++++++++getRangeObservable()
        //2021-09-29 14:00:37.815 10108-10108/com.shumikhin.gbcoursepopularlibrary D/TAG: onSubscribe
        //2021-09-29 14:00:38.130 10108-10108/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: Success
        //2021-09-29 14:00:38.556 10108-10108/com.shumikhin.gbcoursepopularlibrary D/TAG: onComplete

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

            //Log.d(TAG, "+++++++++++getRangeObservable()")
            //producer.getFromCallableObservable().subscribe(stringObserver)

            //Log.d(TAG, "+++++++++++getRangeObservable()")
            ////onErrorComplete говортит о том что если у нас ошибка то мы все равно комплитимся
            //producer.getCreateObservable().onErrorComplete().subscribe(stringObserver)


            //**************************операторы манипуляции над потоком****************//

            //Log.d(TAG, "+++++++++++getJustObservable2()")
            //Оператор take(count) берёт первые count элементов цепочки
            //takeLast - тоже самое, но последних
            //producer.getJustObservable2()
            //    .take(2)
            //    .subscribe(stringObserver)


            //Один из ключевых операторов — map. Этот оператор преобразует элементы цепочки согласно
            //переданному ему правилу. Чаще всего это какая-нибудь лямбда.
            //producer.getIntervalObserver()
            //    .map{ "Количество тиков $it" }
            //    .subscribe(stringObserver)


            //Оператор distinct, как следует из его названия, отсеивает дубликаты.
            //producer.getJustObservable2()
            //    .distinct()
            //    .subscribe(stringObserver)


            //Суть оператора — в его названии. Отфильтруем все строки, представляющие числа, меньшие или
            //равные единице.
            //producer.getJustObservable2()
            //    .filter { it.toString() == "3" } // только 3'ки
            //    .subscribe(stringObserver)


            //Один из операторов объединения. Сливает источники
            //Так как у нас довольно простой пример, все значения выдаются последовательно. Однако на самом
            //деле порядок элементов не гарантируется, и элементы второго источника могут выдаваться между
            //элементами первого.
            //producer.getJustObservable2()
            //    .mergeWith(producer.getJustObservable())
            //    .subscribe(stringObserver)


            //Оператор flatMap похож на map, также применяет функцию к каждому излучаемому элементу, но эта
            //функция возвращает Obsevable. flatMap из каждого элемента создаёт новый источник, после чего
            //выполняет слияние этих источников, похожее на применение над ними оператора merge.
            //producer.getJustObservable2()
            //    .flatMap {
            //        val delay = Random.nextInt(1000).toLong()
            //        return@flatMap Observable.just(it + "x", it + "y").delay(delay, TimeUnit.MILLISECONDS)
            //    }
            //    .subscribe(stringObserver)

            //return@flatMap Observable.just(it + "x").delay(delay, TimeUnit.MILLISECONDS) - результат:
            //2021-09-29 16:05:08.982 11906-11906/com.shumikhin.gbcoursepopularlibrary D/TAG: onSubscribe
            //2й элемент соединен flatMap и помещен впереди 1го
            //2021-09-29 16:05:09.157 11906-11939/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 2x
            //2021-09-29 16:05:09.218 11906-11937/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 1x
            //2021-09-29 16:05:09.493 11906-11937/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 3x
            //2021-09-29 16:05:09.914 11906-11939/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 3x
            //2021-09-29 16:05:09.914 11906-11939/com.shumikhin.gbcoursepopularlibrary D/TAG: onComplete

            //return@flatMap Observable.just(it + "x", it + "y").delay(delay, TimeUnit.MILLISECONDS) - результат:
            //2021-09-29 16:09:02.315 12054-12054/com.shumikhin.gbcoursepopularlibrary D/TAG: onSubscribe
            //2021-09-29 16:09:02.338 12054-12082/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 3x
            //2021-09-29 16:09:02.338 12054-12082/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 3y
            //2021-09-29 16:09:02.605 12054-12081/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 1x
            //2021-09-29 16:09:02.605 12054-12081/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 1y
            //2021-09-29 16:09:02.978 12054-12081/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 3x
            //2021-09-29 16:09:02.978 12054-12081/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 3y
            //2021-09-29 16:09:03.267 12054-12082/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 2x
            //2021-09-29 16:09:03.267 12054-12082/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 2y
            //2021-09-29 16:09:03.267 12054-12082/com.shumikhin.gbcoursepopularlibrary D/TAG: onComplete



            //zip - соединяет несколько потоков данных
            val observable = producer.getJustObservable()
            val observable2 = producer.getJustObservable2()
                Observable.zip(observable,observable2, BiFunction { o1, o2 ->
                    o1+o2 //Хотим склеить 2а потока при помощи BiFunction()
                })
                .subscribe(stringObserver)

            //Обращаю внимание на то что один поток был длиннее, но выполнилось по короткому.
            //2021-09-29 16:33:41.633 12578-12578/com.shumikhin.gbcoursepopularlibrary D/TAG: onSubscribe
            //2021-09-29 16:33:41.638 12578-12578/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: a1
            //2021-09-29 16:33:41.639 12578-12578/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: b2
            //2021-09-29 16:33:41.645 12578-12578/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: c3
            //2021-09-29 16:33:41.646 12578-12578/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: d3
            //2021-09-29 16:33:41.647 12578-12578/com.shumikhin.gbcoursepopularlibrary D/TAG: onComplete



        }
    }
}