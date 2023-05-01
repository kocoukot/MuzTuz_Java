package com.artline.muztus.sounds

/**
 *[SoundWin] - game level finish sound
 *[SoundWrongAnswer] - game level wrong answer
 *[SoundWarningView] - game alert appear sound
 *[SoundSpendMoney] - spend money sound
[SoundResetAll] - reset game statistic
[SoundOpenPremia] - open new premia sound
[SoundOnMusic] / [SoundOffMusic] - turn on / off sound effects
[SoundGotCoins] - received conins sound
[SoundGameover] - pass level heint use sound
 */


interface GameSound {
    fun soundRes(): Int


    object SoundWin : GameSound {
        override fun soundRes(): Int = R.raw.win
    }

    object SoundWrongAnswer : GameSound {
        override fun soundRes(): Int = R.raw.wrong_answer
    }

    object SoundWarningView : GameSound {
        override fun soundRes(): Int = R.raw.warning_view
    }

    object SoundSpendMoney : GameSound {
        override fun soundRes(): Int = R.raw.spend_money
    }

    object SoundResetAll : GameSound {
        override fun soundRes(): Int = R.raw.reset_all
    }

    object SoundOpenPremia : GameSound {
        override fun soundRes(): Int = R.raw.open_premia

    }

    object SoundOnMusic : GameSound {
        override fun soundRes(): Int = R.raw.on_music

    }

    object SoundOffMusic : GameSound {
        override fun soundRes(): Int = R.raw.off_music

    }

    object SoundGotCoins : GameSound {
        override fun soundRes(): Int = R.raw.bought_coins

    }

    object SoundGameover : GameSound {
        override fun soundRes(): Int = R.raw.answer_help
    }
}

interface GameSoundPlay {
    fun playGameSound(soundType: GameSound)
}



