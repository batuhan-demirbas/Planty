package com.batuhandemirbas.planty.ui.setting

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.batuhandemirbas.planty.databinding.FragmentSettingBinding
import kotlinx.coroutines.launch

private var _binding: FragmentSettingBinding? = null
private val binding get() = _binding!!

class SettingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: SettingViewModel by viewModels()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    // Update UI elements

                    with(binding) {
                        nameEditText.setText(it.userPlant?.name)
                        typeEditText.setText(it.userPlant?.type)

                    }

                }
            }
        }

        viewModel.getUserPlantsData()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: SettingViewModel by viewModels()



        binding.textButton.setOnClickListener {

            val nameEditText = binding.nameEditText
            val typeEditText = binding.typeEditText

            if (nameEditText.text != null && typeEditText.text != null) {

                val name = nameEditText.text.toString()
                val type = typeEditText.text.toString()

                viewModel.updateUserPlantsData(
                    it,
                    name,
                    type
                ).also {
                    hideKeyboard()
                }

            }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    /*
    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

     */

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}