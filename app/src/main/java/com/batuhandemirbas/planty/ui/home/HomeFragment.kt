package com.batuhandemirbas.planty.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
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

        viewModel.getPlantData()
        viewModel.getPlantyLastData(requireActivity())

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    // Update UI elements

                    val userPlant = it.userPlant

                    val plantyData = Gson().fromJson(it.plantyData, Feeds::class.java)

                    Glide.with(requireContext()).load("${userPlant?.photo}").into(binding.image)

                    with(binding) {
                        name.text = userPlant?.name
                        type.text = userPlant?.type

                        temperature.text = plantyData?.feeds?.last()?.field4
                        humidity.text = plantyData?.feeds?.last()?.field3
                        moisture.text = plantyData?.feeds?.last()?.field2

                        val waterMl = plantyData?.feeds?.last()?.field1?.toInt()?.div(3)?.toInt()

                        waterLevel.text = waterMl.toString()

                        when (waterMl) {

                            in 120..200 -> binding.tankLevelImage.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.tank_level_100,
                                    null
                                )
                            )
                            in 81..119 -> binding.tankLevelImage.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.tank_level_75,
                                    null
                                )
                            )
                            in 51..80 -> binding.tankLevelImage.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.tank_level_50,
                                    null
                                )
                            )
                            in 26..50 -> binding.tankLevelImage.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.tank_level_25,
                                    null
                                )
                            )
                            in 0..25 -> binding.tankLevelImage.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.tank_level_0,
                                    null
                                )
                            )

                        }

                    }

                    if ((plantyData?.feeds?.last()?.field2?.toInt() ?: 0) <= 40) {
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
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
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