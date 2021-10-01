package com.shumikhin.gbcoursepopularlibrary.rx

import android.util.Log
import io.reactivex.rxjava3.core.Completable
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

        //Completable подходит, когда получать значения не требуется, а нас интересует сам факт завершения
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


    }

    /** Потребитель данных */
    class Consumer(val producer: Producer) {

        //val compositeDisposable = CompositeDisposable()

        private val TAG = "TAG"

        /** Выполнение «потребления» */
        fun execute() {

            //подписка на completable()
            producer.completable().subscribe({
                Log.d(TAG,"onComplete")
            }, {
                Log.e(TAG,"onError: ${it.message}")
            })
            //Пример выполнения:
            //2021-10-01 15:48:22.841 16934-16934/com.shumikhin.gbcoursepopularlibrary E/TAG: onError: Пришёл false






        }





    }

}
