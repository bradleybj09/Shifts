package com.shiftkey.codingchallenge.util

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {

    /**
     * binding to control visibility
     */
    @BindingAdapter("visibleOrGone")
    @JvmStatic fun View.setVisibleOrGone(visible: Boolean) {
        visibility = if (visible) { View.VISIBLE } else { View.GONE }
    }

}
