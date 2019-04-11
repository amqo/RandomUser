package com.amqo.randomuser.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amqo.randomuser.data.network.response.*

@Entity(tableName = "random_user")
data class RandomUserEntry(
    @PrimaryKey @Embedded(prefix = "user_")
    val login: Login,
    val cell: String,
    val email: String,
    val gender: String,
    @Embedded(prefix ="location_")
    val location: Location,
    @Embedded(prefix ="name_")
    val name: Name,
    val phone: String,
    @Embedded(prefix = "picture_")
    val picture: Picture,
    @Embedded(prefix = "registered_")
    val registered: Registered
) {
    fun getId(): String {
        return login.uuid
    }

    fun getFullName() : String {
        return name.first + " " +  name.last
    }
}