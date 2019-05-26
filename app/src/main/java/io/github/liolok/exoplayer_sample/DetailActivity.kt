package io.github.liolok.exoplayer_sample

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

private const val DEFAULT_RATING = 0F  // use when no corresponding rating value exists in preference file

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)  // use custom collapsing toolbar

        // Get item object from list activity and read details
        val item = intent.getParcelableExtra<Item>(Item.EXTRA_KEY)
        supportActionBar?.apply {
            title = item.title  // use item's title
            setDisplayHomeAsUpEnabled(true)  // enable back button
        }
        content.text = item.content
        cover.setImageResource(resources.getIdentifier("cover_${item.book}", "drawable", packageName))

        // Need only one preference file to store rating values
        val ratingPref = getPreferences(Context.MODE_PRIVATE)
        ratingBar.apply {
            rating = ratingPref.getFloat(item.title, DEFAULT_RATING)  // read rating value from preference file
            setOnRatingBarChangeListener { _, rating, fromUser ->
                if (fromUser) with(ratingPref.edit()) { putFloat(item.title, rating); apply() }
            }  // write value into preference file on user changes rating
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {  // back button on action bar
            finishAfterTransition()  // use shared element transition when return to list activity
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
