package com.shumikhin.gbcoursepopularlibrary

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

// View должна быть максимально «глупой», а это значит, что все её активные действия сводятся к сообщению
//презентеру о нажатии и установке текста кнопкам. Кнопки пока будем отличать по индексу (это неправильно)

//Стратегия мокси AddToEndSingle (по старому AddToEndSingleStrategy)- выполнить команду,
// добавить ее в конец очереди и удалить все ее предыдущие экземпляры
@AddToEndSingle
interface MainView: MvpView  {
    fun setButtonText(index: Int, text: String)
    fun setButtonTextC0(text: String)
    fun setButtonTextC1(text: String)
    fun setButtonTextC2(text: String)
}