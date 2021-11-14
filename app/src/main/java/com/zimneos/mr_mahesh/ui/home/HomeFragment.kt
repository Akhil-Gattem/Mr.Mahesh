package com.zimneos.mr_mahesh.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.database.FirebaseDatabase
import com.zimneos.mr_mahesh.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*


class HomeFragment : Fragment(), RecyclerViewAdapter.OnItemListener {

    private var adapter: RecyclerViewAdapter? = null
    var getDataJob: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewLayoutManager()
        fireBaseRecyclerSetup()
        startShimmer()
        onScrollStopShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        getDataJob?.cancel()
    }

    private fun recyclerViewLayoutManager() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager
    }

    private fun fireBaseRecyclerSetup() {
        getDataJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            withContext(Dispatchers.Main) {
                val options = FirebaseRecyclerOptions.Builder<Holding>().setQuery(
                    FirebaseDatabase.getInstance().reference.child("movielist"), Holding::class.java
                ).build()
                adapter = RecyclerViewAdapter(options, this@HomeFragment)
                adapter?.startListening()
                recyclerView.adapter = adapter
            }
        }
    }

    private fun startShimmer() {
        val container: ShimmerFrameLayout = shimmerLayout as ShimmerFrameLayout
        container.startShimmer()
    }

    private fun onScrollStopShimmer() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                shimmerLayout.isInvisible = true
                shimmerLayout.stopShimmer()
            }
        })
    }

    override fun onItemClick(
        listener: Holding, position: Int, poster: ShapeableImageView, title: TextView
    ) {
        val intent = Intent(context, InfoScreenActivity::class.java)
        intent.putExtra("poster", listener.poster)
        intent.putExtra("title", listener.title)
        intent.putExtra("poster_transition", ViewCompat.getTransitionName(poster))
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this.requireActivity(), poster, ViewCompat.getTransitionName(poster)!!)
        startActivity(intent, options.toBundle())
    }


    private fun onError(message: String) {
        Toast.makeText(this.requireContext(), " $message", Toast.LENGTH_SHORT).show()
    }

}