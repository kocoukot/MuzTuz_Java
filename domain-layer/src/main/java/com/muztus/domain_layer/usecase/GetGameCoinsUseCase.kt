package com.muztus.domain_layer.usecase

import com.muztus.domain_layer.repos.GameRepository

class GetGameCoinsUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke() = gameRepository.testCoins()
}