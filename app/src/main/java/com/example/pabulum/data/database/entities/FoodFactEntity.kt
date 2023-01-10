package com.example.pabulum.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pabulum.models.FoodFact
import com.example.pabulum.util.Constants.Companion.FACTS_TABLE

@Entity(tableName = FACTS_TABLE)
class FoodFactEntity (
    @Embedded
    var foodFact: FoodFact
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}