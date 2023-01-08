package com.example.pabulum.ui.fragments.vault

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pabulum.R
import com.example.pabulum.adapters.VaultRecipesAdapter
import com.example.pabulum.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes_vault.view.*

@AndroidEntryPoint
class RecipesVaultFragment : Fragment() {

    private val rAdapter: VaultRecipesAdapter by lazy { VaultRecipesAdapter() }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipes_vault, container, false)

        setupRecyclerView(view.recipesVaultRecyclerView)

        mainViewModel.readVaultRecipes.observe(viewLifecycleOwner, { vaultEntity ->
            rAdapter.setData(vaultEntity)
        })

        return view
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = rAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

}