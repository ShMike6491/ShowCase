package org.example.showcase.common.ui

import com.arkivanov.decompose.Cancellation
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.Store

fun <T : Any> Store<*, T, *>.asValue(): Value<T> = object : Value<T>() {
    override val value: T get() = state

    override fun subscribe(observer: (T) -> Unit): Cancellation {
        val mviObserver = com.arkivanov.mvikotlin.core.rx.observer(onNext = observer)
        val disposable = states(mviObserver)

        return Cancellation {
            disposable.dispose()
        }
    }
}