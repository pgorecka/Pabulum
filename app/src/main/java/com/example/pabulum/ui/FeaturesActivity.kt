package com.example.pabulum.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.pabulum.R
import com.example.pabulum.ui.fragments.directions.DirectionsFragment
import com.example.pabulum.ui.fragments.ingredients.IngredientsFragment
import com.example.pabulum.ui.fragments.summary.SummaryFragment
import kotlinx.android.synthetic.main.activity_features.*

class FeaturesActivity : AppCompatActivity() {

    private val args by navArgs<FeaturesActivityArgs>()

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

        val adapter = com.example.pabulum.adapters.PagerAdapter(
            resultBundle,
            fragments,
            title,
            supportFragmentManager
        )

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}