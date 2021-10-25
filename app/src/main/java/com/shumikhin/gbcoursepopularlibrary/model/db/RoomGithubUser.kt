package com.shumikhin.gbcoursepopularlibrary.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

//класс RoomGithubUser, который представляет таблицу пользователей
@Entity
class RoomGithubUser (
    @PrimaryKey var id: String,
    var login: String,
    var avatarUrl: String,
    var reposUrl: String
)