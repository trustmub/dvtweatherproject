package com.trustmubaiwa.dvtweatherproject.common

import com.trustmubaiwa.dvtweatherproject.services.GenericResponse
import retrofit2.Response

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): T? {
        return when (val result = mainApiResponse(call)) {
            is GenericResponse.Success -> result.output
            is GenericResponse.Error -> result.exception as T
        }
    }

    private suspend fun <T : Any> mainApiResponse(call: suspend () -> Response<T>): GenericResponse<T> {
        val response = call.invoke()
        return if (response.isSuccessful)
            GenericResponse.Success(response.body())
        else
            GenericResponse.Error(Exception("StatusCode: ${response.code()}, message: ${response.message()}"))
    }
}