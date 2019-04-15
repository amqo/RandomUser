package com.amqo.randomuser.ui.list.model

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class LivePagedListBuilderFactory<T> {

    companion object {

        const val DEFAULT_PAGES = 30
    }

    fun create(
        pages: Int = DEFAULT_PAGES,
        dataSourceFactory: DataSource.Factory<Int, T>
    ): LivePagedListBuilder<Int, T> =
        LivePagedListBuilder<Int, T>(dataSourceFactory, pages)

    fun  create(
        pages: Int = DEFAULT_PAGES,
        listBoundaryCallback: PagedList.BoundaryCallback<T>,
        dataSourceFactory: DataSource.Factory<Int, T>
    ): LivePagedListBuilder<Int, T> =
        LivePagedListBuilder<Int, T>(dataSourceFactory, pages).setBoundaryCallback(listBoundaryCallback)
}