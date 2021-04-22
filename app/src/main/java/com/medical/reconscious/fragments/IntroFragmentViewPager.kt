package com.medical.reconscious.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.medical.reconscious.BuildConfig
import com.medical.reconscious.R
import com.medical.reconscious.activities.IntroActivity
import com.medical.reconscious.activities.WelcomeActivity
import kotlinx.android.synthetic.main.fragment_intro_item.view.*

class IntroFragmentViewPager : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_intro_item, container, false)

        if (BuildConfig.DEBUG && arguments == null) {
            error("Assertion failed")
        }

        view.txt_title1.text = getString(arguments!!.getInt("title"))
        view.txt_title2.text = getString(arguments!!.getInt("title1"))
        view.txt_detail.text = getString(arguments!!.getInt("detail"))
        view.img_intro.setImageResource(arguments!!.getInt("img"))

        if (arguments!!.getBoolean("isLeft")) {
            view.img_left.visibility = View.VISIBLE
            view.img_right.visibility = View.GONE
        } else {
            view.img_left.visibility = View.GONE
            view.img_right.visibility = View.VISIBLE
        }

        return view
    }

    companion object {
        fun newInstance(title: Int, title1: Int, detail: Int, image: Int, posX: Int, isLeft: Boolean) : IntroFragmentViewPager {
            val IntroFragmentViewPager = IntroFragmentViewPager()
            val b = Bundle()
            b.putInt("title", title)
            b.putInt("title1", title1)
            b.putInt("detail", detail)
            b.putInt("img", image)
            b.putInt("posX", posX)
            b.putBoolean("isLeft", isLeft)
            IntroFragmentViewPager.setArguments(b)
            return IntroFragmentViewPager
        }
    }

}