package com.example.pabulum.util

class Constants {

    companion object {

        const val BASE_URL = "https://api.spoonacular.com"
        const val BASE_IMG_URL = "https://spoonacular.com/cdn/ingredients_100x100/"
        const val API_KEY = "282e3fd202294972a6975f0b26d3e6f9"
        const val RECIPE_RESULT = "recipeBundle"

        // API Query Keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"
        const val QUERY_SEARCH = "query"


        // ROOM
        const val DATABASE_NAME = "recipes_database"
        const val VAULT_TABLE = "vault_table"
        const val FACTS_TABLE = "facts_table"
        const val RECIPES_TABLE = "recipes_table"


        // BOTTOM SHEET
        const val DEFAULT_TYPE = "appetizer"
        const val DEFAULT_DIET = "vegetarian"
        const val DEFAULT_RECIPES_AMOUNT = "40"
        const val PREFERENCES_TITLE = "app_preferences"
        const val PREFERENCES_TYPE = "mealType"
        const val PREFERENCES_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET = "dietType"
        const val PREFERENCES_DIET_ID = "dietTypeId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"

    }

}

