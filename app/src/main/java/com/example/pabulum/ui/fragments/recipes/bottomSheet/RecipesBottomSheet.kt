package com.example.pabulum.ui.fragments.recipes.bottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.pabulum.R
import com.example.pabulum.util.Constants.Companion.DEFAULT_DIET
import com.example.pabulum.util.Constants.Companion.DEFAULT_TYPE
import com.example.pabulum.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.recipes_bottom_sheet.view.*
import java.util.*

class RecipesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var recipesViewModel: RecipesViewModel

    private var typeChip = DEFAULT_TYPE
    private var typeChipId = 0
    private var dietChip = DEFAULT_DIET
    private var dietChipId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rView =  inflater.inflate(R.layout.recipes_bottom_sheet, container, false)

        recipesViewModel.readTypeAndDiet.asLiveData().observe(viewLifecycleOwner, { value ->
            typeChip = value.checkedType
            dietChip = value.checkedDiet
            updateChip(value.checkedTypeId, rView.meal_type_chipGroup)
            updateChip(value.checkedDietId, rView.diet_type_chipGroup)
        })

        rView.meal_type_chipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val checkedType = chip.text.toString().toLowerCase(Locale.ROOT)
            typeChip = checkedType
            typeChipId = selectedChipId
        }

        rView.diet_type_chipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val checkedDiet = chip.text.toString().toLowerCase(Locale.ROOT)
            dietChip = checkedDiet
            dietChipId = selectedChipId
        }

        rView.search_button.setOnClickListener {
            recipesViewModel.saveTypeAndDiet(
                typeChip,
                typeChipId,
                dietChip,
                dietChipId
            )
            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
        findNavController().navigate(action)
        }

        return rView
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

}