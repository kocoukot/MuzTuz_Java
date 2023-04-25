package com.muztus.domain_layer.usecase.level

import com.muztus.database.LevelInfoEntity
import com.muztus.domain_layer.repos.GameRepository

class SetLevelInfoUseCase(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(levelInfo: LevelInfoEntity) = gameRepository.setLevelInfo(levelInfo)
}