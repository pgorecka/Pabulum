package com.example.pabulum.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pabulum.data.database.entities.RecipesEntity
import com.example.pabulum.data.database.entities.VaultEntity


@Database(
    entities = [RecipesEntity::class, VaultEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}