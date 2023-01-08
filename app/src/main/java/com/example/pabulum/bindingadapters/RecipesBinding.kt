package com.example.pabulum.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.pabulum.data.database.entities.RecipesEntity
import com.example.pabulum.models.FoodRecipe
import com.example.pabulum.util.NetworkResult

class RecipesBinding {

    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImgVisibility(
            imageView: ImageView,
            apiResult: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ){
            if (apiResult is NetworkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (apiResult is NetworkResult.Loading) {
                imageView.visibility = View.INVISIBLE
            } else if (apiResult is NetworkResult.Success) {
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponse_", "readDatabase_", requireAll = true)
        @JvmStatic
        fun errorTxtVisibility(
            textView: TextView,
            apiResult: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResult is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiResult.message.toString()
            } else if (apiResult is NetworkResult.Loading) {
                textView.visibility = View.INVISIBLE
            } else if (apiResult is NetworkResult.Success) {
                textView.visibility = View.INVISIBLE
            }
        }

    }

}