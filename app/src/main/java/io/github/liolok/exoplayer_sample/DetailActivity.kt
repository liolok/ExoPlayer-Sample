package io.github.liolok.exoplayer_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val bundle = Bundle()  // get item object from MainActivity and put it into bundle
        bundle.putParcelable(Item.EXTRA_KEY, intent.getParcelableExtra(Item.EXTRA_KEY))

        val playerFragment = PlayerFragment()  // pass item object to player fragment through bundle
        playerFragment.arguments = bundle

        supportFragmentManager.beginTransaction().add(R.id.detailContainer, playerFragment).commit()  // show fragment
    }
}
