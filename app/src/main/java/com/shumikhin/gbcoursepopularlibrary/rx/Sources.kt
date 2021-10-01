package com.shumikhin.gbcoursepopularlibrary.rx

import android.util.Log
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
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
            producer.maybe()
                .subscribe({ s ->
                    Log.d(TAG,"onSuccess: $s")
                }, {
                    Log.e(TAG,"onError: ${it.message}")
                }, {
                    Log.d(TAG,"onComplete")
                })





        }





    }

}
