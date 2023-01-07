package com.example.pabulum.ui.fragments.summary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.example.pabulum.R
import com.example.pabulum.models.Result
import kotlinx.android.synthetic.main.fragment_summary.view.*
import kotlinx.android.synthetic.main.recipes_row_layout.view.*
import org.jsoup.Jsoup

class SummaryFragment : Fragment() {

     override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
         // Inflate the layout for this fragment
         val view = inflater.inflate(R.layout.fragment_summary, container, false)

         val args = arguments
         val myBundle: Result? = args?.getParcelable("recipeBundle")

         view.primary_imageView.load(myBundle?.image)
         view.name_textView.text = myBundle?.title
         view.likes_textView.text = myBundle?.aggregateLikes.toString()
         view.minutes_textView.text = myBundle?.readyInMinutes.toString()
         myBundle?.summary.let {
             val summary = Jsoup.parse(it).text()
             view.summary_textView.text = summary
         }

         if (myBundle?.vegan == true) {
             view.vegan_marker_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
             view.vegan_marker_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
         }

         if (myBundle?.vegetarian == true) {
             view.vegetarian_marker_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
             view.vegetarian_marker_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
         }

         if (myBundle?.dairyFree == true) {
             view.dairy_free_marker_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
             view.dairy_free_marker_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
         }

         if (myBundle?.glutenFree == true) {
             view.gluten_free_marker_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
             view.gluten_free_marker_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
         }

         if (myBundle?.cheap == true) {
             view.budget_marker_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
             view.budget_marker_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
         }

         if (myBundle?.veryHealthy == true) {
             view.healthy_marker_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
             view.healthy_marker_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
         }

         return view
     }
}