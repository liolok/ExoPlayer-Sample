package io.github.liolok.exoplayer_sample

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var player: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val item = intent.getParcelableExtra<Item>(Item.EXTRA_KEY)  // get item object from list activity
        player = ExoPlayerFactory.newSimpleInstance(this)  // initialize player instance
        playerView.player = player  // bind the player to the view
        player.apply {
            playWhenReady = true  // whether auto-play video when ready
            prepare(  // prepare the player with `ProgressiveMediaSource` for regular media files
                ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(getString(R.string.app_name)))
                    .createMediaSource(Uri.parse(item.url))
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()  // release the player when activity is destroyed, or it will still play in background.
    }
}
