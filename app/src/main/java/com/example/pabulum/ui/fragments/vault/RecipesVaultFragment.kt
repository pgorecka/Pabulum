package com.example.pabulum.ui.fragments.vault

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pabulum.R
import com.example.pabulum.adapters.VaultRecipesAdapter
import com.example.pabulum.databinding.FragmentRecipesVaultBinding
import com.example.pabulum.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes_vault.view.*

@AndroidEntryPoint
class RecipesVaultFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val rAdapter: VaultRecipesAdapter by lazy { VaultRecipesAdapter(requireActivity(), mainViewModel) }
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

        setHasOptionsMenu(true)

        setupRecyclerView(binding.recipesVaultRecyclerView)

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.vault_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.remove_all_vault_recipes_menu) {
            mainViewModel.deleteAllVaultRecipes()
            showSnackbar()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackbar() {
        Snackbar.make(
            binding.root,
            "All recipes removed.",
            Snackbar.LENGTH_SHORT
        ).setAction("OK") {}.show()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = rAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        rAdapter.clearContextualActionMode()
    }
}