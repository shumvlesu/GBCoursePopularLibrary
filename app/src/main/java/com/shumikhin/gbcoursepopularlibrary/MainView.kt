package com.shumikhin.gbcoursepopularlibrary

// View должна быть максимально «глупой», а это значит, что все её активные действия сводятся к сообщению
//презентеру о нажатии и установке текста кнопкам. Кнопки пока будем отличать по индексу (это неправильно)
interface MainView {
    fun setButtonText(index: Int, text: String)
}