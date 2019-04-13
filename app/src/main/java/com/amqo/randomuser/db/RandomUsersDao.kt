package com.amqo.randomuser.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amqo.randomuser.db.entity.RandomUserEntry

@Dao
interface RandomUsersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(randomUsersEntries: List<RandomUserEntry>)

    @Query("select * from random_users where removed == 0")
    fun getAllPaged(): DataSource.Factory<Int, RandomUserEntry>

    @Query("select * from random_users where user_uuid == :id ")
    fun getWithId(id: String): LiveData<RandomUserEntry>

    @Query("update random_users set removed = 1 where user_uuid == :id")
    fun deleteWithId(id: String)
}