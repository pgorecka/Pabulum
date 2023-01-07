package com.example.pabulum.ui.fragments.directions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.pabulum.R
import com.example.pabulum.models.Result
import com.example.pabulum.util.Constants
import kotlinx.android.synthetic.main.fragment_directions.view.*

class DirectionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_directions, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT)

        view.instructions_source_view.webViewClient = object  : WebViewClient() {}
        val webUrl: String = myBundle!!.sourceUrl
        view.instructions_source_view.loadUrl(webUrl)


        return view
    }

}