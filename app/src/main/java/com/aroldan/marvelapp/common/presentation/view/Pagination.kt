package com.aroldan.marvelapp.common.presentation.view

import androidx.recyclerview.widget.RecyclerView

class Pagination {
    private var recyclerView: RecyclerView? = null
    private var isLoading: Boolean = false
    private var isEnabled: Boolean = true
    private var onLoadMoreItemsListener: OnLoadMoreItemsListener? = null

    interface OnLoadMoreItemsListener {
        fun onLoadMoreItems()
    }

    fun onError() {
        isLoading = false
    }

    fun disable() {
        isEnabled = false
    }

    fun enable() {
        isEnabled = true
    }

    class Builder {

        var pagination = Pagination()

        fun with(recyclerView: RecyclerView): Builder {
            pagination.recyclerView = recyclerView

            pagination.recyclerView?.apply {
                addOnScrollListener(object: RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)

                        if (pagination.isEnabled && !pagination.isLoading && !recyclerView.canScrollVertically(1)) {
                            pagination.isLoading = true
                            pagination.onLoadMoreItemsListener?.onLoadMoreItems()
                        }
                    }
                })

                adapter?.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
                    override fun onChanged() {
                        super.onChanged()

                        pagination.isLoading = false
                    }
                })
            }

            return this
        }

        fun onLoadMoreItemsListener(onLoadMoreItemsListener: OnLoadMoreItemsListener): Builder {
            pagination.onLoadMoreItemsListener = onLoadMoreItemsListener
            return this
        }

        fun onLoadMoreItemsListener(onLoadMoreItemsListener: () -> Unit): Builder {
            pagination.onLoadMoreItemsListener = object : OnLoadMoreItemsListener {
                override fun onLoadMoreItems() {
                    onLoadMoreItemsListener()
                }
            }

            return this
        }

        fun build(): Pagination {
            return pagination
        }
    }
}