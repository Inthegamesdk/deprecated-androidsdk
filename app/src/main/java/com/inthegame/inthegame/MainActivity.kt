package com.inthegame.inthegame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import com.inthegame.itgframework.ITGPlayerActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val videoURL = "https://media.inthegame.io/uploads/videos/4engagementvideo.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlayer.setOnClickListener { openPlayerActivity() }
        btnExample.setOnClickListener { openExampleActivity() }
    }

    fun openPlayerActivity() {
        if (!checkIfOnline()) return

        //show the full screen video player
        val intent = Intent(this, ITGPlayerActivity::class.java)
        var bundle = Bundle()
        bundle.putString(ITGPlayerActivity.URL_PARAM, videoURL)
        bundle.putString(ITGPlayerActivity.BROADCASTER_PARAM, "demos")
        intent.putExtras(bundle)
        startActivity(intent)
    }

    fun openExampleActivity() {
        if (!checkIfOnline()) return

        //example activity with a custom implementation
        val intent = Intent(this, ExampleActivity::class.java)
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
