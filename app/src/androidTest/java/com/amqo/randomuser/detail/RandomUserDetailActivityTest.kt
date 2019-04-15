package com.amqo.randomuser.detail

import android.content.Intent
import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.amqo.randomuser.R
import com.amqo.randomuser.RandomUserMockGenerator
import com.amqo.randomuser.data.db.RandomUsersDatabase
import com.amqo.randomuser.ui.detail.RandomUserDetailActivity
import com.amqo.randomuser.ui.detail.RandomUserDetailFragment
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RandomUserDetailActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<RandomUserDetailActivity>
            = ActivityTestRule(RandomUserDetailActivity::class.java, true, false)

    private lateinit var randomUsersDataBase: RandomUsersDatabase

    @Before
    fun setup() {
        randomUsersDataBase = Room.inMemoryDatabaseBuilder(
            getInstrumentation().context,
            RandomUsersDatabase::class.java).build()

        val mockedUser = RandomUserMockGenerator.mockUser()
        randomUsersDataBase.randomUsersDao().insert(mutableListOf(mockedUser))
        val intent = Intent()
        intent.putExtra(RandomUserDetailFragment.ARG_USER_ID, mockedUser.getId())
        intent.putExtra(RandomUserDetailFragment.ARG_USER_NAME, mockedUser.getFullName())
        activityRule.launchActivity(intent)
    }

    @After
    fun closeDb() {
        randomUsersDataBase.close()
    }

    @Test
    fun clickOnImageTest() {
        onView(withId(R.id.user_image)).perform(click())
    }

    // TODO Override Kodein dependency for randomUserDao to make it return the mockedUser inside the detail fragment

//    @Test
//    fun checkMailTest() {
//        onView(withId(R.id.user_mail)).check(matches(withText(dummyMail)))
//    }
//
//    @Test
//    fun checkGenderTest() {
//        onView(withId(R.id.user_gender)).check(matches(withText(dummyGender)))
//    }
}