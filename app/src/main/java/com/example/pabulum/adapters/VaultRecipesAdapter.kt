package com.example.pabulum.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pabulum.data.database.entities.VaultEntity
import com.example.pabulum.databinding.VaultRecipesRowLayoutBinding
import com.example.pabulum.ui.fragments.vault.RecipesVaultFragmentDirections
import com.example.pabulum.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.vault_recipes_row_layout.view.*

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

        holder.itemView.vaultRecipesRowLayout.setOnClickListener {
            val action = RecipesVaultFragmentDirections.actionRecipesVaultFragmentToFeaturesActivity(selectedRecipe.result)
            holder.itemView.findNavController().navigate(action)
        }
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