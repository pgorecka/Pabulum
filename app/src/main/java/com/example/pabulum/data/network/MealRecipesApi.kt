package com.example.pabulum.data.network

import com.example.pabulum.models.FoodFact
import com.example.pabulum.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MealRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<FoodRecipe>

    @GET("food/trivia/random")
    suspend fun getFact(
        @Query("apiKey") apiKey: String
    ): Response<FoodFact>

}