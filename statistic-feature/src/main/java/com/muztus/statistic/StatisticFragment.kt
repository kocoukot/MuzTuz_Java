package com.muztus.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.muztus.statistic.databinding.ActivityStatistikaBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class StatisticFragment : Fragment() {

    private val viewModel: StatisticViewModel by viewModel()

    private var _binding: ActivityStatistikaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityStatistikaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.statistic.observe(viewLifecycleOwner) { statisticData ->
            with(binding) {
                levelSolved.text =
                    getString(R.string.statistic_levels_passed, statisticData.levelsPassed)
            }
        }
    }
}
