package com.example.pabulum.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.pabulum.R
import com.example.pabulum.adapters.PagerAdapter
import com.example.pabulum.data.database.entities.VaultEntity
import com.example.pabulum.ui.fragments.directions.DirectionsFragment
import com.example.pabulum.ui.fragments.ingredients.IngredientsFragment
import com.example.pabulum.ui.fragments.summary.SummaryFragment
import com.example.pabulum.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_features.*

@AndroidEntryPoint
class FeaturesActivity : AppCompatActivity() {

    private val args by navArgs<FeaturesActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var recipeSaved = false
    private var savedRecipeId = 0
    private lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_features)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(SummaryFragment())
        fragments.add(IngredientsFragment())
        fragments.add(DirectionsFragment())

        val title = ArrayList<String>()
        title.add("Summary")
        title.add("Ingredients")
        title.add("Directions")

        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            title,
            supportFragmentManager
        )

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.features_menu, menu)
        menuItem = menu!!.findItem(R.id.save_to_vault_menu)
        checkVaultRecipes(menuItem)
        return true
    }

    private fun checkVaultRecipes(menuItem: MenuItem) {
        mainViewModel.readVaultRecipes.observe(this, { vaultEntity ->
            try {
                for (savedRecipe in vaultEntity) {
                    if (savedRecipe.result.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.green)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    }
                }
            } catch (e: java.lang.Exception) {
                Log.d("FeaturesActivity", e.message.toString())
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_vault_menu && !recipeSaved) {
            saveToVault(item)
        } else if (item.itemId == R.id.save_to_vault_menu && recipeSaved) {
            removeFromVault(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToVault(item: MenuItem) {
        val vaultEntity =
            VaultEntity(
                0,
                args.result
            )
        mainViewModel.insertVaultRecipe(vaultEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackbar("Saved to vault")
        recipeSaved = true
    }

    private fun removeFromVault(item: MenuItem) {
        val vaultEntity =
            VaultEntity(
                savedRecipeId,
                args.result
            )
        mainViewModel.deleteVaultRecipe(vaultEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackbar("Removed from Vault")
        recipeSaved = false
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            featuresLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("OK") {}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(menuItem, R.color.white)
    }
}