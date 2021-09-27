package com.shumikhin.gbcoursepopularlibrary.rx

import io.reactivex.rxjava3.core.Observable

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
        fun exec() {
        }
    }

}
