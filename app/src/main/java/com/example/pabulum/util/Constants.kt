package com.example.pabulum.util

class Constants {

    companion object {

        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = "282e3fd202294972a6975f0b26d3e6f9"

        // API Query Keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"


        // ROOM
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"


        // BOTTOM SHEET
        const val DEFAULT_TYPE = "main course"
        const val DEFAULT_DIET = "vegetarian"
        const val DEFAULT_RECIPES_AMOUNT = "30"
        const val PREFERENCES_TITLE = "app_preferences"
        const val PREFERENCES_TYPE = "mealType"
        const val PREFERENCES_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET = "dietType"
        const val PREFERENCES_DIET_ID = "dietTypeId"

    }

}

