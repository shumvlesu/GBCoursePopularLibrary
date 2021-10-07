package com.shumikhin.gbcoursepopularlibrary.scheduler


object SchedulerFactory {
    fun create(): Schedulers = DefaultSchedulers()
}