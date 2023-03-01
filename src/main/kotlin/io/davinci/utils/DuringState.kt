package io.davinci.utils

import io.davinci.domain.models.network.NetworkState

suspend fun <T, D> NetworkState<T>.duringState(success: suspend (T) -> D, error: (String) -> D): D {
  return when (this) {
    is NetworkState.Success -> success(this.data)
    is NetworkState.Error -> error(this.message)
  }
}
