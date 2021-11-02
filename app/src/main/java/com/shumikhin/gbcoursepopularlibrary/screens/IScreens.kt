package com.shumikhin.gbcoursepopularlibrary.screens

import com.github.terrakok.cicerone.Screen
import com.shumikhin.gbcoursepopularlibrary.model.remote.GitHubUser


//IScreens для router'a cicirone
interface IScreens {
    //Классы Screen и FragmentScreen — часть Cicerone
    fun users(): Screen
    fun details(user : GitHubUser): Screen
}