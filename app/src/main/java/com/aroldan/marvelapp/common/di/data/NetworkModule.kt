package com.aroldan.marvelapp.common.di.data

import com.aroldan.marvelapp.common.Constant
import com.aroldan.marvelapp.common.data.MarvelIncerceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    factory {
        OkHttpClient.Builder()
            .addInterceptor(
                MarvelIncerceptor(Pair(Constant.PUBLIC_API_KEY, Constant.PRIVATE_API_KEY))
            )
            .build()
    }

    factory {
        Retrofit.Builder()
            .client(get())
            .baseUrl(Constant.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
