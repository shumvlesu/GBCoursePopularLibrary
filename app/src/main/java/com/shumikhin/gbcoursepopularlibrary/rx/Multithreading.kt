package com.shumikhin.gbcoursepopularlibrary.rx

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

//Многопоточность стр. 11
class Multithreading {

    fun init() {
        Consumer(Producer()).execute()
    }

    /** Производитель данных */
    class Producer {

        fun scheduledObservable(): Observable<Boolean> = Observable.create {
            it.onNext(true)
        }

    }

    /** Потребитель данных */
    class Consumer(private val producer: Producer) {

        private val myTAG = "Multithreading"

        /** Выполнение «потребления» */
        fun execute() {

            //Обычный обсервер
            //Для указания потока применяется Scheduler. RxJava предоставляет несколько:
            //1. Schedulers.io — для выполнения IO операций (сеть, диск).
            //2. Schedulers.computation — для выполнения вычислений. Используется для больших сложных вычислений.
            // Допустим для преобразования картинки.
            //3. Schedulers.newThread — с произвольным новым потоком.
            producer.scheduledObservable()
                //subscribeOn - решить где мы генерим данные, observeOn - решить где мы данные обработаем
                //Несколько observeOn может быть, ну и логично что несколько subscribeOn - нет (поток то один)
                .subscribeOn(Schedulers.io()) //Здесь и указываем где будет использоваться (генрироваться) поток.
                //Допустим у нас тяжелый map и мы не хотим его обрабатывать в нашем UI потоке
                //Указываем что он обработается в computation
                .observeOn(Schedulers.computation()) //указываем где хотим этот поток обработать.
                //Делаем тяжелую обработку
                .map { Log.d(myTAG, "Тяжелая обработка map в потоке computation - $it") }
                //И тепрь возвращаем все в UI поток
                .observeOn(AndroidSchedulers.mainThread()) //указываем где хотим этот поток обработать.

                //Примеры обработак ошибок в потоках:
                //onErrorReturn - Этот оператор перехватывает ошибку и передаёт вместо неё значение 0. Однако работа источника при этом всё равно завершается.
                .onErrorReturn { return@onErrorReturn 0 }

                //onErrorResumeNext - Аналогичный оператор, только вместо одного значения при ошибке он передаёт запасной источник,
                //который перехватит управление на себя и продолжит работу всей последовательности в случае возникновения ошибки в исходной
                //.onErrorResumeNext {
                //    return@onErrorResumeNext Observable.just(0)
                //}

                //retry
                //Этот оператор в случае ошибки перезапускает подписку
                //В качестве аргумента передаётся максимальное количество попыток. Это удобно, например, когда мы
                //делаем сетевые запросы.
                //.retry(5)

                .subscribe() { Log.d(myTAG, "$it") }

        }

    }


}
