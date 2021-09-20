package com.shumikhin.gbcoursepopularlibrary.presentation

import com.shumikhin.gbcoursepopularlibrary.view.IItemView
import com.shumikhin.gbcoursepopularlibrary.view.UserItemView

//теоретически в нашем приложении может быть много списков
//пользователей, создадим для него интерфейс. Далее реализуем интерфейс в Presenter экрана, а
//затем отдадим эту реализацию в качестве интерфейса в адаптер, чтобы делегировать ему всю
//логику:

//По аналогии с интерфейсом IItemView мы создали интерфейс IListPresenter, куда вынесли общие
//для всех списков вещи:
//● слушатель клика;
//● функция получения количества элементов;
//● функция наполнения View.
interface IListPresenter <V : IItemView> {
    //Здесь V представляет собой тип View для строки списка, а itemClickListener — функция,
    //принимающая на вход эту самую View. Таким образом, при обработке клика мы получаем от View
    //позицию и находим требуемый элемент.

    //По сути продублировали логику адаптера ресайквью
    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}

//А этот интерфейс наследуется от того что выше
interface IUserListPresenter : IListPresenter<UserItemView>