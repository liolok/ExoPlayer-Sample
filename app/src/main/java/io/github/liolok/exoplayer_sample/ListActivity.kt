package io.github.liolok.exoplayer_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseList

private const val GRID_SPAN_COUNT = 2  // grid layout column count

class ListActivity : AppCompatActivity() {
    private val items
        @ImplicitReflectionSerializer get() = Json.parseList<Item>(  // read a list of item from `res/raw/items.json`
            resources.openRawResource(R.raw.items).bufferedReader().use { it.readText() })  // convert to string

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        recyclerView.apply {
            setHasFixedSize(true) // use this to improve performance if layout size of RecyclerView doesn't change
            layoutManager = GridLayoutManager(this@ListActivity, GRID_SPAN_COUNT)  // use grid layout
            adapter = RecyclerAdapter(items)  // pass item list to adapter
        }
    }
}
