package com.goldmansanch.data

/**
 * Created by Abhishek on 27,February,2022 For GolmanSanch
 *
 */
data class Response<out T>(
    val isSuccess: Boolean,
    val code: String? = null,
    val data: T? = null,
    val message: String? = null
)
{
    companion object
    {
        fun <T> success(data: T?): Response<T> =
            Response(isSuccess = true, code = null, data = data, message = null)

        fun <T> failure(code: String, message: String?): Response<T> =
            Response(isSuccess = false, code = code, data = null, message = message)
    }
}
