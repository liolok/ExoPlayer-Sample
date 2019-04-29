package io.github.liolok.luxun

import android.R.anim
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

private const val DELAY_MILLIS: Long = 2000  // 2s delay

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, ListActivity::class.java))
            finish(); overridePendingTransition(anim.fade_in, anim.fade_out)  // fade to list activity
        }, DELAY_MILLIS)
    }
}
