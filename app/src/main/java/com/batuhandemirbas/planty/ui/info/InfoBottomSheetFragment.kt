package com.batuhandemirbas.planty.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.batuhandemirbas.planty.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InfoBottomSheetFragment: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // BottomSheet içeriğini oluşturun
        val view = inflater.inflate(R.layout.fragment_info_bottom_sheet, container, false)

        // BottomSheet içeriğine etkileşimler ekleyin
        // ...

        return view
    }


}