package com.satyasnehith.acud.core.network

import com.satyasnehith.acud.core.network.model.ErrorRes
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.net.ConnectException
import java.net.UnknownHostException

sealed class Result<T: Any>

class Success<T: Any>(val data: T): Result<T>()

open class Failure<T: Any>(val error: String): Result<T>()

class NetworkError<T: Any>(error: String): Failure<T>(error)

var moshi: Moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

suspend fun <T: Any> Call<T>.executeForResult(): Result<T> = withContext(Dispatchers.IO) {
    val response = try {
        execute()
    } catch (e: Exception) {
        val error = when(e) {
            is ConnectException ->"Unable to reach server"
            is UnknownHostException -> "Unable to reach server"
            else -> "Unknown Exception"
        }
        return@withContext NetworkError(error)
    }
    val body = response.body()
    if (body != null) return@withContext Success(body)
    val errorBody = response.errorBody() ?: return@withContext Failure("Unknown errorBody is null")
    val httpCode = response.code()
    val message = moshi
        .adapter(ErrorRes::class.java)
        .fromJson(errorBody.string())
        ?.error
        .toString()
    return@withContext when(httpCode) {
        400 -> Failure(message)
        else -> Failure("Unknown")
    }
}

suspend fun <T: Any> Call<T>.executeForSuccessData(): T? = (executeForResult() as? Success)?.data