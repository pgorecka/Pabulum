package com.example.pabulum.data

import com.example.pabulum.data.database.RecipesDao
import com.example.pabulum.data.database.entities.FoodFactEntity
import com.example.pabulum.data.database.entities.VaultEntity
import com.example.pabulum.data.database.entities.RecipesEntity
import com.example.pabulum.models.FoodFact
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalData @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readVaultRecipes(): Flow<List<VaultEntity>> {
        return recipesDao.readVaultRecipes()
    }

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.fetchRecipes()
    }

    fun readFoodFact(): Flow<List<FoodFactEntity>> {
        return recipesDao.readFoodFact()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFoodFact(foodFactEntity: FoodFactEntity) {
        recipesDao.insertFoodFact(foodFactEntity)
    }

    suspend fun insertVaultRecipe(vaultEntity: VaultEntity) {
        recipesDao.insertVaultRecipe(vaultEntity)
    }

    suspend fun deleteVaultRecipe(vaultEntity: VaultEntity) {
        recipesDao.deleteVaultRecipe(vaultEntity)
    }

    suspend fun deleteAllVaultRecipes() {
        recipesDao.deleteAllVaultRecipes()
    }

}