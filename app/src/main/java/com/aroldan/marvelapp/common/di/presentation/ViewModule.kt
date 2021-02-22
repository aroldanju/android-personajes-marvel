package com.aroldan.marvelapp.common.di.presentation

import com.aroldan.marvelapp.common.Constant
import com.aroldan.marvelapp.common.data.mapper.ComicEntityMapper
import com.aroldan.marvelapp.common.presentation.mapper.CharacterDisplayMapper
import com.aroldan.marvelapp.common.presentation.mapper.ComicDisplayMapper
import com.aroldan.marvelapp.common.presentation.mapper.ErrorDisplayMapper
import com.aroldan.marvelapp.feature.character.presentation.CharacterViewModel
import com.aroldan.marvelapp.feature.characterlist.presentation.CharacterListViewModel
import com.aroldan.marvelapp.feature.characterlist.presentation.PageStatus
import com.aroldan.marvelapp.feature.splash.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {

    factory {
        ComicDisplayMapper()
    }

    factory {
        CharacterDisplayMapper(get())
    }

    factory {
        ErrorDisplayMapper()
    }

    factory {
        PageStatus(
            offset = 0,
            limit = Constant.PAGINATION_SIZE,
            characters = mutableListOf())
    }

    viewModel {
        CharacterViewModel(
            getCharacterDetailsUseCase = get(),
            characterDisplayMapper = get()
        )
    }

    viewModel {
        CharacterListViewModel(
            getCharacterListUseCase = get(),
            characterDisplayMapper = get(),
            pageStatus = get()
        )
    }

    viewModel {
        SplashViewModel()
    }

}