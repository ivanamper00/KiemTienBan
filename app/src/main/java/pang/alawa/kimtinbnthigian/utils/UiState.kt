package pang.alawa.kimtinbnthigian.utils

sealed class UiState<out T> {
    data class Success<out T>(val data: T): UiState<T>()
    data class Error(val exception: Throwable): UiState<Nothing>()
}