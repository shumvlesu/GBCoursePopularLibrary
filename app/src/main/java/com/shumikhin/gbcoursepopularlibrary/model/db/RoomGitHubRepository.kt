package com.shumikhin.gbcoursepopularlibrary.model.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
//Класс описывающий таблицу списка репозиториев пользователя
//Так как пользователь и репозиторий состоят в отношении «один ко многим»,
//потребуется внешний ключ. Для этого мы создали поле userId, которое связали с полем id в классе
//RoomGithubUser посредством аргумента аннотации. В аргумент foreignKeys передаётся массив всех
//внешних ключей. Здесь он один и в него мы передаём:
//1. Класс внешней сущности entity.
//2. Поле id, к которому привязываемся во внешней сущности в parentColumns.
//3. Поле usedId, которое привязываем в текущей сущности в childColumns.
//4. Поведение дочерних сущностей при удалении родительской в onDelete. В нашем случае мы
//передаём CASCADE, чтобы дочерние сущности исчезали при удалении родительской, так как
//репозитории без пользователя сюда не подходят.
@Entity(
    foreignKeys = [ForeignKey(
        entity = RoomGitHubUser::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomGitHubRepository (
    @PrimaryKey var id: String,
    var name: String,
    //var forksCount: Int,
    var userId: String
)