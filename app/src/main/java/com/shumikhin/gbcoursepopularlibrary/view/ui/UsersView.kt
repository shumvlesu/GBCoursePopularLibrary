package com.shumikhin.gbcoursepopularlibrary.view.ui

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

// View должна быть максимально «глупой», а это значит, что все её активные действия сводятся к сообщению
//презентеру о нажатии и установке текста кнопкам. Кнопки пока будем отличать по индексу (это неправильно)

//Стратегия мокси AddToEndSingle (по старому AddToEndSingleStrategy)- выполнить команду,
// добавить ее в конец очереди и удалить все ее предыдущие экземпляры
@AddToEndSingle
interface UsersView: MvpView  {
    //Так как всё, что появится на экране — просто список, интерфейс включает всего два метода:
    //● init() — для первичной инициализации списка, который мы будем вызывать при присоединении View к Presenter;
    //● updateList() — для обновления содержимого списка.
    fun init()
    fun updateList()
}
