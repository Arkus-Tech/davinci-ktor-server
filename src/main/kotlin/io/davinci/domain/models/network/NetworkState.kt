package io.davinci.domain.models.network

sealed class NetworkState<out T> {
  data class Success<T>(val data: T) : NetworkState<T>()
  data class Error(val message: String) : NetworkState<Nothing>()
}
