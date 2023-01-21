package com.example.pabulum.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.*
import com.example.pabulum.util.Constants.Companion.DEFAULT_DIET
import com.example.pabulum.util.Constants.Companion.DEFAULT_TYPE
import com.example.pabulum.util.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.pabulum.util.Constants.Companion.PREFERENCES_DIET
import com.example.pabulum.util.Constants.Companion.PREFERENCES_DIET_ID
import com.example.pabulum.util.Constants.Companion.PREFERENCES_TITLE
import com.example.pabulum.util.Constants.Companion.PREFERENCES_TYPE
import com.example.pabulum.util.Constants.Companion.PREFERENCES_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject



@ViewModelScoped
class DataRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object Preferences {
        val checkedType = stringPreferencesKey(PREFERENCES_TYPE)
        val checkedTypeId = intPreferencesKey(PREFERENCES_TYPE_ID)
        val checkedDiet = stringPreferencesKey(PREFERENCES_DIET)
        val checkedDietId = intPreferencesKey(PREFERENCES_DIET_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences> = context.createDataStore(
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

    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit {  preferences ->
            preferences[Preferences.backOnline] = backOnline
        }
    }

    val readTypeAndDiet: Flow<TypeAndDiet> = dataStore.data
        .catch { exception ->
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

    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[Preferences.backOnline] ?: false
            backOnline
        }
}

data class TypeAndDiet(
    val checkedType: String,
    val checkedTypeId: Int,
    val checkedDiet: String,
    val checkedDietId: Int
)



