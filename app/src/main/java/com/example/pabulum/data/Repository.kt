package com.example.pabulum.data

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteData,
    localDataSource: LocalData
) {

    val remote = remoteDataSource
    val local = localDataSource

}