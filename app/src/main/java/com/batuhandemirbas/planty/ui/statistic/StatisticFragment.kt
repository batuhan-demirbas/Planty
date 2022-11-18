package com.batuhandemirbas.planty.ui.statistic

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.batuhandemirbas.planty.R
import com.batuhandemirbas.planty.databinding.FragmentStatisticBinding
import com.batuhandemirbas.planty.ui.home.HomeViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.launch

private var _binding: FragmentStatisticBinding? = null
private val binding get() = _binding!!

class StatisticFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: StatisticViewModel by viewModels()

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
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lineEntry = arrayListOf<Entry>()
        val weekdayModel = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

        with(lineEntry) {
            add(Entry(0f, 20f))
            add(Entry(1f, 24f))
            add(Entry(2f, 22f))
            add(Entry(3f, 28f))
            add(Entry(4f, 24f))
            add(Entry(5f, 22f))
            add(Entry(6f, 26f))
        }

        val lineDataSet = LineDataSet(lineEntry, "")
        lineDataSet.setValueTextColors(mutableListOf(Color.TRANSPARENT))
        lineDataSet.setColor(R.color.secondary_400)
        lineDataSet.setCircleColor(Color.BLUE)
        lineDataSet.lineWidth = 3f
        lineDataSet.circleRadius = 5f

        val lineData = LineData(lineDataSet)
        binding.lineChart.data = lineData
        binding.lineChart.description.isEnabled = false
        binding.lineChart.getAxis(YAxis.AxisDependency.RIGHT).isEnabled = false
        binding.lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(weekdayModel)
        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.lineChart.xAxis.setDrawGridLines(false)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}