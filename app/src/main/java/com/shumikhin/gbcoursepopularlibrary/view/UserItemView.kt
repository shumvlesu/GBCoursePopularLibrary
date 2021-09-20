package com.shumikhin.gbcoursepopularlibrary.view


interface UserItemView : IItemView{
    fun setLogin(text: String) //для получения поля логин из GithubUser
}