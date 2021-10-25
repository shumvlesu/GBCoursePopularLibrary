package com.shumikhin.gbcoursepopularlibrary.model.db

import androidx.room.*

//Это дао для репозиториев пользователя
@Dao
interface RepositoryDao {

    //В функциях insert через аргумент аннотации onConflict указываем, что при возникновении конфликта
    //по первичному ключу надо заменить старое значение новым.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomGithubRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: RoomGithubRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomGithubRepository>)

    @Update
    fun update(user: RoomGithubRepository)

    @Update
    fun update(vararg users: RoomGithubRepository)

    @Update
    fun update(users: List<RoomGithubRepository>)

    @Delete
    fun delete(user: RoomGithubRepository)

    @Delete
    fun delete(vararg users: RoomGithubRepository)

    @Delete
    fun delete(users: List<RoomGithubRepository>)

    @Query("SELECT * FROM RoomGithubRepository")
    fun getAll(): List<RoomGithubRepository>

    //Всё аналогично, но в случае поиска по пользователю используется поле userId — внешний ключ.
    @Query("SELECT * FROM RoomGithubRepository WHERE userId = :userId")
    fun findForUser(userId: String): List<RoomGithubRepository>

}