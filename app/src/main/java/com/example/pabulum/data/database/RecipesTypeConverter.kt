package com.example.pabulum.data.database

import androidx.room.TypeConverter
import com.example.pabulum.models.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.pabulum.models.Result

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun recipeToString(foodRecipe: FoodRecipe): String {
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToRecipe(data: String): FoodRecipe {
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun stringToResult(data: String): Result {
        val listType = object : TypeToken<Result>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun resultToString(result: Result): String {
        return gson.toJson(result)
    }

}