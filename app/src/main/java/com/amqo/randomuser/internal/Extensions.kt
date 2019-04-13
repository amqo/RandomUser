package com.amqo.randomuser.internal

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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

fun EditText.afterTextChanged(
    afterTextChanged: (String) -> Unit
) {
    this.addTextChangedListener(object : TextWatcher {
        private var handler = Handler()
        private val DELAY: Long = 1000
        private val runnable = Runnable {
            afterTextChanged.invoke(text.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, DELAY)
        }
    })
}