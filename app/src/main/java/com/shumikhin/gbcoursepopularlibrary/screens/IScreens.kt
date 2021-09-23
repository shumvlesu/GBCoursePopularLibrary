package com.shumikhin.gbcoursepopularlibrary.screens

import com.github.terrakok.cicerone.Screen

//IScreens для router'a cicirone
interface IScreens {
    //Классы Screen и FragmentScreen — часть Cicerone
    fun users(): Screen
}