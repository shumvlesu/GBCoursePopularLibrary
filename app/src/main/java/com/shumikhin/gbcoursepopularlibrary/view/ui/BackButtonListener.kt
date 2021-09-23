package com.shumikhin.gbcoursepopularlibrary.view.ui

//абстрактный слушатель навигации назад
interface BackButtonListener {
    fun backPressed(): Boolean
}