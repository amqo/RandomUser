package com.amqo.randomuser.ui.detail

import android.R
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import com.amqo.randomuser.ui.base.GlideApp
import kotlinx.android.synthetic.main.activity_random_user_image_detail.*

class RandomUserImageDetailActivity : AppCompatActivity() {

    companion object {

        const val ARG_USER_IMAGE_URL = "arg_user_image_url"
        const val ARG_USER_NAME = "arg_user_name"
    }

    var gradientDrawable: GradientDrawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.amqo.randomuser.R.layout.activity_random_user_image_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getStringExtra(ARG_USER_IMAGE_URL)?.let {
            GlideApp.with(this).load(it).into(random_user_image)
        } ?: finish()

        intent.getStringExtra(ARG_USER_NAME)?.let {
            supportActionBar?.title = it
        }
        animateTransitionRounded()
    }

    override fun onBackPressed() = finishAfterTransition()

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.home -> {
                finishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    // Private functions

    private fun animateTransitionRounded() {
        gradientDrawable = GradientDrawable()
        gradientDrawable?.shape = GradientDrawable.RECTANGLE

        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onSharedElementEnd(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                var sharedImageView: ImageView? = null
                sharedElements?.forEach {
                    if (it is ImageView) {
                        sharedImageView = it
                    }
                }
                sharedImageView?.also {
                    animateToRounded(it)
                }
            }
        })
    }

    private fun animateToRounded(it: ImageView) {
        gradientDrawable?.cornerRadius = 0f
        it.background = gradientDrawable
        it.clipToOutline = true
        val cornerAnimation = ObjectAnimator.ofFloat(
            gradientDrawable, "cornerRadius", 0f, 200f
        )
        val animatorSet = AnimatorSet()
        animatorSet.duration = 500
        animatorSet.play(cornerAnimation)
        animatorSet.start()
    }
}
