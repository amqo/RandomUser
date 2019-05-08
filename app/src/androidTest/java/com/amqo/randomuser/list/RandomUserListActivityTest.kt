package com.amqo.randomuser.list

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.amqo.randomuser.EspressoUtils
import com.amqo.randomuser.EspressoUtils.getCountFromRecyclerView
import com.amqo.randomuser.R
import com.amqo.randomuser.ui.list.RandomUserListActivity
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RandomUserListActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<RandomUserListActivity>
            = ActivityTestRule(RandomUserListActivity::class.java)

    @Test
    fun clickOnUserTest() {
        onView(withId(R.id.item_list)).perform(RecyclerViewActions
            .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

    @Test
    fun swipeToRemoveUserTest() {
        val previousListCount = getCountFromRecyclerView(R.id.item_list)

        onView(withId(R.id.item_list)).perform(RecyclerViewActions
            .actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeLeft()))

        val newListCount = getCountFromRecyclerView(R.id.item_list)
        assertEquals(previousListCount - 1, newListCount)
    }

    @Test
    fun clickOnUserPhoneTest() {
        onView(withId(R.id.item_list)).perform(RecyclerViewActions
            .actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, EspressoUtils.clickChildViewWithId(R.id.user_phone_text)))
    }
}