package io.github.liolok.luxun

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize  // for intent extra, see more in RecyclerAdapter.ItemViewHolder.onClick() and DetailActivity.onCreate()
@Serializable  // for reading list from json resource file, see more in ListActivity.onCreate()
data class Item(val book: String, val title: String, val content: String) : Parcelable {
    companion object {
        const val EXTRA_KEY = "${BuildConfig.APPLICATION_ID}.Item"
    }  // intent extra key, use app's package name as prefix to be unique
}
