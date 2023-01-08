package com.example.pabulum.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pabulum.models.FoodRecipe
import com.example.pabulum.util.Constants.Companion.RECIPES_TABLE


@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
)
 {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}