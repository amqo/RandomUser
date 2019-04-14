package com.amqo.randomuser.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.amqo.randomuser.databinding.FragmentRandomUserDetailBinding
import com.amqo.randomuser.internal.consume
import com.amqo.randomuser.ui.base.ScopedFragment
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
    private val viewModelFactoryInstanceFactory: ((String) -> RandomUserViewModelFactory) by factory()

    private lateinit var viewModel: RandomUserViewModel
    private lateinit var binding: FragmentRandomUserDetailBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userId = arguments?.getString(ARG_USER_ID, "") as String
        viewModel = ViewModelProviders.of(this, viewModelFactoryInstanceFactory(userId))
            .get(RandomUserViewModel::class.java)

        bindUI()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.amqo.randomuser.R.layout.fragment_random_user_detail ,container, false)
        return binding.root
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        consume(viewModel.randomUser, { randomUser ->
            binding.randomUser = randomUser
            binding.randomUserInteractor = object : UserDetailInteractor {
                override fun getMailFormatted(): Spannable {
                    return viewModel.getMailFormatted(randomUser)
                }
                override fun getRegisteredMessage(): Spannable {
                    return viewModel.getRegisteredMessage(randomUser)
                }
                override fun getMapUrl(): String {
                    return viewModel.getMapUrl(randomUser)
                }
                override fun navigateToSendMail() {
                    this@RandomUserDetailFragment.navigateToSendMail()
                }
                override fun navigateToUserLocation() {
                    this@RandomUserDetailFragment.navigateToUserLocation()
                }
            }
            binding.executePendingBindings()
        })
    }

    fun navigateToSendMail() {
        binding.randomUser?.let {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, it.email)
            startActivity(Intent.createChooser(intent, "Send Email"))
        }
    }

    fun navigateToUserLocation() {
        activity?.let { activity ->
            binding.randomUser?.let {
                val gmmIntentUri = Uri.parse(
                    "geo:" +
                            "${it.location.coordinates.latitude}," +
                            "${it.location.coordinates.longitude}"
                )
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(activity.packageManager) != null) {
                    startActivity(mapIntent)
                }
            }
        }
    }

    interface UserDetailInteractor {

        fun navigateToSendMail()

        fun navigateToUserLocation()

        fun getMapUrl(): String

        fun getMailFormatted(): Spannable

        fun getRegisteredMessage(): Spannable
    }
}
