package com.example.pabulum.adapters

import android.os.Message
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pabulum.R
import com.example.pabulum.data.database.entities.VaultEntity
import com.example.pabulum.databinding.VaultRecipesRowLayoutBinding
import com.example.pabulum.ui.fragments.vault.RecipesVaultFragmentDirections
import com.example.pabulum.util.RecipesDiffUtil
import com.example.pabulum.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.vault_recipes_row_layout.view.*

class VaultRecipesAdapter(

    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel

): RecyclerView.Adapter<VaultRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private lateinit var rActionMode: ActionMode
    private lateinit var rootView: View
    private var multiSelect = false
    private var selectedRecipes = arrayListOf<VaultEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()
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
        myViewHolders.add(holder)
        rootView = holder.itemView.rootView
        val currentRecipe = vaultRecipes[position]
        holder.bind(currentRecipe)

        //Single Click Listener
        holder.itemView.vaultRecipesRowLayout.setOnClickListener {
            if (multiSelect) {
                applyChoice(holder, currentRecipe)
            } else {
                val action = RecipesVaultFragmentDirections.actionRecipesVaultFragmentToFeaturesActivity(
                    currentRecipe.result
                )
                holder.itemView.findNavController().navigate(action)
            }

        }

        //Long Click Listener
        holder.itemView.vaultRecipesRowLayout.setOnLongClickListener {
            if (!multiSelect) {
                multiSelect = true
                requireActivity.startActionMode(this)
                applyChoice(holder, currentRecipe)
                true
            } else {
                multiSelect = false
                false
            }

        }
    }

    private fun applyChoice(holder: MyViewHolder, currentRecipe: VaultEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.white, R.color.mediumGray)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBgLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.itemView.vaultRecipesRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.itemView.vault_row_cardView.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun applyActionModeTitle() {
        when(selectedRecipes.size) {
            0 -> {
                rActionMode.finish()
            }
            1 -> {
                rActionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                rActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }
    override fun getItemCount(): Int {
        return vaultRecipes.size
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.vault_contextual_menu, menu)
        rActionMode = mode!!
        applyStatusBarColor(R.color.contextualStatus)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.remove_vault_recipe_menu) {
            selectedRecipes.forEach {
                mainViewModel.deleteVaultRecipe(it)
            }
            showSnackbar("${selectedRecipes.size} item/s successfully removed.")
            multiSelect = false
            selectedRecipes.clear()
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach {  holder ->
            changeRecipeStyle(holder, R.color.white, R.color.mediumGray)
        }
        multiSelect = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBar)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("OK") {}.show()
    }

    fun setData(newVaultRecipes: List<VaultEntity>) {
        val vaultRecipesDiffUtil =
            RecipesDiffUtil(vaultRecipes, newVaultRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(vaultRecipesDiffUtil)
        vaultRecipes = newVaultRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun clearContextualActionMode() {
        if (this::rActionMode.isInitialized) {
            rActionMode.finish()
        }
    }
}