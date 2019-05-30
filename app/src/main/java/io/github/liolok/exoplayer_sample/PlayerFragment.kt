package io.github.liolok.exoplayer_sample

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class PlayerFragment : Fragment() {
    private lateinit var player: SimpleExoPlayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (container == null) return null  // create and return view only in dual pane mode

        val playerView = PlayerView(activity)

        // https://exoplayer.dev/hello-world.html
        player = ExoPlayerFactory.newSimpleInstance(activity)  // initialize player instance
        playerView.player = player  // bind the player to the view
        val item = arguments?.getParcelable<Item>(Item.EXTRA_KEY)  // get item object from list activity
        player.apply {
            playWhenReady = true  // whether auto-play video when ready
            prepare(
                ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(getString(R.string.app_name)))
                    .createMediaSource(Uri.parse(item?.url))
            )  // prepare the player with `ProgressiveMediaSource`, which is for regular media files like "*.mp4".
        }

        return playerView
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()  // release the player when fragment is destroyed, or it will still play in background.
    }
}
