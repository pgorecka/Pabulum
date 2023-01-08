package com.example.pabulum.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pabulum.data.database.entities.VaultEntity
import com.example.pabulum.databinding.VaultRecipesRowLayoutBinding
import com.example.pabulum.util.RecipesDiffUtil

class VaultRecipesAdapter: RecyclerView.Adapter<VaultRecipesAdapter.MyViewHolder>() {

    private var vaultRecipes = emptyList<VaultEntity>()

    class MyViewHolder(private val binding: VaultRecipesRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(vaultEntity: VaultEntity) {
            binding.vaultEntity = vaultEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VaultRecipesRowLayoutBinding.inflate(layoutInflater, parent, false)

                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedRecipe = vaultRecipes[position]
        holder.bind(selectedRecipe)
    }

    override fun getItemCount(): Int {
        return vaultRecipes.size
    }

    fun setData(newVaultRecipes: List<VaultEntity>) {
        val vaultRecipesDiffUtil =
            RecipesDiffUtil(vaultRecipes, newVaultRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(vaultRecipesDiffUtil)
        vaultRecipes = newVaultRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }
}