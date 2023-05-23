package com.dayker.airsearch.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BasePresenter<T : BaseView> {
    protected var view: T? = null
    protected val coroutineScope = CoroutineScope(Dispatchers.IO)

   open fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        this.view = null
        coroutineScope.cancel()
    }
}