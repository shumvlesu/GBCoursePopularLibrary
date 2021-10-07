package com.shumikhin.gbcoursepopularlibrary.scheduler

import io.reactivex.Scheduler

interface Schedulers {

    // Фоновый поток
    fun background(): Scheduler

    // Главный поток
    fun main(): Scheduler

    // Поток для вычислений
    fun computation(): Scheduler

}