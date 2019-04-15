package com.amqo.randomuser

import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.data.network.response.*
import java.util.*

object RandomUserMockGenerator {

    val dummyUserId = UUID.randomUUID().toString()
    const val dummyMail = "dummy.mail@example.com"
    const val dummyCity = "dummyCity"
    const val dummyState = "dummyState"
    const val dummyStreet = "dummyStreet"
    const val dummyPicture = "http://writingexercises.co.uk/images2/randomimage/boat.jpg"
    const val dummyRegisteredDate = "2005-02-12T00:43:02Z"
    const val dummyGender = "male"

    fun mockUser() = RandomUserEntry(
        Login(dummyUserId), "", dummyMail, dummyGender,
        Location(dummyCity, dummyState, dummyStreet, Coordinates(2.4, 31.9)),
        Name("dummy", "name", ""), "", Picture(dummyPicture, "", ""),
        Registered(dummyRegisteredDate), false
    )
}