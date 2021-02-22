package com.aroldan.marvelapp.common.di.data

import com.aroldan.marvelapp.common.data.MarvelApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    single {
        get<Retrofit>()
            .create(MarvelApi::class.java)
    }

}
