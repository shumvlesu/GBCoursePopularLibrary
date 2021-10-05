package com.shumikhin.gbcoursepopularlibrary.rx

import android.util.Log
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Sources {

    fun init() {
        Consumer(Producer()).execute()
    }

    /** Производитель данных */
    class Producer {

        //метод который спит 1 сек. а потом возвращает одно из булевых значений
        fun randomResultOperation(): Boolean {
            Thread.sleep(Random.nextLong(1000))
            return listOf(true, false, true)[Random.nextInt(2)]
        }

        //Completable
        // подходит, когда получать значения не требуется, а нас интересует сам факт завершения
        //какой-либо операции, например, запись в файл или базу данных. Для обработки этого источника
        //используется CompletableObserver, аналогичный Observer, но не имеющий onNext.
        //Короче говоря когда надо знать хорошо завершилась операция или завершилась ошибкой.
        fun completable() = Completable.create { emitter ->
            val result = randomResultOperation()
            if (result) {
                emitter.onComplete()
            } else {
                emitter.onError(RuntimeException("Пришёл false"))
            }
        }


        //Single
        //Источник аналогичен Observable, однако может выдать только одно значение. Single идеально
        //подходит для HTTP-запросов, так как всегда ожидается только один ответ от сервера. Получение
        //значения и завершение его работы — одно и то же событие. Вместо onNext и onComplete у его
        //SingleObserver есть только один метод onSuccess, и он терминальный.
        fun single() = Single.fromCallable {
            //Для каждого вида источников есть свой аналог fromCallable. Например, в случае Completable
            //используется fromAction, так как возвращаемое значение отсутствует. Мы используем его для
            //создания Single с некоторым строковым значением. Нетрудно представить, например, как в теле
            //fromCallable размещается код сетевого HTTP-запроса и возвращается его результат.
            return@fromCallable "Привет!"
        }

        //Аналог создания Single
        fun single2() = Single.create<Boolean> { emitter ->
            val result = randomResultOperation()
            if (result) {
                emitter.onSuccess(result)
            } else {
                emitter.onError(RuntimeException("Пришёл false"))
            }
        }

        //Maybe
        //Maybe подходит, если нас устраивает как наличие значения, так и его отсутствие.
        //Например, при обработке авторизации с возможностью гостевого доступа. В этом случае нас устроит
        //и наличие, и отсутствие авторизованного пользователя.
        //Можно сказать, что он где-то между Completable и Single. Его MaybeObserver, помимо onError и
        //onSubscribe, имеет методы onSuccess и onComplete. Оба считаются «терминальными», и,
        //соответственно, взаимоисключающими. Если значение есть, то вызывается первый, наоборот — второй
        fun maybe() = Maybe.create<String> { emitter ->
            randomResultOperation().let {
                if (it) {
                    emitter.onSuccess("Success: $it")
                } else {
                    emitter.onComplete()
                    return@create
                }
            }
        }


        //Горячий Observable
        //отправляет данные независимо от того, подписан кто-нибудь на него или нет.
        //Если никто его в это время не слушает, данные будут потеряны. Такой подход удобен, например, для
        //оборачивания сокетных соединений, так как данные там идут непрерывно
        fun hotObservable() = Observable
            .interval(1, TimeUnit.SECONDS)
            //Посредством оператора publish() формируется объект ConnectableObservable, метод которого — connect().
            //Именно этот метод запускает работу Observable.
            //При этом не имеет значения, есть подписчики или нет.
            .publish() //один из способов  сделать обсервер горячим это вызвать метод publish()


        //МЕТОДЫ ГОРЯЧЕГО ОБСЕРВЕРА//
        //************У горячего Observable есть операторы, которые делают его работу отчасти сходной с работой холодного.****************//

        //метод кэширует данные, и каждый новый подписчик получает полный набор
        //данных, например, все сообщения чата, пришедшие до подписки, когда бы он ни подключился. Можно
        //подумать, что таким образом мы получаем обычный холодный Observable, но это не так. Наш
        //источник всё ещё горячий и его работа начнётся только после вызова метода connect()
        fun hotObservableReplay() = Observable
            .interval(1, TimeUnit.SECONDS)
            //Метод replay() используется вместо publish()
            //.publish()
            .replay()

        //refCount()
        //Этот метод сделает из ConnectableObservable Observable, который начнёт свою работу, как только
        //появится первый подписчик
        //Здесь нет метода connect(), так как класс ConnectableObservable, полученный методом publish(),
        //обёрнут обратно в класс Observable, благодаря использованию метода refCount(). При такой
        //конструкции мы получаем горячий Observable, который ведёт себя как холодный. Тем не менее это
        //именно горячий Observable, так как он будет раздавать одни и те же данные всем подписчикам, а не
        //стартовать работу заново для каждого из них.
        fun hotObservableRefCount() = Observable.interval(1, TimeUnit.SECONDS)
                .publish()
                .refCount()

        //Сache()
        //Observable, который появится в результате работы этого оператора, будет похож на результат
        //операторов replay() и refCount(). Он начинает работу при первом подписчике, хранит все элементы и
        //выдаёт их каждому новому подписчику, но не заканчивает работу при отсутствии подписчиков.
        fun hotObservableCache() = Observable
            .interval(1, TimeUnit.SECONDS)
            .cache()


        //Subject
        //Subject представляет собой класс, который расширяет (наследует) Observable и реализует интерфейс
        //Observer. Это одновременно и Observable, и Observer. То есть он может подписываться на источник
        //данных и рассылать эти данные своим подписчикам. Ещё такой класс используется как некий
        //источник на ручном приводе, вручную вызывая onNext, когда это требуется. Subject крайне полезны,
        //например, когда надо быстро связать между собой реактивный и нереактивный код, так как позволяют
        //сделать это без массивного рефакторинга нереактивного кода в реактивный. Достаточно просто
        //создать Subject, выставить его наружу из нереактивного класса и изнутри передавать в него значения
        //по мере работы. Так можно, например, оборачивать в Rx библиотеки, асинхронный код которых
        //передаёт свой результат через колбэки.
        //короче говоря он и источник и подписчик одновременно
        //fun publishSubject() = PublishSubject.create<String>().apply {
        //    Observable
        //        .timer(2, TimeUnit.SECONDS)
        //        .subscribe { onNext("Значение из subject") }
        //}
        //2й пример Subject
        fun publishSubject() = PublishSubject.create<Long> { publishEmitter ->
            Observable
                .interval(2, TimeUnit.SECONDS) //interval не существует для всего что с наружи
                .subscribe { publishEmitter.onNext(it) } //Но на него можно здесь подписаться
                                                         // и передавать данные в publishEmitter
        }




        }

    /** Потребитель данных */
    class Consumer(val producer: Producer) {

        //val compositeDisposable = CompositeDisposable()

        private val TAG = "TAG"

        /** Выполнение «потребления» */
        fun execute() {

//            //подписка на completable()
//            producer.completable().subscribe({
//                Log.d(TAG,"onComplete")
//            }, {
//                Log.e(TAG,"onError: ${it.message}")
//            })
//            //Пример выполнения:
//            //**** E/TAG: onError: Пришёл false


//            //подписка на single()
//            producer.single()
//                .map { it + it } //мапой предварительно сливаем
//                .subscribe({ s ->
//                    Log.d(TAG,"onSuccess: $s")
//                }, {
//                    Log.e(TAG,"onError: ${it.message}")
//                })
//            //Пример выполнения:
//            // **** D/TAG: onSuccess: Привет!Привет!


            //подписка на maybe()
            //Если случайный результат — true, то возвращаем onSuccess, в противном случае — onComplete.
            //Нетрудно представить, например, функцию получения текущего пользователя на месте
            //randomResultOperation.
//            producer.maybe()
//                .subscribe({ s ->
//                    Log.d(TAG,"onSuccess: $s")
//                }, {
//                    Log.e(TAG,"onError: ${it.message}")
//                }, {
//                    Log.d(TAG,"onComplete")
//                })


            //Подписка на hotObservable
//            val hotObservable = producer.hotObservable()
//            hotObservable.subscribe {Log.d(TAG, it.toString())} //Здесь еще не начинается вывод, мы просто подписались
//            hotObservable.connect() //Начинаем вывод данных
//            Thread.sleep(3000)
//            hotObservable.subscribe {Log.d(TAG,"Отложенный подписчик: $it")}//Данные идут не с нуля, как у холодного обсервера,
            //а именно как бы подключается к существующему потоку.
            //Пример выполнения:
            //2021-10-04 16:23:20.484 21366-21394/com.shumikhin.gbcoursepopularlibrary D/TAG: 0
            //2021-10-04 16:23:21.493 21366-21394/com.shumikhin.gbcoursepopularlibrary D/TAG: 1
            //2021-10-04 16:23:22.483 21366-21394/com.shumikhin.gbcoursepopularlibrary D/TAG: 2
            //2021-10-04 16:23:22.483 21366-21394/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 2
            //2021-10-04 16:23:23.482 21366-21394/com.shumikhin.gbcoursepopularlibrary D/TAG: 3
            //2021-10-04 16:23:23.482 21366-21394/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 3
            //2021-10-04 16:23:24.482 21366-21394/com.shumikhin.gbcoursepopularlibrary D/TAG: 4
            //2021-10-04 16:23:24.482 21366-21394/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 4

            //МЕТОДЫ ГОРЯЧЕГО ОБСЕРВЕРА//
            //************У горячего Observable есть операторы, которые делают его работу отчасти сходной с работой холодного.****************//

            //Replay()
//            val hotObservableReplay = producer.hotObservableReplay()
//            hotObservableReplay.subscribe { Log.d(TAG, it.toString())} //Здесь еще не начинается вывод, мы просто подписались
//            hotObservableReplay.connect() //Начинаем вывод данных
//            Thread.sleep(3000)
//            hotObservableReplay.subscribe {Log.d(TAG,"Отложенный подписчик: $it")}//Данные идут не с нуля, как у холодного обсервера,
            //Пример выполнения: Второй подписчик получает аналогично все данные хоть и подключился на 3 секунды позже
            //2021-10-04 16:34:48.920 22135-22163/com.shumikhin.gbcoursepopularlibrary D/TAG: 0
            //2021-10-04 16:34:49.920 22135-22163/com.shumikhin.gbcoursepopularlibrary D/TAG: 1
            //2021-10-04 16:34:50.920 22135-22163/com.shumikhin.gbcoursepopularlibrary D/TAG: 2
            //2021-10-04 16:34:50.920 22135-22135/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 0
            //2021-10-04 16:34:50.920 22135-22135/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 1
            //2021-10-04 16:34:50.920 22135-22135/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 2
            //2021-10-04 16:34:51.920 22135-22163/com.shumikhin.gbcoursepopularlibrary D/TAG: 3
            //2021-10-04 16:34:51.920 22135-22163/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 3

            //RefCount()
//            val hotObservableRefCount = producer.hotObservableRefCount()
//            hotObservableRefCount.subscribe { Log.d(TAG, it.toString())} //Здесь еще не начинается вывод, мы просто подписались
//            //Здесь нет метода connect(), так как класс ConnectableObservable, полученный методом publish(),
//            //обёрнут обратно в класс Observable, благодаря использованию метода refCount().
//            Thread.sleep(3000)
//            hotObservableRefCount.subscribe {Log.d(TAG,"Отложенный подписчик: $it")}
            //Пример выполнения: горячий обсервер который ведет себя как холодный, но с потоком данных как у горячего
            //2021-10-04 16:47:06.559 22298-22324/com.shumikhin.gbcoursepopularlibrary D/TAG: 0
            //2021-10-04 16:47:07.559 22298-22324/com.shumikhin.gbcoursepopularlibrary D/TAG: 1
            //2021-10-04 16:47:08.559 22298-22324/com.shumikhin.gbcoursepopularlibrary D/TAG: 2
            //2021-10-04 16:47:09.559 22298-22324/com.shumikhin.gbcoursepopularlibrary D/TAG: 3
            //2021-10-04 16:47:09.559 22298-22324/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 3
            //2021-10-04 16:47:10.559 22298-22324/com.shumikhin.gbcoursepopularlibrary D/TAG: 4
            //2021-10-04 16:47:10.559 22298-22324/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 4
            //2021-10-04 16:47:11.559 22298-22324/com.shumikhin.gbcoursepopularlibrary D/TAG: 5
            //2021-10-04 16:47:11.559 22298-22324/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 5

            //Сache() соединяет 2 предыдущих
//            val hotObservableCache = producer.hotObservableCache()
//            hotObservableCache.subscribe { Log.d(TAG, it.toString())} //Здесь еще не начинается вывод, мы просто подписались
//            //Здесь нет метода connect()
//            Thread.sleep(3000)
//            hotObservableCache.subscribe {Log.d(TAG,"Отложенный подписчик: $it")}
            //Пример выполнения:
            //2021-10-04 16:54:25.993 22429-22458/com.shumikhin.gbcoursepopularlibrary D/TAG: 0
            //2021-10-04 16:54:26.994 22429-22458/com.shumikhin.gbcoursepopularlibrary D/TAG: 1
            //2021-10-04 16:54:27.992 22429-22458/com.shumikhin.gbcoursepopularlibrary D/TAG: 2
            //2021-10-04 16:54:27.993 22429-22429/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 0
            //2021-10-04 16:54:27.993 22429-22429/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 1
            //2021-10-04 16:54:27.993 22429-22429/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 2
            //2021-10-04 16:54:28.993 22429-22458/com.shumikhin.gbcoursepopularlibrary D/TAG: 3
            //2021-10-04 16:54:28.993 22429-22458/com.shumikhin.gbcoursepopularlibrary D/TAG: Отложенный подписчик: 3

            //Subject
//            val subject = producer.publishSubject()
//            subject.subscribe({
//                Log.d(TAG,"onNext: $it")
//            }, {
//                Log.d(TAG,"onError: ${it.message}")
//            })
//            subject.onNext("Вызван из execute()")
            //Пример выполнения:
            //2021-10-05 10:59:15.537 23321-23321/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: Вызван из execute()
            //2021-10-05 10:59:17.537 23321-23347/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: Значение из subject

            //2й пример
//            val subject = producer.publishSubject()
//            subject.subscribe{Log.d(TAG,"onNext: $it")}
            //Пример выполнения:
            //2021-10-05 11:13:06.983 23503-23537/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 0
            //2021-10-05 11:13:08.939 23503-23537/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 1
            //2021-10-05 11:13:10.940 23503-23537/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 2
            //2021-10-05 11:13:12.989 23503-23537/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 3
            //2021-10-05 11:13:14.940 23503-23537/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 4
            //2021-10-05 11:13:16.939 23503-23537/com.shumikhin.gbcoursepopularlibrary D/TAG: onNext: 5
            //...








        }

    }

}
