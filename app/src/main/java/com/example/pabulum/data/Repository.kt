package com.example.pabulum.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteData,
    localDataSource: LocalData
) {

    val remote = remoteDataSource
    val local = localDataSource

}