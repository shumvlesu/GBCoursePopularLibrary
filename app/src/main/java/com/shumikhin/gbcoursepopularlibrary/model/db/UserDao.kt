package com.shumikhin.gbcoursepopularlibrary.model.db

import androidx.room.*

//Это дао для пользователей
@Dao
interface UserDao {

    //В функциях insert через аргумент аннотации onConflict указываем, что при возникновении конфликта
    //по первичному ключу надо заменить старое значение новым.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomGitHubUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: RoomGitHubUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomGitHubUser>)

    @Update
    fun update(user: RoomGitHubUser)

    @Update
    fun update(vararg users: RoomGitHubUser)

    @Update
    fun update(users: List<RoomGitHubUser>)

    @Delete
    fun delete(user: RoomGitHubUser)

    @Delete
    fun delete(vararg users: RoomGitHubUser)

    @Delete
    fun delete(users: List<RoomGitHubUser>)

    @Query("SELECT * FROM RoomGitHubUser")
    fun getAll(): List<RoomGitHubUser>

    @Query("SELECT * FROM RoomGitHubUser WHERE login = :login LIMIT 1")
    fun findByLogin(login: String): RoomGitHubUser?

}