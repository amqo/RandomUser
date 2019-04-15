package com.amqo.randomuser.internal

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.amqo.randomuser.R
import com.amqo.randomuser.ui.base.GlideApp
import kotlinx.coroutines.Deferred

fun <T : Any, L : LiveData<out T>> observeForever(
    liveData: L, block: (T) -> Unit
) {
    liveData.observeForever { it?.let(block) }
}

fun <T> LiveData<T>.observeOnce(
    lifecycleOwner: LifecycleOwner, observer: Observer<T>
) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

suspend fun <T : Any, L : Deferred<LiveData<out T>>> LifecycleOwner.consume(
    deferred: L, block: (param: T) -> Unit, blockError: () -> Unit = {}
) {
    deferred.await().observe(this, Observer {
        it?.let(block) ?: blockError()
    })
}

fun SearchView.afterTextChanged(
    afterTextChanged: (String) -> Unit
) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        private var handler = Handler()
        private val DELAY: Long = 800
        private val runnable = Runnable {
            afterTextChanged.invoke(query.toString())
        }
        override fun onQueryTextChange(text: String?): Boolean {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, DELAY)
            return true
        }
        override fun onQueryTextSubmit(text: String?): Boolean {
            afterTextChanged.invoke(text.toString())
            return true
        }
    })
}

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

@BindingAdapter("circleImageUrl")
fun ImageView.setCircleImageUrl(
    url: String?
) {
    GlideApp.with(context).load(url).circleCrop()
        .error(R.drawable.ic_account_circle_black_60dp)
        .placeholder(R.drawable.ic_account_circle_black_60dp).into(this)
}

@BindingAdapter("imageMapUrl")
fun ImageView.setImageMapUrl(
    url: String?
) {
    GlideApp.with(context).load(url)
        .placeholder(R.drawable.ic_map_black_24dp)
        .error(R.drawable.ic_map_black_24dp).into(this)
}

// Private functions

private fun Context.hideKeyboard(
    view: View
) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
}