package com.satyasnehith.acud.core.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import java.net.ConnectException

sealed class Result<T: Any>

class Success<T: Any>(val data: T): Result<T>()

open class Failure<T: Any>(val error: String): Result<T>()

class NetworkException<T: Any>(error: String): Failure<T>(error)

var moshi: Moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

fun <T: Any> Call<T>.executeForResult(): Result<T> {
    val response = try {
        execute()
    } catch (e: ConnectException) {
        return NetworkException("Unable to reach server")
    }
    val body = response.body()
    if (body != null) return Success(body)
    val errorBody = response.errorBody() ?: return Failure("Unknown errorBody is null")
    val httpCode = response.code()
    val message = moshi.adapter(com.satyasnehith.acud.core.network.model.ErrorRes::class.java).fromJson(errorBody.string())?.error.toString()
    return when(httpCode) {
        400 -> Failure(message)
        else -> Failure("Unknown")
    }
}

fun <T: Any> Call<T>.executeForSuccessData(): T? = (executeForResult() as? Success)?.data