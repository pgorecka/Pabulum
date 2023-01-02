package com.example.pabulum.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.pabulum.R

class RecipesRowBinding {

    companion object {

        @BindingAdapter("loadThumbnail")
        @JvmStatic
        fun loadThumbnail(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
            }
        }

        @BindingAdapter("applyVege")
        @JvmStatic
        fun applyVege(view: View, vege: Boolean) {
            if(vege){
                when(view){
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }

    }

}