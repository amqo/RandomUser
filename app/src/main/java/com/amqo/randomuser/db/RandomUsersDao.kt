package com.amqo.randomuser.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amqo.randomuser.db.entity.RandomUserEntry

@Dao
interface RandomUsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(randomUsersEntries: List<RandomUserEntry>)

    @Query("select * from random_user order by random() limit :number ")
    fun getRandomUsers(number: Int): LiveData<List<RandomUserEntry>>

    @Query("select * from random_user where user_uuid == :id ")
    fun getRandomUserWithId(id: String): LiveData<RandomUserEntry>

    @Query("DELETE FROM random_user")
    fun deleteAll()
}