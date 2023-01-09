package com.wipro.feeddigest.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wipro.feeddigest.R
import com.wipro.feeddigest.adapter.FeedDigestAdapter
import com.wipro.feeddigest.databinding.FeedUiBinding
import com.wipro.feeddigest.utilities.Utility
import com.wipro.feeddigest.viewmodel.FeedDigestViewModel

/**
 * Main Feed Digest Screen
 * */
class FeedDigestActivity : AppCompatActivity() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var feedDigestViewModel: FeedDigestViewModel? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var feedUiBinding: FeedUiBinding

    var feedDigestAdapter: FeedDigestAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedUiBinding = FeedUiBinding.inflate(layoutInflater)
        setContentView(feedUiBinding.root)
        initialiseUI()
        addFeedDataObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> {
                fetchFeeds()
            }
        }
        return true
    }

    private fun initialiseUI() {
        feedUiBinding.rvFeedDigest.let {
            it.layoutManager = LinearLayoutManager(this)
            feedDigestAdapter = FeedDigestAdapter()
            it.addItemDecoration(
                DividerItemDecoration(
                    this,
                    (it.layoutManager as LinearLayoutManager).orientation
                )
            )
            it.adapter = feedDigestAdapter
        }
    }

    /**
     * This function is responsible for fetching feeds from server
     * and observe the feeds accordingly
     */
    private fun addFeedDataObserver() {
        feedDigestViewModel = ViewModelProvider(this)[FeedDigestViewModel::class.java]
        fetchFeeds()

        feedDigestViewModel?.feedDigestResponse?.observe(this) { feedDigestList ->
            if (feedDigestList.isEmpty()) {
                showNoFeedDigest()
            } else {
                showFeedDigestData()
                feedDigestAdapter?.setFeedDigest(feedDigestList)
            }
        }

        feedDigestViewModel?.feedDigestTitle?.observe(this) { digestTitle ->
            title = digestTitle
        }

        feedDigestViewModel?.feedDigestResponseError?.observe(this) {
            showNoFeedDigest()
        }
    }

    override fun onResume() {
        super.onResume()
        feedUiBinding.shimmerFrameLayout.startShimmer()
    }

    /*
    This method is responsible for hide and visible the view when no feed is available
     */
    private fun showNoFeedDigest() {
        showNoDataView(getString(R.string.no_data_found))
    }


    /*
    This method is used to refresh the feeds
     */
    private fun fetchFeeds() {
        if (Utility.isNetworkAvailable(this)) {
            showLoadingView()
            feedDigestViewModel?.getFeedDigest()
        } else {
            showNoDataView(getString(R.string.no_internet_available))
        }
    }

    private fun showNoDataView(message: String) {
        feedUiBinding.apply {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
            rvFeedDigest.visibility = View.GONE
            noFeedData.visibility = View.VISIBLE
            noFeedData.text = message
        }
    }

    private fun showLoadingView() {
        feedUiBinding.apply {
            noFeedData.visibility = View.GONE
            rvFeedDigest.visibility = View.GONE
            shimmerFrameLayout.visibility = View.VISIBLE
            shimmerFrameLayout.startShimmer()
        }
    }


    /*
    This method is used to visible and hide the views for showing the feeds
     */
    private fun showFeedDigestData() {
        feedUiBinding.apply {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
            noFeedData.visibility = View.GONE
            rvFeedDigest.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        feedUiBinding.shimmerFrameLayout.stopShimmer()
    }
}