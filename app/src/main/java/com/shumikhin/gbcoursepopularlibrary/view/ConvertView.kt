package com.shumikhin.gbcoursepopularlibrary.view

import android.net.Uri
import moxy.MvpView
import moxy.viewstate.strategy.alias.SingleState

@SingleState
interface ConvertView: MvpView {

    //Показать сообщение
    //message - Строка сообщения
    fun showMessage(message: String)

    // Выбрать исходный рисунок для конвертирования
    fun chooseImage()

    // Показать исходный выбранный рисунок
    // uri - Uri исходного рисунка, который нужно показать
    fun showSelectedImage(uri: Uri?)

    // Показать сохраненный конвертированнный рисунок
    // uri - Uri конвертированного рисунка
    fun showResultImage(uri: Uri?)

    // Установить Enable/Disable для кнопок КОНВЕРТИРОВАТЬ, ОТМЕНА
    // use True(Сделать активными кнопки) /False (Сделать неактивными кнопки)
    fun useConvertButtons(use: Boolean)

    // Показать/скрыть ProgressBar
    // isLoading True(Показать)/False(Скрыть)
    fun showLoading(isLoading: Boolean)

}