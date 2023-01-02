package com.example.pabulum.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pabulum.util.Constants.Companion.API_KEY
import com.example.pabulum.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.pabulum.util.Constants.Companion.QUERY_API_KEY
import com.example.pabulum.util.Constants.Companion.QUERY_DIET
import com.example.pabulum.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.pabulum.util.Constants.Companion.QUERY_NUMBER
import com.example.pabulum.util.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = "30"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "main course"
        queries[QUERY_DIET] = "vegetarian"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

}
