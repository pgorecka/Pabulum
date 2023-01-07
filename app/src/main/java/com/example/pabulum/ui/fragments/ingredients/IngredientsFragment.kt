package com.example.pabulum.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pabulum.R
import com.example.pabulum.adapters.IngredientsAdapter
import com.example.pabulum.models.Result
import com.example.pabulum.util.Constants.Companion.RECIPE_RESULT
import kotlinx.android.synthetic.main.fragment_ingredients.view.*

class IngredientsFragment : Fragment() {

    private val rAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_ingredients, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT)

        setupRecyclerView(view)
        myBundle?.extendedIngredients?.let { rAdapter.setData(it) }

        return view
    }

    private fun setupRecyclerView(view: View) {
        view.recyclerview_ingredients.adapter = rAdapter
        view.recyclerview_ingredients.layoutManager = LinearLayoutManager(requireContext())
    }

}