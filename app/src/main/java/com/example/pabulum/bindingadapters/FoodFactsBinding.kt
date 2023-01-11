package com.example.pabulum.bindingadapters

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.pabulum.data.database.entities.FoodFactEntity
import com.example.pabulum.models.FoodFact
import com.example.pabulum.util.NetworkResult
import com.google.android.material.card.MaterialCardView

class FoodFactsBinding {

    companion object {

        @BindingAdapter("readApi3", "readDatabase3", requireAll = false)
        @JvmStatic
        fun setItemsVisibility(
            view: View,
            apiResponse: NetworkResult<FoodFact>?,
            database: List<FoodFactEntity>?
        ) {
            when (apiResponse) {
                is NetworkResult.Loading -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.VISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                            if (database != null) {
                                if (database.isEmpty()) {
                                    view.visibility = View.INVISIBLE
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Success -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        @BindingAdapter("readApi4", "readDatabase4", requireAll = true)
        @JvmStatic
        fun setErrorVisibility(
            view: View,
            apiResponse: NetworkResult<FoodFact>?,
            database: List<FoodFactEntity>?
        ) {
            if (database != null) {
                if (database.isEmpty()) {
                    view.visibility = View.VISIBLE
                    if (view is TextView) {
                        if (apiResponse != null) {
                            view.text = apiResponse.message.toString()
                        }
                    }
                }
            }
            if (apiResponse is NetworkResult.Success) {
                view.visibility = View.INVISIBLE
            }
        }

    }
}