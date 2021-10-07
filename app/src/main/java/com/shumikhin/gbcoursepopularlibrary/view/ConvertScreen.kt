package com.shumikhin.gbcoursepopularlibrary.view

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.shumikhin.gbcoursepopularlibrary.view.ConvertFragment

class ConvertScreen {
    fun create(): Screen = FragmentScreen { ConvertFragment.newInstance() }
}