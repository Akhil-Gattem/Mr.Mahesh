package com.zimneos.mr_mahesh.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.zimneos.mr_mahesh.R
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_slideshow.*

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel = ViewModelProvider(this).get(SlideshowViewModel::class.java)
        return inflater.inflate(R.layout.fragment_slideshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        slideshowViewModel.text.observe(viewLifecycleOwner, {
            text_slideshow.text = it
        })
        MobileAds.initialize(requireContext()) {}
        loadAds()
    }

    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()
        adViewBanner_slideShow1.loadAd(adRequest)
        adViewBanner_slideShow2.loadAd(adRequest)
    }
}