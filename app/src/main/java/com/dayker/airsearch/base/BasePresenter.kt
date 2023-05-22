package com.dayker.airsearch.base

abstract class BasePresenter<T : BaseView> {
    private var view: T? = null

    fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}