package com.aroldan.marvelapp.common.di.data

import com.aroldan.marvelapp.common.data.CharacterRepository
import com.aroldan.marvelapp.common.data.mapper.CharacterEntityMapper
import com.aroldan.marvelapp.common.data.mapper.ComicEntityMapper
import com.aroldan.marvelapp.common.domain.CharacterRepositoryContract
import org.koin.dsl.module

val repositoryModule = module {

    factory {
        ComicEntityMapper()
    }

    factory {
        CharacterEntityMapper(get())
    }

    single<CharacterRepositoryContract> {
        CharacterRepository(
                marvelApi = get(),
                characterEntityMapper = get()
        )
    }

}