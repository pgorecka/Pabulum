package com.example.pabulum.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pabulum.models.Result
import com.example.pabulum.util.Constants.Companion.VAULT_TABLE

@Entity(tableName = VAULT_TABLE)
class VaultEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)