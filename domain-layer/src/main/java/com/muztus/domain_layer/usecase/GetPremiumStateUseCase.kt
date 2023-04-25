package com.muztus.domain_layer.usecase

import com.muztus.domain_layer.repos.GameRepository

class GetPremiumStateUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke() = gameRepository.getDisksInfo()
}