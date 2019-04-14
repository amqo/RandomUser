package com.amqo.randomuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.amqo.randomuser.R
import com.amqo.randomuser.databinding.RandomUserDetailBinding
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
    private lateinit var binding: RandomUserDetailBinding

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
            inflater, R.layout.random_user_detail ,container, false)
        return binding.root
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        consume(viewModel.randomUser, { randomUser ->
            binding.randomUser = randomUser
            binding.executePendingBindings()
        })
    }
}
