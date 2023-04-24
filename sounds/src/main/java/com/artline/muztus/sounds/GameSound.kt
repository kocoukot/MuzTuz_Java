package com.artline.muztus.sounds

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

    object SoundAppearView : GameSound {
        override fun soundRes(): Int = R.raw.appearing_view

    }

    object SoundGameover : GameSound {
        override fun soundRes(): Int = R.raw.answer_help
    }
}

interface GameSoundPlay {
    fun playGameSound(soundType: GameSound)
}



