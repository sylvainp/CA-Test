package com.sylvainp.ca_test.domain.usecases

sealed class Usecase<P, T> {
    abstract suspend fun execute(params:P? = null):UsecaseResponse<T>;
}