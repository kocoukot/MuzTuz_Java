package com.muztus.domain_layer.usecase.global

import com.muztus.domain_layer.model.GameSoundsInfo
import com.muztus.domain_layer.repos.GameRepository

class SetSoundsStateUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(soundState: GameSoundsInfo) = gameRepository.setSoundsState(soundState)
}