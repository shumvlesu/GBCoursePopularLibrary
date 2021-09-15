package com.shumikhin.gbcoursepopularlibrary

import com.shumikhin.gbcoursepopularlibrary.model.GithubUser
import com.shumikhin.gbcoursepopularlibrary.model.GithubUsersRepo
import com.shumikhin.gbcoursepopularlibrary.presentation.IUserListPresenter
import com.shumikhin.gbcoursepopularlibrary.view.UserItemView
import moxy.MvpPresenter

//Мы реализовали интерфейс IUserListPresenter классом UsersListPresenter, где и содержатся
//данные и логика по наполнению View. Сюда же делегируется получение количества элементов списка
//через getCount(). В остальном всё просто:
//● при первом присоединении View вызываем метод init(), в котором напишем все операции по инициализации View;
//● затем получаем данные из репозитория;
//● отдаём их презентеру списка;
//● командуем View обновить список.
//Далее оставляем заготовку слушателя клика
class MainPresenter(val usersRepo: GithubUsersRepo) : MvpPresenter<MainView>() {

    class UsersListPresenter : IUserListPresenter {

        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }

    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { itemView ->
            //TODO: переход на экран пользователя
        }

    }

    fun loadData() {
        val users = usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

}
