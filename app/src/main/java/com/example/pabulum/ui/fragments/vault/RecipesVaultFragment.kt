package com.example.pabulum.ui.fragments.vault

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pabulum.viewmodels.MainViewModel
import com.example.pabulum.R
import com.example.pabulum.adapters.SourceAdapter
import com.example.pabulum.util.NetworkResult
import com.example.pabulum.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*

@AndroidEntryPoint
class RecipesVaultFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val rAdapter by lazy { SourceAdapter() }
    private lateinit var rView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rView = inflater.inflate(R.layout.fragment_recipes, container, false)

        setupRecyclerView()
        readDatabase()

        return rView
    }

    private fun readDatabase() {
        mainViewModel.readRecipes.observe(viewLifecycleOwner, { database ->
            if (database.isNotEmpty()) {
                Log.d("Recipes fragment", "RequestDatabase called")
                rAdapter.setData(database[0].foodRecipe)
                hideShimmer()
            } else {
                requestApiData()
            }
        })
    }

    private fun requestApiData() {
        Log.d("Recipes fragment", "RequestApiData called")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmer()
                    response.data?.let { rAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmer()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        rView.recyclerview.adapter = rAdapter
        rView.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        rView.recyclerview.showShimmer()
    }

    private fun hideShimmer() {
        rView.recyclerview.hideShimmer()
    }

}