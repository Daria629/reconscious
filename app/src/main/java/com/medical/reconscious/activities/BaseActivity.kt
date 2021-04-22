package com.medical.reconscious.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.kaopiz.kprogresshud.KProgressHUD


open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )

        setkProgressHUD()
    }

    protected open fun setkProgressHUD() {
        kProgressHUD = KProgressHUD.create(this)
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setMaxProgress(100)
            .setDimAmount(0.2f)
            .setBackgroundColor(Color.TRANSPARENT)
    }
    companion object {

        @SuppressLint("StaticFieldLeak")
        var kProgressHUD: KProgressHUD? = null

        fun showHUD() {
            kProgressHUD!!.show()
        }

        fun hideHUD() {
            if (kProgressHUD!!.isShowing) {
                kProgressHUD!!.dismiss()
            }
        }
    }
}