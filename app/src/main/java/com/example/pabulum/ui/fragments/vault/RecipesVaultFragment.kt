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
import com.example.pabulum.databinding.FragmentRecipesVaultBinding
import com.example.pabulum.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes_vault.view.*

@AndroidEntryPoint
class RecipesVaultFragment : Fragment() {

    private val rAdapter: VaultRecipesAdapter by lazy { VaultRecipesAdapter() }
    private val mainViewModel: MainViewModel by viewModels()
    private var _binding: FragmentRecipesVaultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesVaultBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.rAdapter = rAdapter

        setupRecyclerView(binding.recipesVaultRecyclerView)

        return binding.root
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = rAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}