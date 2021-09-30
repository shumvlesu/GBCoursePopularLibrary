package com.shumikhin.gbcoursepopularlibrary.rx

import kotlin.random.Random

class Sources {

    fun exec() {
        Consumer(Producer()).exec()
    }

    class Producer {
        fun randomResultOperation(): Boolean {
            Thread.sleep(Random.nextLong(1000))
            return listOf(true, false, true)[Random.nextInt(2)]
        }
    }

    class Consumer(val producer: Producer) {
        fun exec() {
        }
    }

}
