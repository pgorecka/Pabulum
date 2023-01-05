package com.example.pabulum.ui.fragments.vault.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pabulum.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class VaultBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.vault_bottom_sheet, container, false)
    }

}