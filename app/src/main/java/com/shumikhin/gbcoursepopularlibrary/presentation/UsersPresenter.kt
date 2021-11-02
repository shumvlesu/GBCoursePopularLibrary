package com.shumikhin.gbcoursepopularlibrary.presentation

import android.util.Log
import com.github.terrakok.cicerone.Router
import com.shumikhin.gbcoursepopularlibrary.model.remote.GitHubUser
import com.shumikhin.gbcoursepopularlibrary.retrofit.IGitHubUsersRepo
import com.shumikhin.gbcoursepopularlibrary.screens.AndroidScreens
import com.shumikhin.gbcoursepopularlibrary.view.UserItemView
import com.shumikhin.gbcoursepopularlibrary.view.ui.UsersView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter

//Мы реализовали интерфейс IUserListPresenter классом UsersListPresenter, где и содержатся
//данные и логика по наполнению View. Сюда же делегируется получение количества элементов списка
//через getCount(). В остальном всё просто:
//● при первом присоединении View вызываем метод init(), в котором напишем все операции по инициализации View;
//● затем получаем данные из репозитория;
//● отдаём их презентеру списка;
//● командуем View обновить список.
//Далее оставляем заготовку слушателя клика
class UsersPresenter(private val usersRepo: IGitHubUsersRepo, private val router: Router) :
    MvpPresenter<UsersView>() {

    //Неиспользуем у внутреннего презентера мокси так как  он все равно внутри презентера который мокси использжует.
    class UsersListPresenter : IUserListPresenter {

        val users = mutableListOf<GitHubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login.orEmpty())
            user.avatarUrl?.let { view.loadAvatar(it) }

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
            //переход на экран пользователя
            val user = usersListPresenter.users[itemView.pos]
            router.navigateTo(AndroidScreens().details(user))
            //router.navigateTo(AndroidScreens().details(GitHubUser(user.login)))
        }

    }

    //загружаем даннные при помощи RxJava
    private fun loadData() {
        usersRepo
            .getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ users ->
                usersListPresenter.users.clear()
                usersListPresenter.users.addAll(users)
                viewState.updateList()
            }, {
                Log.e("UsersPresenter", "Ошибка получения пользователей!", it)
            })
    }

    //Для обработки нажатия клавиши «Назад» добавляем функцию backPressed(). Она возвращает
    //Boolean, где мы передаём обработку выхода с экрана роутеру. Вообще, функции Presenter, согласно
    //парадигме, не должны ничего возвращать, но в нашем случае приходится идти на компромисс из-за
    //недостатков фреймворка.
    fun backPressed(): Boolean {
        router.exit()
        return true
    }


    //1. navigateTo() — переход на новый экран.
    //2. newScreenChain() — сброс цепочки до корневого экрана и открытие одного нового.
    //3. newRootScreen() — сброс цепочки и замена корневого экрана.
    //4. replaceScreen() — замена текущего экрана.
    //5. backTo() — возврат на любой экран в цепочке.
    //6. exit() — выход с экрана.
    //7. exitWithMessage() — выход с экрана и отображение сообщения.
    //8. showSystemMessage() — отображение системного сообщения.

}
