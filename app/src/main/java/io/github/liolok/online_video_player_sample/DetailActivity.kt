package io.github.liolok.online_video_player_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val playerFragment = PlayerFragment()  // create a player fragment

        val item = intent.getParcelableExtra<Item>(Item.EXTRA_KEY)  // get item object from MainActivity
        supportActionBar?.title = item.title  // set actionbar title with item's
        val bundle = Bundle(); bundle.putParcelable(Item.EXTRA_KEY, item)  // pack item object up with a bundle
        playerFragment.arguments = bundle  // pass item object to player fragment through bundle

        supportFragmentManager.beginTransaction().add(R.id.detailContainer, playerFragment).commit()  // show fragment
    }
}
