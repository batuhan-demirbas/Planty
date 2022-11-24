package com.batuhandemirbas.planty.ui.home

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
import com.batuhandemirbas.planty.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

private var _binding: FragmentHomeBinding? = null
private val binding get() = _binding!!

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: HomeViewModel by viewModels()

        viewModel.getUserPlantsData()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    // Update UI elements

                    val userPlant = it.userPlant

                    Glide.with(requireContext()).load("${userPlant?.image}").into(binding.image)

                    with(binding) {
                        name.text = userPlant?.name
                        type.text = userPlant?.type

                        temperature.text = userPlant?.temperature?.last().toString()
                        humidity.text = userPlant?.humidity?.last().toString()
                        moisture.text = userPlant?.moisture?.last()

                        waterLevel.text = it.userPlant?.waterLevel.toString()

                        when(userPlant?.waterLevel) {

                            in 750 .. 1000 -> binding.tankLevelImage.setImageDrawable(resources.getDrawable(R.drawable.tank_level_100))
                            in 500 .. 749 -> binding.tankLevelImage.setImageDrawable(resources.getDrawable(R.drawable.tank_level_75))
                            in 250 .. 499 -> binding.tankLevelImage.setImageDrawable(resources.getDrawable(R.drawable.tank_level_50))
                            in 1 .. 249 -> binding.tankLevelImage.setImageDrawable(resources.getDrawable(R.drawable.tank_level_25))
                            0 -> binding.tankLevelImage.setImageDrawable(resources.getDrawable(R.drawable.tank_level_0))

                        }

                    }

                    if(userPlant?.moisture?.last()?.toInt() ?: 0 <= 64) {
                        with(binding) {
                            happy.visibility = View.GONE
                            thirsty.visibility = View.VISIBLE

                        }

                    } else {
                        with(binding) {
                            happy.visibility = View.VISIBLE
                            thirsty.visibility = View.GONE
                        }
                    }

                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}