package com.aroldan.marvelapp.common.di.domain

import com.aroldan.marvelapp.feature.character.domain.GetCharacterDetailsUseCase
import com.aroldan.marvelapp.feature.characterlist.domain.GetCharacterListUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val useCaseModule = module {

    single {
        Dispatchers.IO
    }

    factory {
        GetCharacterDetailsUseCase(
            characterRepository = get(),
            dispatcher = get()
        )
    }

    factory {
        GetCharacterListUseCase(
            characterRepository = get(),
            dispatcher = get()
        )
    }

}
