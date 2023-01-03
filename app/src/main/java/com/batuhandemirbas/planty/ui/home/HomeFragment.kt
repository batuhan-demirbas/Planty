package com.batuhandemirbas.planty.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.batuhandemirbas.planty.R
import com.batuhandemirbas.planty.data.model.Feeds
import com.batuhandemirbas.planty.databinding.FragmentHomeBinding
import com.batuhandemirbas.planty.ui.info.InfoBottomSheetFragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.coroutines.launch

private var _binding: FragmentHomeBinding? = null
private val binding get() = _binding!!

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: HomeViewModel by viewModels()

        viewModel.getUserPlantsData()
        viewModel.getPlantyLastData(requireActivity())

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    // Update UI elements

                    val userPlant = it.userPlant

                    val plantyData = Gson().fromJson(it.plantyData, Feeds::class.java)

                    Glide.with(requireContext()).load("${userPlant?.image}").into(binding.image)

                    with(binding) {
                        name.text = userPlant?.name
                        type.text = userPlant?.type

                        temperature.text = plantyData?.feeds?.last()?.field4
                        humidity.text = plantyData?.feeds?.last()?.field3
                        moisture.text = plantyData?.feeds?.last()?.field2

                        waterLevel.text = it.userPlant?.waterLevel.toString()

                        when (plantyData?.feeds?.last()?.field1?.toInt()) {

                            in 480..600 -> binding.tankLevelImage.setImageDrawable(
                                resources.getDrawable(
                                    R.drawable.tank_level_100
                                )
                            )
                            in 401..479 -> binding.tankLevelImage.setImageDrawable(
                                resources.getDrawable(
                                    R.drawable.tank_level_75
                                )
                            )
                            in 301..401 -> binding.tankLevelImage.setImageDrawable(
                                resources.getDrawable(
                                    R.drawable.tank_level_50
                                )
                            )
                            in 201..300 -> binding.tankLevelImage.setImageDrawable(
                                resources.getDrawable(
                                    R.drawable.tank_level_25
                                )
                            )
                            in 0 .. 200-> binding.tankLevelImage.setImageDrawable(resources.getDrawable(R.drawable.tank_level_0))

                        }

                    }

                    if ((userPlant?.moisture?.last()?.toInt() ?: 0) <= 64) {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val infoButtomSheet = InfoBottomSheetFragment()


        binding.infoButton.setOnClickListener {
            infoButtomSheet.show(parentFragmentManager, "InfoBottomSheetFragment")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}