package com.inthegame.inthegame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.inthegame.itgframework.ITGPlayerActivity
import kotlinx.android.synthetic.main.activity_main.*

val videoURL = "https://api-dev.inthegame.io/uploads/videos/streamers/a64706dd0f42356e93d299075940c456.857ecbb7a131f9bb4940a6b8ad5ec70e.mp4"
val broadcaster = "orlandofcchannel"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlayer.setOnClickListener { openPlayerActivity() }
        btnExample.setOnClickListener { openExampleActivity() }
        btnOverlay.setOnClickListener { openOverlayActivity() }
    }

    fun openPlayerActivity() {
        if (!checkIfOnline()) return

        //show the full screen video player
        val intent = Intent(this, ITGPlayerActivity::class.java)
        var bundle = Bundle()
        bundle.putString(ITGPlayerActivity.URL_PARAM, videoURL)
        bundle.putString(ITGPlayerActivity.BROADCASTER_PARAM, broadcaster)
        bundle.putBoolean(ITGPlayerActivity.DEVMODE_PARAM, true)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun openExampleActivity() {
        if (!checkIfOnline()) return

        //example activity with a custom implementation
        val intent = Intent(this, ExampleActivity::class.java)
        startActivity(intent)
    }

    fun openOverlayActivity() {
        if (!isOnline(this)) {
            Toast.makeText(this@MainActivity, "No internet connection available", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, OverlayActivity::class.java)
        startActivity(intent)
    }

    private fun checkIfOnline() : Boolean {
        val online = isOnline(this)
        if (!online) {
            Toast.makeText(this@MainActivity, "No internet connection available", Toast.LENGTH_SHORT).show()
        }
        return online
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
