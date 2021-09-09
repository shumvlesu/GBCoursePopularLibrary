package com.shumikhin.gbcoursepopularlibrary

//Список для хранения счётчиков, методы получения текущего и следующего, а также метод
//установки значения по индексу — этого более чем достаточно.
class CountersModel {

    private val counters = mutableListOf(0, 0, 0)

    private fun getCurrent(index: Int): Int {
        return counters[index]
    }

    fun next(index: Int): Int {
        counters[index]++
        return getCurrent(index)
    }

    fun set(index: Int, value: Int) {
        counters[index] = value
    }

}