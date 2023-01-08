package com.batuhandemirbas.planty.ui.statistics

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import java.time.LocalDate

private var _binding: FragmentStatisticsBinding? = null
private val binding get() = _binding!!

class StatisticsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: StatisticsViewModel by viewModels()
        var moistureEntry = arrayListOf<Entry>()
        var temperatureEntry = arrayListOf<Entry>()
        var humidityEntry = arrayListOf<Entry>()

        val thisWeek: ArrayList<LocalDate> = arrayListOf()

        for (i in 0..6) {
            val date = LocalDate.now().minusDays(i.toLong())
            thisWeek.add(date)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    // Update UI elements

                    val moistureArray: ArrayList<String> =
                        arrayListOf("10", "10", "10", "10", "10", "10", "10")
                    val temperatureArray: ArrayList<String> =
                        arrayListOf("10", "10", "10", "10", "10", "10", "10")
                    val humidityArray: ArrayList<String> =
                        arrayListOf("10", "10", "10", "10", "10", "10", "10")

                    it.plantyData?.let {
                        moistureArray.clear()
                        temperatureArray.clear()
                        humidityArray.clear()
                    }

                    it.plantyData?.feeds?.forEach { plantData ->

                        moistureArray.add(plantData.field2)
                        temperatureArray.add(plantData.field4)
                        humidityArray.add(plantData.field3)

                    }

                    repeat(7) {
                        if (moistureArray.size < 7) {
                            moistureArray.add(0, "0")
                        }

                        if (humidityArray.size < 7) {
                            humidityArray.add(0, "0")
                        }

                        if (temperatureArray.size < 7) {
                            temperatureArray.add(0, "0")
                        }

                    }

                    //moistureArray.reverse()
                    //humidityArray.reverse()
                    //temperatureArray.reverse()

                    moistureEntry = applyChartData(moistureArray, moistureEntry)
                    temperatureEntry = applyChartData(temperatureArray, temperatureEntry)
                    humidityEntry = applyChartData(humidityArray, humidityEntry)

                    applyChartSetting(
                        binding.moistureChart,
                        moistureEntry,
                        ContextCompat.getColor(requireContext(), R.color.secondary_400),
                        ContextCompat.getColor(requireContext(), R.color.secondary_400),
                        ContextCompat.getDrawable(requireContext(), R.drawable.gradient_blue)

                    )

                    applyChartSetting(
                        binding.humidityChart,
                        humidityEntry,
                        ContextCompat.getColor(requireContext(), R.color.primary_400),
                        ContextCompat.getColor(requireContext(), R.color.primary_400),
                        ContextCompat.getDrawable(requireContext(), R.drawable.gradient_green)

                    )

                    applyChartSetting(
                        binding.temperatureChart,
                        temperatureEntry,
                        ContextCompat.getColor(requireContext(), R.color.purple),
                        ContextCompat.getColor(requireContext(), R.color.purple),
                        ContextCompat.getDrawable(requireContext(), R.drawable.gradient_purple)

                    )

                }
            }
        }

        viewModel.getWeeklyAverageData(
            requireActivity(),
            LocalDate.now().minusDays(7).toString(),
            LocalDate.now().toString()
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root

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
        val weekdayModel: ArrayList<String> = arrayListOf()

        var currentDate = LocalDate.now().dayOfWeek

        for (i in 0..6) {
            currentDate = currentDate.minus(1)
            weekdayModel.add(
                currentDate.getDisplayName(
                    java.time.format.TextStyle.SHORT,
                    java.util.Locale.getDefault()
                )
            )
        }

        weekdayModel.reverse()

        chart.clear()

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

    private fun applyChartData(
        dataArray: ArrayList<String>,
        chartEntry: ArrayList<Entry>
    ): ArrayList<Entry> {
        val lastIndex = dataArray.lastIndex
        var flag = 0f

        chartEntry.clear()
        for (i in lastIndex.minus(6)..lastIndex) {
            chartEntry.add(Entry(flag, dataArray[i].toFloat()))
            flag++
        }

        return chartEntry
    }

}