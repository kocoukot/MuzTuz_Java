package com.muztus.domain_layer.repos

import com.muztus.domain_layer.model.HintModel

interface HintsRepository {

//    fun getLevelHints(level: GameLevelModel) : List<HintModel>

    fun useHint(usedHint: HintModel): List<HintModel>
}