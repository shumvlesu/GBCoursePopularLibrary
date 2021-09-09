package com.shumikhin.gbcoursepopularlibrary

//Логику приложения, вытащим в презентер
//Формируем ссылку на модель и метод для вызова из View при клике на кнопку, куда отдаётся
//id, и где происходит вся логика. Для этого получаем у модели следующее значение по индексу
//и командуем view об установке текста соответствующей кнопке. Как уже упоминалось,
//относительно view подобная работа с кнопками по индексам считается неправильной. Однако
//это часть специально заложенной архитектурной ошибки, о которой говорит комментарий в
//презентере.
//В чём ошибка: в презентере упоминается класс R, относящийся к AndroidSDK, но быть его
//здесь не должно. Исправление этой ошибки будет практическим заданием к уроку.
class MainPresenter(val view: MainView) {

    val model = CountersModel()

//Архитектурная ошибка. В качестве практического задания -- исправить
//    fun counterClick(id: Int) {
//        when (id) {
//            R.id.btn_counter1 -> {
//                val nextValue = model.next(0)
//                view.setButtonText(0, nextValue.toString())
//            }
//            R.id.btn_counter2 -> {
//                val nextValue = model.next(1)
//                view.setButtonText(1, nextValue.toString())
//            }
//            R.id.btn_counter3 -> {
//                val nextValue = model.next(2)
//                view.setButtonText(2, nextValue.toString())
//            }
//        }
//    }

    //Как Вариант с индексом
    fun counterClick(id: Int) {
        val nextValue = model.next(id)
        view.setButtonText(id, nextValue.toString())
    }

    fun counterClick0() {
        val nextValue = model.next(0)
        view.setButtonTextC0(nextValue.toString())
    }

    fun counterClick1() {
        val nextValue = model.next(1)
        view.setButtonTextC1(nextValue.toString())
    }

    fun counterClick2() {
        val nextValue = model.next(2)
        view.setButtonTextC2(nextValue.toString())
    }

}