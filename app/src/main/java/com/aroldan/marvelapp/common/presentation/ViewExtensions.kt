package com.aroldan.marvelapp.common.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

inline fun <reified T> Activity.navigate(finishCurrentActivity: Boolean = false, bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)

    bundle?.let {
        intent.putExtras(it)
    }

    startActivity(intent)

    if (finishCurrentActivity) finish()
}

fun Activity.navigateBack(result: Int?, bundle: Bundle?) {
    val intent = Intent()

    bundle?.let {
        intent.putExtras(bundle)
    }

    result?.let {
        setResult(it, intent)
    }

    finish()
}

fun ImageView.loadFromUrl(url: String?) {
    url?.let {
        Glide.with(this)
            .load(it)
            .into(this)
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun Activity.snackbar(text: String): Snackbar? {
    val rootView = findViewById<View>(android.R.id.content)
    rootView?.run {
        return Snackbar.make(this, text, 3000)
    }

    return null
}
