package com.shumikhin.gbcoursepopularlibrary.screens

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.shumikhin.gbcoursepopularlibrary.model.remote.GitHubUser
import com.shumikhin.gbcoursepopularlibrary.view.ui.UsersFragment
import com.shumikhin.gbcoursepopularlibrary.view.ui.detailsuser.UserDetailsFragment

//import ru.terrakok.cicerone.android.su


//Этот класс у нас будет знать о андроидовских фрагментах
class AndroidScreens : IScreens {
    //Классы Screen и FragmentScreen — часть Cicerone, причём второй — наследник первого. В его
    //конструктор мы передаём функтор, создающий фрагмент. Такой фрагмент представляет собой экран.
    //В дальнейшем функтор вызывается внутри навигатора при получении навигационных команд.
    override fun users(): Screen = FragmentScreen { UsersFragment.newInstance() }
    override fun details(user: GitHubUser) : Screen = FragmentScreen { UserDetailsFragment.newInstance(user) }
}

