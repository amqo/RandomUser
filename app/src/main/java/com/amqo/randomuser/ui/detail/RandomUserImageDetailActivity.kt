package com.amqo.randomuser.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.amqo.randomuser.ui.base.GlideApp
import kotlinx.android.synthetic.main.activity_random_user_image_detail.*

class RandomUserImageDetailActivity : AppCompatActivity() {

    companion object {

        const val ARG_USER_IMAGE_URL = "arg_user_image_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.amqo.randomuser.R.layout.activity_random_user_image_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imageUrl = intent.getStringExtra(ARG_USER_IMAGE_URL)

        imageUrl?.let {
            GlideApp.with(this).load(imageUrl).into(random_user_image)
        } ?: finish()
    }

    override fun onBackPressed() = supportFinishAfterTransition()

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                true
            } else -> super.onOptionsItemSelected(item)
        }
}
