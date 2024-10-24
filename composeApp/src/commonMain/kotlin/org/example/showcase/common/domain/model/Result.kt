package org.example.showcase.common.domain.model

sealed interface Result<out D, out E: IError> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: IError>(val error: E): Result<Nothing, E>
}

inline fun <T, E: IError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: IError> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

inline fun <T, E: IError> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: IError> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}

typealias EmptyResult<E> = Result<Unit, E>