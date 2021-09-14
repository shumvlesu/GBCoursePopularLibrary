package com.shumikhin.gbcoursepopularlibrary

import androidx.annotation.IntRange
import moxy.MvpPresenter

//Логику приложения, вытащим в презентер
//Формируем ссылку на модель и метод для вызова из View при клике на кнопку, куда отдаётся
//id, и где происходит вся логика. Для этого получаем у модели следующее значение по индексу
//и командуем view об установке текста соответствующей кнопке. Как уже упоминалось,
//относительно view подобная работа с кнопками по индексам считается неправильной. Однако
//это часть специально заложенной архитектурной ошибки, о которой говорит комментарий в
//презентере.
//В чём ошибка: в презентере упоминается класс R, относящийся к AndroidSDK, но быть его
//здесь не должно. Исправление этой ошибки будет практическим заданием к уроку.

//Подключаем презентр к мокси -  MvpPresenter<MainView>()
// где MainView это наша вьюха (класс MainView)
class MainPresenter : MvpPresenter<MainView>(){

    val model = CountersModel()

    //Как Вариант с индексом
    fun counterClick(@IntRange(from = 0, to = 2) id: Int) {
        val nextValue = model.next(id)
        //view.setButtonText(id, nextValue.toString())
        //Заменяем view на viewState так как мы подключили мокси
        viewState.setButtonText(id, nextValue.toString())
    }

    fun counterClick0() {
        val nextValue = model.next(0)
        viewState.setButtonTextC0(nextValue.toString())
    }

    fun counterClick1() {
        val nextValue = model.next(1)
        viewState.setButtonTextC1(nextValue.toString())
    }

    fun counterClick2() {
        val nextValue = model.next(2)
        viewState.setButtonTextC2(nextValue.toString())
    }

}