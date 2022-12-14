package com.muztus.domain_layer.model

data class GameSoundsInfo(
    val soundState: IGameSound,
    val musicState: IGameSound,
) : TestSound {
    fun changeSound(sound: IGameSound) {
        sound.action(this)
    }

    override fun changeSound() {
        soundState.changeState()
    }

    override fun changeMusic() {
        musicState.changeState()
    }

    override fun toString(): String {

        return "soundState ${soundState.soundState()} musicState ${musicState.soundState()}"
    }
}

interface TestSound {
    fun changeSound()
    fun changeMusic()
}


interface IGameSound {

    fun setSound(isOn: Boolean)

    fun action(test: TestSound)

    fun changeState()

    fun soundState(): Boolean

    abstract class Base : IGameSound {
        private var isSoundOn: Boolean = true
        override fun changeState() {
            isSoundOn = !isSoundOn
        }

        override fun soundState(): Boolean = isSoundOn

        override fun setSound(isOn: Boolean) {
            isSoundOn = isOn
        }
    }

    object GameSound : Base() {
        override fun action(test: TestSound) {
            test.changeSound()
        }
    }

    object GameMusic : Base() {
        override fun action(test: TestSound) {
            test.changeMusic()
        }
    }

}
