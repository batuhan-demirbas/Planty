package com.batuhandemirbas.planty.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.batuhandemirbas.planty.databinding.FragmentInfoBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class InfoBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentInfoBottomSheetBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: InfoBottomSheetViewModel by viewModels()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    // Update UI elements

                    with(binding) {
                        textVeiwDate.text = it.plant?.date?.let { date -> viewModel.getPlantAge(date) }
                        textViewWatering.text = it.plant?.water?.frequency
                        textViewHumidity.text = it.plant?.water?.humidity
                        textViewTemperature.text = it.plant?.temperature
                        textViewLighting.text = it.plant?.light
                        textViewInfo.text = it.plant?.info
                    }

                }
            }
        }

        viewModel.getPlantData()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}