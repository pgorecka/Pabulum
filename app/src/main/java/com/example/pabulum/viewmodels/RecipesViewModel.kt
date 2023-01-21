package com.example.pabulum.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pabulum.data.DataRepository
import com.example.pabulum.util.Constants.Companion.API_KEY
import com.example.pabulum.util.Constants.Companion.DEFAULT_DIET
import com.example.pabulum.util.Constants.Companion.DEFAULT_RECIPES_AMOUNT
import com.example.pabulum.util.Constants.Companion.DEFAULT_TYPE
import com.example.pabulum.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.pabulum.util.Constants.Companion.QUERY_API_KEY
import com.example.pabulum.util.Constants.Companion.QUERY_DIET
import com.example.pabulum.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.pabulum.util.Constants.Companion.QUERY_NUMBER
import com.example.pabulum.util.Constants.Companion.QUERY_SEARCH
import com.example.pabulum.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(application: Application, private val dataRepository: DataRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_TYPE
    private var dietType = DEFAULT_DIET

    var networkStatus = false
    var backOnline = false

    val readTypeAndDiet = dataRepository.readTypeAndDiet
    val readBackOnline = dataRepository.readBackOnline.asLiveData()

    fun saveTypeAndDiet(mealType: String, mealTypeId: Int, mealDiet: String, mealDietId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.saveTypeAndDiet(mealType, mealTypeId, mealDiet, mealDietId)
        }

    fun saveBackOnline(backOnline: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataRepository.saveBackOnline(backOnline)
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
           readTypeAndDiet.collect {  value ->
               mealType = value.checkedType
               dietType = value.checkedDiet
           }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_AMOUNT
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_AMOUNT
        queries[QUERY_API_KEY]= API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Network Connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "Back Online!", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }

}
