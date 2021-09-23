package com.shumikhin.gbcoursepopularlibrary.screens

import com.github.terrakok.cicerone.Screen
import com.shumikhin.gbcoursepopularlibrary.model.GithubUser

//IScreens для router'a cicirone
interface IScreens {
    //Классы Screen и FragmentScreen — часть Cicerone
    fun users(): Screen
    fun details(user : GithubUser): Screen
}