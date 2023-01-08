package com.example.pabulum.data.database

import androidx.room.*
import com.example.pabulum.data.database.entities.RecipesEntity
import com.example.pabulum.data.database.entities.VaultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaultRecipe(vaultEntity: VaultEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun fetchRecipes(): Flow<List<RecipesEntity>>

    @Query("SELECT * FROM vault_table ORDER BY id ASC")
    fun readVaultRecipes(): Flow<List<VaultEntity>>

    @Delete
    suspend fun deleteVaultRecipe(vaultEntity: VaultEntity)

    @Query("DELETE FROM vault_table")
    suspend fun deleteAllVaultRecipes()

}