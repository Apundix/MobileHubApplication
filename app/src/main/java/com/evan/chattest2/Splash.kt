package com.evan.chattest2

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val currentProgress = 6000
        progressBar.max = 6000

        ObjectAnimator.ofInt(progressBar, "progress", currentProgress)
            .setDuration(6000)
            .start()

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this@Splash, Login::class.java)
            startActivity(intent)

        } ,6000)

    }
}