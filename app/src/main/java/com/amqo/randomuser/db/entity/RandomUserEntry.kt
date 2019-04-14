package com.amqo.randomuser.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amqo.randomuser.data.network.response.*
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "random_users")
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
    val registered: Registered,
    val removed: Boolean = false
) {

    fun getId() : String {
        return login.uuid
    }

    fun getGenderCapitalized(): String {
        return gender.capitalize()
    }

    fun getDateFormatted(): String {
        val originalDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'", Locale.getDefault())
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        return simpleDateFormat.format(originalDateFormat.parse(registered.date))
    }

    fun getFullName() : String {
        return name.first + " " +  name.last
    }
}