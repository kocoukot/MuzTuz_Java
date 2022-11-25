package com.muztus.domain_layer.usecase

import com.muztus.domain_layer.repos.GameRepository

class SetCoinsAmountUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(coinsAmount: Int, starsAmount: Int = 0) =
        gameRepository.setGameCoinsAmount(coinsAmount, starsAmount)
}