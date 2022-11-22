package com.batuhandemirbas.planty.ui.statistics

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.batuhandemirbas.planty.R
import com.batuhandemirbas.planty.databinding.FragmentStatisticsBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.launch

private var _binding: FragmentStatisticsBinding? = null

private val binding get() = _binding!!

class StatisticsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: StatisticsViewModel by viewModels()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    // Update UI elements
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moistureEntry = arrayListOf<Entry>()

        with(moistureEntry) {
            add(Entry(0f, 20f))
            add(Entry(1f, 24f))
            add(Entry(2f, 22f))
            add(Entry(3f, 28f))
            add(Entry(4f, 24f))
            add(Entry(5f, 22f))
            add(Entry(6f, 26f))
        }

        applyChartSetting(
            binding.moistureChart,
            moistureEntry,
            ContextCompat.getColor(requireContext(), R.color.secondary_400),
            ContextCompat.getColor(requireContext(), R.color.secondary_400),
            ContextCompat.getDrawable(requireContext(), R.drawable.gradient_blue)
        )

        applyChartSetting(
            binding.humidityChart,
            moistureEntry,
            ContextCompat.getColor(requireContext(), R.color.primary_400),
            ContextCompat.getColor(requireContext(), R.color.primary_400),
            ContextCompat.getDrawable(requireContext(), R.drawable.gradient_green)
        )

        applyChartSetting(
            binding.temperatureChart,
            moistureEntry,
            ContextCompat.getColor(requireContext(), R.color.purple),
            ContextCompat.getColor(requireContext(), R.color.purple),
            ContextCompat.getDrawable(requireContext(), R.drawable.gradient_purple)

        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun applyChartSetting(
        chart: com.github.mikephil.charting.charts.LineChart,
        lineEntry: ArrayList<Entry>,
        lineColor: Int,
        holeColor: Int,
        gradient: Drawable?

    ) {
        val lineDataSet = LineDataSet(lineEntry, "")
        val lineData = LineData(lineDataSet)
        val weekdayModel = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

        with(chart) {
            isDragEnabled = false
            isScaleYEnabled = false
            isScaleXEnabled = false
            setTouchEnabled(false)
            data = lineData
            description.isEnabled = false
            getAxis(YAxis.AxisDependency.RIGHT).isEnabled = false
            xAxis.valueFormatter = IndexAxisValueFormatter(weekdayModel)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)

        }

        with(lineDataSet) {
            setValueTextColors(mutableListOf(Color.TRANSPARENT))
            color = lineColor
            setCircleColor(ContextCompat.getColor(requireContext(), R.color.white))
            lineWidth = 1f
            circleHoleColor = holeColor
            circleRadius = 5f
            circleHoleRadius = 3f
            fillDrawable = gradient
            setDrawFilled(true)
            lineWidth = 3f
        }

    }

}