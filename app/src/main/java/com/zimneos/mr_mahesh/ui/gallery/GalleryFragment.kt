package com.zimneos.mr_mahesh.ui.gallery

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.zimneos.mr_mahesh.R
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        Log.d("akki","onCreateView")
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("akki","onViewCreated")
        galleryViewModel.text.observe(viewLifecycleOwner, {
            text_gallery.text = it
        })
        MobileAds.initialize(requireContext()) {}
        loadAds()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("akki","onAttach")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("akki","onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("akki","onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("akki","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("akki","onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.d("akki","onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("akki","onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("akki","onDetach")
    }

    override fun onResume() {
        super.onResume()
        Log.d("akki","onResume")
    }

    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()
        adViewBanner_gallery1.loadAd(adRequest)
        adViewBanner_gallery2.loadAd(adRequest)
    }
}