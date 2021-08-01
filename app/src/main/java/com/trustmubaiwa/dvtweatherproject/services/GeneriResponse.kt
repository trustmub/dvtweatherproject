package com.trustmubaiwa.dvtweatherproject.services

sealed class GenericResponse<out T : Any>{
    data class Success<out T : Any>(val output : T?) : GenericResponse<T>()
    data class Error(val exception: Exception?)  : GenericResponse<Nothing>()
}