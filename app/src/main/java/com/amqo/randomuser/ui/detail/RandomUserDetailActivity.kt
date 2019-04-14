package com.amqo.randomuser.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.amqo.randomuser.R
import com.amqo.randomuser.databinding.ActivityRandomUserDetailBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_random_user_detail.*

class RandomUserDetailActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityRandomUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_random_user_detail)

        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initFabRemoveButton()
        if (savedInstanceState == null) {
            addDetailFragment()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            } else -> super.onOptionsItemSelected(item)
        }

    // Private functions

    private fun initFabRemoveButton() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Please confirm, are you sure?", Snackbar.LENGTH_LONG)
                .setAction("Remove") {
                    Log.e("TEST", "Removed")
                }.show()
        }
    }

    private fun addDetailFragment() {
        val fragment = RandomUserDetailFragment().apply {
            arguments = Bundle().apply {
                activityMainBinding.randomUserName =
                    intent.getStringExtra(RandomUserDetailFragment.ARG_USER_NAME)
                activityMainBinding.executePendingBindings()
                putString(
                    RandomUserDetailFragment.ARG_USER_ID,
                    intent.getStringExtra(RandomUserDetailFragment.ARG_USER_ID)
                )
            }
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.item_detail_container, fragment)
            .commit()
    }
}
