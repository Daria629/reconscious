package com.medical.reconscious

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.medical.reconscious.activities.BaseActivity
import com.medical.reconscious.activities.DashboardActivity
import com.medical.reconscious.activities.IntroActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            if (prefs.token == null || prefs.token =="") {
                val mainIntent = Intent(this, IntroActivity::class.java)
                startActivity(mainIntent)
                finish()
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            } else {
                val mainIntent = Intent(this, DashboardActivity::class.java)
                startActivity(mainIntent)
                finish()
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }

        }, 1000)
    }
}