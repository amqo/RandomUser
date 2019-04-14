package com.amqo.randomuser.internal

import android.os.Handler
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
        private val DELAY: Long = 1000
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

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    GlideApp.with(context).load(url)
        .placeholder(R.drawable.ic_account_circle_black_60dp).into(this)
}

@BindingAdapter("circleImageUrl")
fun ImageView.setCircleImageUrl(url: String?) {
    GlideApp.with(context).load(url).circleCrop()
        .placeholder(R.drawable.ic_account_circle_black_60dp).into(this)
}