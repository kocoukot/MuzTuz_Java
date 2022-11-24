package com.artline.muztus.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.artline.muztus.audio.MusicPlayerService
import com.artline.muztus.databinding.ActivityMainBinding
import com.muztus.core.ext.SupportInfoBar
import com.muztus.core.ext.castSafe
import com.muztus.core_mvi.UpdateCoins
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), UpdateCoins {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

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
        MusicPlayerService.start(this)
        MusicPlayerService.release()
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
        observeLiveData()

        //todo fix remove after test
        binding.imageView5.setOnClickListener {
            viewModel.addCoins()
        }
    }

    private fun observeLiveData() {
        viewModel.coins.observe(this) { mainInfo ->
            binding.menuCoins.text = mainInfo.coinsAmount.toString()
            binding.menuStars.text = mainInfo.starsAmount.toString()
        }
    }


    fun infoBarVisibility(isVisible: Boolean) {
        binding.infoLayout.isVisible = isVisible
    }

    override fun updateCoins() {
        viewModel.updateCoins()
    }
}


