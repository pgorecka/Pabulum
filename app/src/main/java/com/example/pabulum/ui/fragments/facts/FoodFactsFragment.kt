package com.example.pabulum.ui.fragments.facts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pabulum.R
import com.example.pabulum.databinding.FragmentFoodFactsBinding
import com.example.pabulum.util.Constants.Companion.API_KEY
import com.example.pabulum.util.NetworkResult
import com.example.pabulum.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodFactsFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel> ()
    private var _binding: FragmentFoodFactsBinding? = null
    private val binding get() = _binding!!
    private var foodFact = "No Facts for now"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodFactsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel

        setHasOptionsMenu(true)

        mainViewModel.getFoodFact(API_KEY)

        mainViewModel.foodFactResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.factsTextView.text = response.data?.text
                    if (response.data != null) {
                        foodFact = response.data.text
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Log.d("FoodFactFragment", "Fact Loading")
                }
            }
        })

        return binding.root
    }

    private fun loadDataFromCache() {
      lifecycleScope.launch {
          mainViewModel.readFoodFacts.observe(viewLifecycleOwner, { database ->
              if (!database.isNullOrEmpty()) {
                  binding.factsTextView.text = database[0].foodFact.text
                  foodFact = database[0].foodFact.text
              }
          })
      }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.facts_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share_fact) {

            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, foodFact)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}