package io.github.liolok.online_video_player_sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parseList

private const val GRID_SPAN_COUNT = 1  // grid layout column count, use 1 for list layout

class MainActivity : AppCompatActivity(), RecyclerAdapter.OnItemClickListener {
    private var isDualPane: Boolean = false  // is layout at sw600dp variation or just normal

    private val items
        @ImplicitReflectionSerializer get() = Json.parseList<Item>(  // read a list of item from `res/raw/items.json`
            resources.openRawResource(R.raw.items).bufferedReader().use { it.readText() })  // convert to string

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            setHasFixedSize(true)  // use this to improve performance if layout size of RecyclerView doesn't change
            layoutManager = GridLayoutManager(this@MainActivity, GRID_SPAN_COUNT)  // use grid layout
            adapter = RecyclerAdapter(items, this@MainActivity)  // pass item list to adapter
        }

        // Determine current layout: https://developer.android.com/training/multiscreen/adaptui#TaskDetermineCurLayout
        val fragmentContainer: View? = findViewById(R.id.fragmentContainer)
        isDualPane = fragmentContainer?.visibility == View.VISIBLE

        // Add a Fragment to the Activity at Runtime, in this case, it's default fragment of MainActivity.
        // https://developer.android.com/training/basics/fragments/fragment-ui#AddAtRuntime
        if (isDualPane) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) return

            val defaultFragment = PlayerFragment()  // create a new Fragment to be placed in the activity layout
            val bundle = Bundle(); bundle.putParcelable(Item.EXTRA_KEY, items[0])  // pack first item up with a bundle
            defaultFragment.arguments = bundle  // pass first item to default fragment

            // Add the fragment to the 'fragmentContainer' FrameLayout
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, defaultFragment).commit()
        }
    }

    override fun onItemClick(item: Item) {
        if (isDualPane) {
            val newFragment = PlayerFragment()
            val bundle = Bundle(); bundle.putParcelable(Item.EXTRA_KEY, item)  // pack the item up with a bundle
            newFragment.arguments = bundle  // pass first item to first fragment
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, newFragment).commit()
        }  // layout is at sw600dp variation, replace fragment on list item clicked.
        else {
            startActivity(Intent(this, DetailActivity::class.java).putExtra(Item.EXTRA_KEY, item))
        }  // layout is at normal variation, start detail activity on list item clicked.
    }  // implement RecyclerAdapter.OnItemClickListener
}
