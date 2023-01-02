package com.example.pabulum.data

import com.example.pabulum.data.network.MealRecipesApi
import com.example.pabulum.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteData @Inject constructor(
    private val foodRecipesApi: MealRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

}