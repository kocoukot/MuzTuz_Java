package com.artline.muztus.ui

import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.artline.muztus.billing_feature.AdsService
import com.artline.muztus.databinding.ActivityMainBinding
import com.artline.muztus.sounds.GameSound
import com.artline.muztus.sounds.GameSoundPlay
import com.artline.muztus.sounds.MusicPlayerService
import com.artline.muztus.sounds.SoundsPlayerService
import com.muztus.core.ext.SupportInfoBar
import com.muztus.core.ext.castSafe
import com.muztus.core_mvi.AdsActivity
import com.muztus.core_mvi.UpdateCoins
import com.muztus.domain_layer.model.IGameSound
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), UpdateCoins, GameSoundPlay, AdsActivity {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    private val soundsPlayerService = SoundsPlayerService()
    private val musicPlayerService: MusicPlayerService = MusicPlayerService(this)
    private lateinit var adsClient: AdsService

    private val navHost by lazy {
        supportFragmentManager.findFragmentById(binding.navHostView.id)
            .castSafe<NavHostFragment>()
    }

    private val currentVisibleFragment: Fragment?
        get() = navHost?.childFragmentManager?.fragments?.first()

    private val onBackStackChangedListener by lazy {
        FragmentManager.OnBackStackChangedListener {
            binding.infoLayout.isVisible = currentVisibleFragment is SupportInfoBar
        }
    }

    override fun onResume() {
        super.onResume()
        FragmentManager.OnBackStackChangedListener { }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityMainBinding.inflate(layoutInflater)
                .also { binding = it }
                .root
        )

        navHost?.apply {
            childFragmentManager.addOnBackStackChangedListener(onBackStackChangedListener)
        }


        with(binding) {
            menuMusic.apply {
                isActivated = true
                setOnClickListener {
                    if (isActivated) musicPlayerService.pause() else musicPlayerService.resume()
                    viewModel.soundChange(IGameSound.GameMusic)
                }
            }

            menuSound.apply {
                isActivated = true
                setOnClickListener {
                    playGameSound(if (isActivated) GameSound.SoundOnMusic else GameSound.SoundOffMusic)
                    viewModel.soundChange(IGameSound.GameSound)
                }
            }
        }

        adsClient = AdsService(this, this::onCoinsGot)

        observeLiveData()
    }

    override fun onCoinsGot(amount: Int) {
        viewModel.addCoins(amount)
        lifecycleScope.launch {
            delay(500)
            playGameSound(GameSound.SoundGotCoins)
        }
    }


    private fun observeLiveData() {
        viewModel.coins.observe(this) { mainInfo ->
//            binding.menuStars.text = mainInfo.starsAmount.toString()

            viewAnimator(
                binding.menuStars.text.toString().toInt(),
                mainInfo.starsAmount,
                binding.menuStars
            )
            viewAnimator(
                binding.menuCoins.text.toString().toInt(),
                mainInfo.coinsAmount,
                binding.menuCoins
            )
        }

        viewModel.sounds.observe(this) { soundsInfo ->
            println("sound activity $soundsInfo")

            binding.menuMusic.apply {
                isActivated = soundsInfo.musicState.soundState()
                if (isActivated) {
                    musicPlayerService.start()
                }
            }

            binding.menuSound.apply {
                isActivated = soundsInfo.soundState.soundState()
            }
        }
    }

    private fun viewAnimator(first: Int, second: Int, view: TextView) {
        val animator = ValueAnimator.ofInt(
            first,
            second
        ) //0 is min number, 600 is max number
        animator.duration = 300 //Duration is in milliseconds
        animator.addUpdateListener { animation ->
            view.text = (animation.animatedValue.toString())
        }
        animator.start()
    }


    fun infoBarVisibility(isVisible: Boolean) {
        binding.infoLayout.isVisible = isVisible
    }

    override fun updateCoins() {
        viewModel.updateCoins()
    }

    override fun playGameSound(soundType: GameSound) {
        soundsPlayerService.start(this@MainActivity, soundType)
    }


    override fun showAd() {
        adsClient.showAd(this)
    }
}


