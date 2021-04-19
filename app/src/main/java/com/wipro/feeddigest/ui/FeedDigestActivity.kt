package com.wipro.feeddigest.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.wipro.feeddigest.R
import com.wipro.feeddigest.adapter.FeedDigestAdapter
import com.wipro.feeddigest.databinding.FeedUiBinding
import com.wipro.feeddigest.model.FeedDigest
import com.wipro.feeddigest.utilities.Utility
import com.wipro.feeddigest.viewmodel.FeedDigestViewModel
import org.jetbrains.annotations.TestOnly

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
        menuInflater.inflate(R.menu.main, menu);
        return true;
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
        feedDigestViewModel = ViewModelProvider(this).get(FeedDigestViewModel::class.java)
        fetchFeeds()
        feedDigestViewModel?.feedDigestResponse?.observe(this, Observer { feedDigestList ->
            if (feedDigestList.isEmpty()) {
                showNoFeedDigest()
            } else {
                showFeedDigestData()
                feedDigestAdapter?.setFeedDigest(feedDigestList)
            }
        })

        feedDigestViewModel?.feedDigestTitle?.observe(this, Observer { digestTitle ->
            title = digestTitle
        })

        feedDigestViewModel?.feedDigestResponseError?.observe(this, Observer {
            showNoFeedDigest()
        })
    }

    override fun onResume() {
        super.onResume()
        feedUiBinding.shimmerFrameLayout.startShimmerAnimation()
    }

    /*
    This method is responsible for hide and visible the view when no feed is available
     */
    private fun showNoFeedDigest() {
        feedUiBinding.let {
            it.rvFeedDigest.visibility = View.GONE
            it.noFeedData.visibility = View.VISIBLE
            it.shimmerFrameLayout.visibility = View.GONE
            it.shimmerFrameLayout.stopShimmerAnimation()
        }
    }


    /*
    This method is used to refresh the feeds
     */
    private fun fetchFeeds() {
        if (Utility.isNetworkAvailable(FeedDigestActivity@ this)) {
            feedUiBinding.let {
                it.noFeedData.visibility = View.GONE
                it.shimmerFrameLayout.visibility = View.VISIBLE
                it.rvFeedDigest.visibility = View.GONE
                it.shimmerFrameLayout.startShimmerAnimation()
            }
            feedDigestViewModel?.getFeedDigest()
        } else {
            feedUiBinding.let {
                it.shimmerFrameLayout.visibility = View.GONE
                it.rvFeedDigest.visibility = View.GONE
                it.noFeedData.visibility = View.VISIBLE
                it.noFeedData.text = "No internet available"
            }
        }
    }

    /*
    This method is used to visible and hide the views for showing the feeds
     */
    private fun showFeedDigestData() {
        feedUiBinding.let {
            it.shimmerFrameLayout.stopShimmerAnimation()
            it.rvFeedDigest.visibility = View.VISIBLE
            it.noFeedData.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        feedUiBinding.shimmerFrameLayout.stopShimmerAnimation()
    }

    @TestOnly
    fun setTestViewModel(feedViewModel: FeedDigestViewModel) {
        feedDigestViewModel = feedViewModel
    }
}