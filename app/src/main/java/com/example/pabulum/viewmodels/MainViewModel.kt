package com.example.pabulum.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.pabulum.data.Repository
import com.example.pabulum.data.database.entities.FoodFactEntity
import com.example.pabulum.data.database.entities.RecipesEntity
import com.example.pabulum.data.database.entities.VaultEntity
import com.example.pabulum.models.FoodFact
import com.example.pabulum.models.FoodRecipe
import com.example.pabulum.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    // ROOM

    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()
    val readVaultRecipes: LiveData<List<VaultEntity>> = repository.local.readVaultRecipes().asLiveData()
    val readFoodFacts: LiveData<List<FoodFactEntity>> = repository.local.readFoodFact().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    fun insertVaultRecipe(vaultEntity: VaultEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertVaultRecipe(vaultEntity)
    }

    fun insertFoodFact(foodFactEntity: FoodFactEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFoodFact(foodFactEntity)
        }

    fun deleteVaultRecipe(vaultEntity: VaultEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteVaultRecipe(vaultEntity)
    }

    fun deleteAllVaultRecipes() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllVaultRecipes()
    }

    // RETROFIT

    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var foodFactResponse: MutableLiveData<NetworkResult<FoodFact>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    fun getFoodFact(apiKey: String) = viewModelScope.launch { 
        getFoodFactSafeCall(apiKey)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)

                val recipe = recipesResponse.value!!.data
                if (recipe != null) {
                    offlineCacheRecipes(recipe)
                }
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Result not found.")
            }
        } else {
            recipesResponse.value = NetworkResult.Error("No Network Connection.")
        }
    }

    private suspend fun getFoodFactSafeCall(apiKey: String) {
        foodFactResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getFoodFact(apiKey)
                foodFactResponse.value = handleFoodFactResponse(response)

                val foodFact = foodFactResponse.value!!.data
                if (foodFact != null) {
                    offlineCacheFacts(foodFact)
                }
            } catch (e: Exception) {
                foodFactResponse.value = NetworkResult.Error("Result not found.")
            }
        } else {
            foodFactResponse.value = NetworkResult.Error("No Network Connection.")
        }
    }


    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                searchRecipesResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                searchRecipesResponse.value = NetworkResult.Error("Result not found.")
            }
        } else {
            searchRecipesResponse.value = NetworkResult.Error("No Network Connection.")
        }
    }


    private fun offlineCacheFacts(foodFact: FoodFact) {
        val foodFactEntity = FoodFactEntity(foodFact)
        insertFoodFact(foodFactEntity)
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Result not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun handleFoodFactResponse(response: Response<FoodFact>): NetworkResult<FoodFact>? {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                NetworkResult.Error("API Key Limited.")
            }
            response.isSuccessful -> {
                val foodFact = response.body()
                NetworkResult.Success(foodFact!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}