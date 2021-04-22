package com.medical.reconscious.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kaopiz.kprogresshud.KProgressHUD

open class BaseFragment: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setkProgressHUD()
    }

    protected open fun setkProgressHUD() {
        kProgressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setMaxProgress(100)
                .setDimAmount(0.2f)
                .setBackgroundColor(Color.TRANSPARENT)
    }
    companion object {

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