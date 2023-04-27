package com.muztus.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.muztus.core.ext.DateFormatter
import com.muztus.core.levelsdata.premiaImagesList
import com.muztus.statistic.databinding.ActivityStatistikaBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class StatisticFragment : Fragment() {

    private val viewModel: StatisticViewModel by viewModel()

    private var _binding: ActivityStatistikaBinding? = null
    private val binding get() = _binding!!


    private val timeFormatter = DateFormatter()

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
                sumDuration.text = getString(
                    R.string.statistic_summary_duration,
                    timeFormatter.milsecondsToDuration(statisticData.summaryTime)
                )
                levelSolved.text =
                    getString(R.string.statistic_levels_passed, statisticData.levelsPassed)

                fastestText.text = getString(
                    R.string.statistic_fastest_level_text,
                    timeFormatter.milsecondsToDuration(statisticData.fastestLevel.levelDuration)

                )
                fastestImage.setImageResource(premiaImagesList[statisticData.fastestLevel.premiaIndex][statisticData.fastestLevel.levelIndex])

                lowestText.text = getString(
                    R.string.statistic_longest_level_text,
                    timeFormatter.milsecondsToDuration(statisticData.longestLevel.levelDuration)
                )
                lowestImage.setImageResource(premiaImagesList[statisticData.longestLevel.premiaIndex][statisticData.longestLevel.levelIndex])

                helpsUsed.text = getString(R.string.statistic_hints_used, statisticData.hintsUsed)
            }
        }

        viewModel.emptyStata.observe(viewLifecycleOwner) { isEmpty ->
            binding.statisticHead.text =
                getString(if (isEmpty) R.string.statistic_empty_stata else R.string.statistic_statistic_title)
            binding.statisticScroll.isVisible = !isEmpty
        }
    }
}
