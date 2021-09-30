package com.shumikhin.gbcoursepopularlibrary.rx

import kotlin.random.Random

class Sources {

    fun init() {
        Consumer(Producer()).execute()
    }

    /** Производитель данных */
    class Producer {

        fun randomResultOperation(): Boolean {
            Thread.sleep(Random.nextLong(1000))
            return listOf(true, false, true)[Random.nextInt(2)]
        }

    }

    /** Потребитель данных */
    class Consumer(val producer: Producer) {

        //val compositeDisposable = CompositeDisposable()

        private val TAG = "RxJava"

        /** Выполнение «потребления» */
        fun execute() {
        }

    }

}
