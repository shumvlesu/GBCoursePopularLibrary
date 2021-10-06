package com.shumikhin.gbcoursepopularlibrary.rx

import android.util.Log
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


//Flowable
//Flowable аналогичен Observable и отличается от него поддержкой механизма BackPressure.
//Назначение BackPressure — корректная обработка значений, выдаваемых источником, когда их
//настолько много, что код не успевает их обработать. Например, если мы получаем частые сообщения
//из сетевого сокета. Такая ситуация может привести к тому, что неуспевающие обработчики «съедят»
//всю память, а приложение упадёт с OutOfMemoryException. BackPressure позволяет распорядиться
//теми значениями, которые выдаёт источник, когда обработка предыдущих ещё не завершилась. Есть
//несколько стратегий, применяемых через соответствующие операторы:
//1. onBackpressureBuffer() — сохранять значения и обрабатывать по мере возможности. В
//качестве аргумента обычно передаётся максимальный размер буфера.
//2. onBackpressureDrop() — выбрасывать лишние значения.
//3. onBackpressureLatest() — выбрасывать все значения, кроме последнего.
class BackPressure {

    fun execute() {
        Consumer(Producer()).consume()
    }

    class Producer {
        //Обычный Observable
        fun noBackPressure(): Observable<Int> = Observable.range(
            0,
            10000000
        ).subscribeOn(Schedulers.io()) //Обычно Schedulers вешается при подписке а не при создании источника
        //Но для примера это сделали здесь

        //Flowable как эволюция Observable, позволяет работать с большим потоком данных что бы подписчик не
        //"захлебнулся" в этом потоке передаваемых данных.
        //Редко встречается в андроид разработке, но знать надо.
        fun backPressure(): Flowable<Int> = Flowable.range(
            0,
            10000000
        //onBackpressureLatest() — выбрасывать все значения, кроме последнего.
        //То есть при перегрузе обработчика будут выбрасываться все значения, кроме последнего
        ).subscribeOn(Schedulers.io()).onBackpressureLatest()

        //Пример для отписки от потока
        fun observable1(): Observable<String> = Observable.just("1")
        fun observable2(): Observable<String> = Observable.just("2")

    }

    class Consumer(private val producer: Producer) {

        private val myTAG = "Flowable"
        val compositeDisposable =  CompositeDisposable()

        fun consume() {
            // consumeNoBackPressure()//Без страдегии onBackpressureLatest
            //consumeBackPressure()//используя Flowable

            //Пример отписки от потока
            execComposite()
        }

        //Если мы запустим пример, то
        //увидим, что числа начнут выводиться по порядку. Периодически будут возникать сообщения о
        //постоянных движениях Garbage Collector, свидетельствующих об очень активном использовании
        //памяти. В результате это приведёт к OOM
        private fun consumeNoBackPressure() {
            producer.noBackPressure()
                .observeOn(Schedulers.computation())
                .subscribe({
                    Thread.sleep(1000)
                    Log.d(myTAG, it.toString())
                }, {Log.e(myTAG, "onError: ${it.message}")}
                )
        }
        //Пример выполнения:
        //2021-10-06 12:15:47.260 28415-28443/com.shumikhin.gbcoursepopularlibrary D/Flowable: 0
        //2021-10-06 12:15:48.261 28415-28443/com.shumikhin.gbcoursepopularlibrary D/Flowable: 1
        //2021-10-06 12:15:49.262 28415-28443/com.shumikhin.gbcoursepopularlibrary D/Flowable: 2
        //...

        private fun consumeBackPressure(){
            producer.backPressure()
                .observeOn(Schedulers.computation(), false, 1)//см. ниже описание
                .subscribe({
                    Thread.sleep(1000)
                    Log.d(myTAG, it.toString())
                },{
                    Log.e(myTAG,"onError: ${it.message}")
                })
        }
        //Пример выполнения: Результат зависит от bufferSize в observeOn
        //2021-10-06 12:15:47.260 28415-28443/com.shumikhin.gbcoursepopularlibrary D/Flowable: 0
        //2021-10-06 12:15:48.261 28415-28443/com.shumikhin.gbcoursepopularlibrary D/Flowable: 7993317
        //2021-10-06 12:15:49.262 28415-28443/com.shumikhin.gbcoursepopularlibrary D/Flowable: 9999999

        //Все остальные значения, кроме этих 3, выбросились, так как мы не успевали их обрабатывать.
        //Важны аргументы observeOn. Помимо Scheduler, появились ещё два. Второй говорит, надо ли
        //воспроизводить последовательности элементов, приводящие к onError, а false здесь стандартное
        //значение. Этот аргумент нас не интересует и пояснён просто для информации. Интересен третий
        //аргумент, сообщающий размер буфера observeOn.
        //У observeOn есть свой буфер значений, размер которого по умолчанию равен 128. В нашем примере
        //этого размера было бы достаточно, чтобы значения выводились по порядку какое-то время —
        //нетрудно догадаться, какое. Поэтому для демонстрации работы backpressure этот буфер отключился
        //посредством передачи его размера, равного единице.


        fun execComposite() {
            val compositeDisposable = CompositeDisposable()
            val disposable1 = producer.observable1().subscribe {
                Log.d(myTAG,it)
            }
            val disposable2 = producer.observable2().subscribe {
                Log.d(myTAG,it)
            }
            //отписываемся
            compositeDisposable.addAll(disposable1)
            compositeDisposable.addAll(disposable2)
        }


    }
}

