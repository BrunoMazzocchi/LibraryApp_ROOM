package com.example.libraryapproom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.libraryapproom.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startSplashScreen()
    }
    private fun startSplashScreen(timeDelayInMilis : Long = 2000) {
        Handler(Looper.myLooper()!!).postDelayed(
            {
                startFirstActivity()
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }, timeDelayInMilis
        )
    }

    private fun startFirstActivity() = startActivity(Intent(this, MainActivity::class.java))
}