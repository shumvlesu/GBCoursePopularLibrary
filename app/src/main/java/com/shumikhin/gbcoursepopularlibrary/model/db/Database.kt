package com.shumikhin.gbcoursepopularlibrary.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Весь класс помечается аннотацией @Database, где массивом перечисляются сущности и указывается
//версия базы. Версионирование используется для обеспечения работы механизма миграций, но о них
//поговорим позже.
@Database(
    entities = [RoomGithubUser::class, RoomGithubRepository::class],
    version = 1
)
// Database: точка доступа к соединению с базой данных.
//Должна удовлетворять следующим условиям:
//● быть абстрактным наследником RoomDatabase;
//● включать список сущностей, хранящихся в базе (RoomGithubUser,RoomGithubRepository);
//● иметь абстрактный метод (ы), который не имеет аргументов и возвращает класс,
//помеченный аннотацией @Dao.(userDao, repositoryDao)
//В рантайме Database можно получить, вызвав Room.databaseBuilder() или
//Room.inMemoryDatabaseBuilder().
abstract class Database : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val repositoryDao: RepositoryDao

    companion object {

        private const val DB_NAME = "database.db"
        private var instance: com.shumikhin.gbcoursepopularlibrary.model.db.Database? = null

        fun getInstance() = instance
            ?: throw RuntimeException("Database has not been created. Please call create(context)")

        fun create(context: Context?) {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context!!,
                    com.shumikhin.gbcoursepopularlibrary.model.db.Database::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }

}