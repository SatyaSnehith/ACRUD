package com.satyasnehith.acrud.core.network

import com.satyasnehith.acrud.core.network.model.ErrorRes
import com.satyasnehith.acrud.core.network.model.SuccessRes
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.net.ConnectException
import java.net.UnknownHostException

sealed class Result<T: Any> {
    class Success<T: Any>(val data: T): Result<T>()
    open class Failure<T: Any>(val error: String): Result<T>()
    class NetworkError<T: Any>(error: String): Failure<T>(error)
}

fun <T: Any> Result<T>.asKotlinResult(): kotlin.Result<T> {
    return when(this) {
        is Result.Success -> kotlin.Result.success(data)
        is Result.Failure -> kotlin.Result.failure(Exception(error))
    }
}

fun <T: Any> kotlin.Result<T>.asResult(): Result<T> {
    val data = getOrNull()
    return if (data != null) Result.Success(data)
    else Result.Failure(exceptionOrNull()?.message.orEmpty())
}

fun Result<SuccessRes>.getMessage(): String {
    return when (this) {
        is Result.Success -> data.message
        is Result.Failure -> error
    }
}

fun <T: Any> Result<T>.asSuccessOrNull(): Result.Success<T>? {
    return this as? Result.Success
}

var moshi: Moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

inline fun <reified T : Any> fromJson(json: String): T? {
    return try {
        moshi
            .adapter(T::class.java)
            .fromJson(json)
    }catch (e: Exception) {
        null
    }
}

suspend fun <T: Any> Call<T>.executeForResult(): Result<T> = withContext(Dispatchers.IO) {
    val response = try {
        execute()
    } catch (e: Exception) {
        val error = when(e) {
            is ConnectException -> "Unable to reach server"
            is UnknownHostException -> "Unable to reach server"
            else -> "Unknown Exception"
        }
        return@withContext Result.NetworkError(error)
    }
    val body = response.body()
    if (body != null) return@withContext Result.Success(body)
    val errorBody = response.errorBody() ?: return@withContext Result.Failure("Unknown errorBody is null")
    val httpCode = response.code()
    val message = fromJson<ErrorRes>(errorBody.toString())?.error ?: errorBody.toString()
    return@withContext when(httpCode) {
        400 -> Result.Failure(message)
        else -> Result.Failure("Unknown")
    }
}

suspend fun <T: Any> Call<T>.executeForSuccessData(): T? = (executeForResult() as? Result.Success)?.data
