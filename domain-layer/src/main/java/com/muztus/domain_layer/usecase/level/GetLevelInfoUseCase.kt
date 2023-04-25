package com.muztus.domain_layer.usecase.level

import com.muztus.domain_layer.repos.GameRepository

class GetLevelInfoUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(premiumIndex: Int, levelIndex: Int) =
        gameRepository.getLevelInfo(premiumIndex, levelIndex)
}