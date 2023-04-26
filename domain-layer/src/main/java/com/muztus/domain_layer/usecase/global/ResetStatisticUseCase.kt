package com.muztus.domain_layer.usecase.global

import com.muztus.domain_layer.repos.GameRepository

class ResetStatisticUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke() = gameRepository.resetStatistic()
}
