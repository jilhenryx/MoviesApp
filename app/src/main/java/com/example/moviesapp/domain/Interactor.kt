package com.example.moviesapp.domain

import kotlinx.coroutines.flow.Flow

abstract class AuthInteractor<in Params, DomainResult> {
    abstract suspend operator fun invoke(params: Params): Flow<DomainResult>
}