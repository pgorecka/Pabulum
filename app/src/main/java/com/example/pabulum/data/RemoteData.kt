package com.example.pabulum.data

import com.example.pabulum.data.network.MealRecipesApi
import com.example.pabulum.models.FoodFact
import com.example.pabulum.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteData @Inject constructor(
    private val mealRecipesApi: MealRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return mealRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return mealRecipesApi.searchRecipes(searchQuery)
    }

    suspend fun getFoodFact(apiKey: String): Response<FoodFact> {
        return mealRecipesApi.getFact(apiKey)
    }

}