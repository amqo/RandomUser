package com.amqo.randomuser.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.amqo.randomuser.R
import com.amqo.randomuser.data.db.entity.RandomUserEntry
import com.amqo.randomuser.databinding.FragmentRandomUserDetailBinding
import com.amqo.randomuser.internal.consume
import com.amqo.randomuser.ui.base.ScopedFragment
import com.amqo.randomuser.ui.detail.model.RandomUserDetailFragmentViewModel
import com.amqo.randomuser.ui.detail.model.RandomUserDetailFragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_random_user_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory

class RandomUserDetailFragment : ScopedFragment(), KodeinAware {

    companion object {

        const val ARG_USER_ID = "arg_user_id"
        const val ARG_USER_NAME = "arg_user_name"
    }

    override val kodein by closestKodein()
    private val detailFragmentViewModelFactoryInstanceFactory:
            ((String) -> RandomUserDetailFragmentViewModelFactory) by factory()

    private lateinit var detailFragmentViewModel: RandomUserDetailFragmentViewModel
    private lateinit var binding: FragmentRandomUserDetailBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userId = arguments?.getString(ARG_USER_ID, "") as String
        detailFragmentViewModel = ViewModelProviders.of(this,
            detailFragmentViewModelFactoryInstanceFactory(userId))
            .get(RandomUserDetailFragmentViewModel::class.java)
        bindUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = R.layout.fragment_random_user_detail
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return binding.root
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        consume(detailFragmentViewModel.randomUser, { randomUser ->
            binding.randomUser = randomUser
            bindInteractor(randomUser)
            bindNavigator()
            binding.executePendingBindings()
        })
    }

    private fun bindNavigator() {
        binding.randomUserNavigator = object : UserDetailNavigator {
            override fun navigateToSendMail() {
                this@RandomUserDetailFragment.navigateToSendMail()
            }
            override fun navigateToUserLocation() {
                this@RandomUserDetailFragment.navigateToUserLocation()
            }
            override fun navigateToUserImage() {
                this@RandomUserDetailFragment.navigateToUserImage()
            }
        }
    }

    private fun bindInteractor(randomUser: RandomUserEntry) {
        binding.randomUserInteractor = object : UserDetailInteractor {
            override fun getMailFormatted(): Spannable {
                return detailFragmentViewModel.getMailFormatted(randomUser)
            }
            override fun getRegisteredMessage(): Spannable {
                return detailFragmentViewModel.getRegisteredMessage(randomUser)
            }
            override fun getMapUrl(): String {
                val mapUrl = detailFragmentViewModel.getMapUrl(randomUser)
                if (mapUrl.isEmpty()) {
                    map_image.visibility = View.GONE
                }
                return mapUrl
            }
        }
    }

    private fun navigateToSendMail() {
        binding.randomUser?.let {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, it.email)
            startActivity(Intent.createChooser(intent, getString(R.string.message_send_mail)))
        }
    }

    private fun navigateToUserLocation() {
        activity?.let { activity ->
            binding.randomUser?.let {
                val gmmIntentUri = Uri.parse(
                    "geo:${it.location.coordinates.latitude},${it.location.coordinates.longitude}"
                )
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(activity.packageManager) != null) {
                    startActivity(mapIntent)
                }
            }
        }
    }

    private fun navigateToUserImage() {
        activity?.let{ activity ->
            binding.randomUser?.let {
                val intent = Intent(activity, RandomUserImageDetailActivity::class.java)
                intent.putExtra(RandomUserImageDetailActivity.ARG_USER_IMAGE_URL, it.picture.large)
                intent.putExtra(RandomUserImageDetailActivity.ARG_USER_NAME, it.getFullName())
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, binding.userImage as View, getString(R.string.user_image_transition))
                startActivity(intent, options.toBundle())
            }
        }
    }

    interface UserDetailInteractor {

        fun getMapUrl(): String

        fun getMailFormatted(): Spannable

        fun getRegisteredMessage(): Spannable
    }

    interface UserDetailNavigator {

        fun navigateToSendMail()

        fun navigateToUserLocation()

        fun navigateToUserImage()
    }
}
