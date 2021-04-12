package com.inthegame.inthegame

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.inthegame.itgframework.ITGOverlayListener
import com.inthegame.itgframework.ITGOverlayView
import com.inthegame.itgframework.ITGPlayerView
import kotlinx.android.synthetic.main.activity_overlay.*

class OverlayActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overlay)

        btnPlay.setOnClickListener { play() }
        btnPause.setOnClickListener { pause() }
        btnForward.setOnClickListener { forward() }
    }

    override fun onResume() {
        super.onResume()

        //create a listener to detect when the user taps the empty video area
        //(to show any custom controls, for example)
        val listener = object: ITGOverlayListener {
            override fun didOpenActivity() {
                Log.d("ITG", "did open activity")
            }

            override fun didCloseActivity() {
                Log.d("ITG", "did close activity")
            }

            override fun didTapVideo() {
                Log.d("ITG", "did tap video area")
            }

        }

        //load the overlay
        overlayView.load(videoURL, broadcaster, "en", listener, true)


        val uri = Uri.parse(videoURL)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()

        videoView.setOnPreparedListener { mp: MediaPlayer ->
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
            mp.setVolume(1f, 1f)
        }

        videoView.setOnCompletionListener { mp: MediaPlayer ->
            mp.seekTo(0)
            overlayView.sendCommand(ITGPlayerView.COMMAND_STOP)
        }
    }

    //when playback commands are sent to the video player,
    //we need to inform the overlay
    //this includes actions such as
    //play, pause, stop and update video time
    fun play() {
        if (!videoView.isPlaying) {
            videoView.start()
            overlayView.sendCommand(ITGOverlayView.COMMAND_PLAY)
        }
    }

    fun pause() {
        if (videoView.isPlaying) {
            videoView.pause()
            overlayView.sendCommand(ITGOverlayView.COMMAND_PAUSE)
        }
    }

    fun forward() {
        val timeMilli = videoView.currentPosition
        val newTime = timeMilli + 10000
        videoView.seekTo(newTime)
        overlayView.updateTime(newTime)
    }
}