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
class MainPresenter(private val usersRepo: GithubUsersRepo) : MvpPresenter<MainView>() {

    //Неиспользуем у внутреннего презентера мокси так как  он все равно внутри презентера который мокси использжует.
    class UsersListPresenter : IUserListPresenter {

        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }

    }

    //создаем во внешнем презентере ссылку на внутренний
    val usersListPresenter = UsersListPresenter()

    //У презентера как и у вьюх есть свой жзненный цыкл
    //Этот метод выполняется при самом первом присоединении самой первой из вьюшек
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
       //инициализируем вьюху
        viewState.init()
        //Загружаем данные
        loadData()
        //инициализируем клик
        usersListPresenter.itemClickListener = { itemView ->
            //TODO: переход на экран пользователя
        }

    }

    //загружаем даннные
    fun loadData() {
        val users = usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

}
