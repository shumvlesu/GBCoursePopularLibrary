package com.shumikhin.gbcoursepopularlibrary.model.db

import androidx.room.*

//Это дао для репозиториев пользователя
@Dao
interface RepositoryDao {

    //В функциях insert через аргумент аннотации onConflict указываем, что при возникновении конфликта
    //по первичному ключу надо заменить старое значение новым.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomGitHubRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: RoomGitHubRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomGitHubRepository>)

    @Update
    fun update(user: RoomGitHubRepository)

    @Update
    fun update(vararg users: RoomGitHubRepository)

    @Update
    fun update(users: List<RoomGitHubRepository>)

    @Delete
    fun delete(user: RoomGitHubRepository)

    @Delete
    fun delete(vararg users: RoomGitHubRepository)

    @Delete
    fun delete(users: List<RoomGitHubRepository>)

    @Query("SELECT * FROM RoomGitHubRepository")
    fun getAll(): List<RoomGitHubRepository>

    //Всё аналогично, но в случае поиска по пользователю используется поле userId — внешний ключ.
    @Query("SELECT * FROM RoomGitHubRepository WHERE userId = :userId")
    fun findForUser(userId: String): List<RoomGitHubRepository>

}