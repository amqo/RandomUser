package com.amqo.randomuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.amqo.randomuser.R
import com.amqo.randomuser.db.entity.RandomUserEntry
import com.amqo.randomuser.internal.consume
import com.amqo.randomuser.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory


class ItemDetailFragment : ScopedFragment(), KodeinAware {

    companion object {

        const val ARG_USER_ID = "arg_user_id"
    }

    override val kodein by closestKodein()
    private val viewModelFactoryInstanceFactory: ((String) -> RandomUserViewModelFactory) by factory()

    private lateinit var viewModel: RandomUserViewModel

    private var item: RandomUserEntry? = null

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
        return inflater.inflate(R.layout.item_detail, container, false)
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        consume(viewModel.randomUser, { randomUser ->
            item = randomUser
            activity?.toolbar_layout?.title = randomUser.getFullName()
            item_detail.text = randomUser.email
        })
    }
}
