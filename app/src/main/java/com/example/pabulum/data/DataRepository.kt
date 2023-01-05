package com.example.pabulum.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.example.pabulum.util.Constants.Companion.DEFAULT_DIET
import com.example.pabulum.util.Constants.Companion.DEFAULT_TYPE
import com.example.pabulum.util.Constants.Companion.PREFERENCES_DIET
import com.example.pabulum.util.Constants.Companion.PREFERENCES_DIET_ID
import com.example.pabulum.util.Constants.Companion.PREFERENCES_TITLE
import com.example.pabulum.util.Constants.Companion.PREFERENCES_TYPE
import com.example.pabulum.util.Constants.Companion.PREFERENCES_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object Preferences {
        val checkedType = preferencesKey<String>(PREFERENCES_TYPE)
        val checkedTypeId = preferencesKey<Int>(PREFERENCES_TYPE_ID)
        val checkedDiet = preferencesKey<String>(PREFERENCES_DIET)
        val checkedDietId = preferencesKey<Int>(PREFERENCES_DIET_ID)
    }

    private val dataStore: DataStore<androidx.datastore.preferences.Preferences> = context.createDataStore(
        name = PREFERENCES_TITLE
    )

    suspend fun saveTypeAndDiet(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        dataStore.edit {  preferences ->
            preferences[Preferences.checkedType] = mealType
            preferences[Preferences.checkedTypeId] = mealTypeId
            preferences[Preferences.checkedDiet] = dietType
            preferences[Preferences.checkedDietId] = dietTypeId

        }
    }

    val readTypeAndDiet: Flow<TypeAndDiet> = dataStore.data
        .catch {  exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
                }
            }
        .map { preferences ->
            val checkedType = preferences[Preferences.checkedType] ?: DEFAULT_TYPE
            val checkedTypeId = preferences[Preferences.checkedTypeId] ?: 0
            val checkedDiet = preferences[Preferences.checkedDiet] ?: DEFAULT_DIET
            val checkedDietId = preferences[Preferences.checkedDietId] ?: 0

            TypeAndDiet(
                checkedType,
                checkedTypeId,
                checkedDiet,
                checkedDietId
            )
        }
}

data class TypeAndDiet(
    val checkedType: String,
    val checkedTypeId: Int,
    val checkedDiet: String,
    val checkedDietId: Int
)



