package com.artline.muztus.data.repo

import com.artline.muztus.data.SharedPreferencesStorage
import com.muztus.domain_layer.model.HintModel
import com.muztus.domain_layer.repos.HintsRepository

class HintsRepositoryImpl(
    private val sharedPreferencesStorage: SharedPreferencesStorage
) : HintsRepository {

//    override fun getLevelHints(level: GameLevelModel): List<HintModel> {
//    }

    override fun useHint(usedHint: HintModel): List<HintModel> {
        TODO("Not yet implemented")
    }

}