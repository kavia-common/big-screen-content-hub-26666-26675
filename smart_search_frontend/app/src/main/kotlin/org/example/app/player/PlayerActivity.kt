package org.example.app.player

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.VideoView
import org.example.app.R

/**
 * Simple local player screen using VideoView.
 *
 * Expects a local resource named res/raw/sample_video.mp4 (or any supported format).
 */
class PlayerActivity : Activity() {

    private lateinit var titleView: TextView
    private lateinit var videoView: VideoView
    private lateinit var playPauseButton: Button
    private lateinit var seekBar: SeekBar

    private val handler = Handler(Looper.getMainLooper())
    private var isTracking = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        titleView = findViewById(R.id.playerTitle)
        videoView = findViewById(R.id.videoView)
        playPauseButton = findViewById(R.id.playPauseButton)
        seekBar = findViewById(R.id.seekBar)

        titleView.text = intent.getStringExtra(EXTRA_TITLE) ?: getString(R.string.player_title)

        val videoResId = resources.getIdentifier("sample_video", "raw", packageName)
        if (videoResId != 0) {
            val uri = Uri.parse("android.resource://$packageName/$videoResId")
            videoView.setVideoURI(uri)
        }

        playPauseButton.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
                playPauseButton.setText(R.string.player_play)
            } else {
                videoView.start()
                playPauseButton.setText(R.string.player_pause)
                scheduleProgressUpdates()
            }
        }

        videoView.setOnPreparedListener { mp ->
            seekBar.max = mp.duration
            scheduleProgressUpdates()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // no-op
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isTracking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val p = seekBar?.progress ?: 0
                videoView.seekTo(p)
                isTracking = false
            }
        })
    }

    private fun scheduleProgressUpdates() {
        handler.removeCallbacksAndMessages(null)
        handler.post(object : Runnable {
            override fun run() {
                if (!isTracking) {
                    seekBar.progress = videoView.currentPosition
                }
                handler.postDelayed(this, 500)
            }
        })
    }

    companion object {
        const val EXTRA_TITLE = "extra_title"
    }
}
