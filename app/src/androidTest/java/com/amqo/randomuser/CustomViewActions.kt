package com.amqo.randomuser

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher


object EspressoUtils {

    fun clickChildViewWithId(id: Int): ViewAction = object : ViewAction {
        override fun getDescription(): String = ""
        override fun getConstraints(): Matcher<View>? = null
        override fun perform(uiController: UiController?, view: View?) {
            val childView: View? = view?.findViewById(id)
            childView?.performClick()
        }
    }

    fun getCountFromRecyclerView(@IdRes RecyclerViewId: Int): Int {
        var count = 0
        val matcher = object : TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View): Boolean {
                count = (item as RecyclerView).adapter!!.itemCount
                return true
            }
            override fun describeTo(description: Description) {}
        }
        onView(allOf(withId(RecyclerViewId), isDisplayed())).check(matches(matcher))
        val result = count
        count = 0
        return result
    }
}