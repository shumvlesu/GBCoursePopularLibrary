package com.shumikhin.gbcoursepopularlibrary.model

//репозиторий с фиктивными данными, которым будем пользоваться, пока не
//реализуем получение данных из сети
class GithubUsersRepo {

    private val repositories = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

    fun getUsers() : List<GithubUser> {
        return repositories
    }

}