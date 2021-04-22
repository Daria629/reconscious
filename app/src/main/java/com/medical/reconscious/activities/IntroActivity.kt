package com.medical.reconscious.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.medical.reconscious.R
import com.medical.reconscious.R.*
import com.medical.reconscious.fragments.IntroFragmentViewPager
import kotlinx.android.synthetic.main.activity_intro.*
import java.util.*

class IntroActivity : BaseActivity() , ViewPager.OnPageChangeListener {

    var imageViews = ArrayList<ImageView>()

    companion object {

        var imgReses = intArrayOf(
            drawable.intro_image1,
            drawable.intro_image2,
            drawable.intro_image3,
            drawable.intro_image4
        )

        var titles = intArrayOf(
            string.psychedelic_telehealth,
            string.how_it_works,
            string.how_it_works,
            string.how_it_works
        )

        var detailTitle1s = intArrayOf(
            string.intro_detail1_title,
            string.intro_detail2_title,
            string.intro_detail3_title,
            string.intro_detail4_title
        )

        var details = intArrayOf(
            string.intro_detail1,
            string.intro_detail2,
            string.intro_detail3,
            string.intro_detail4
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_intro)

        imageViews.add(img_dot1)
        imageViews.add(img_dot2)
        imageViews.add(img_dot3)
        imageViews.add(img_dot4)

        view_pager.adapter = MyPagerAdapter(supportFragmentManager)
        view_pager.addOnPageChangeListener(this)

        indicator(0)

        linear_start.setOnClickListener {
            val mainIntent = Intent(this, WelcomeActivity::class.java)
            startActivity(mainIntent)
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    private fun indicator(index: Int) {
        for (i in 0..3) {
            val imageView = imageViews[i]
            if (i == index) imageView.setImageResource(R.drawable.indicator_dot_selected)
            else imageView.setImageResource(R.drawable.indicator_dot_unselected)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        indicator(position)
    }

    class MyPagerAdapter constructor(fm: FragmentManager?) : FragmentPagerAdapter(
        fm!!,
        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getItem(pos: Int): Fragment {
            return when (pos) {
                0 -> IntroFragmentViewPager.newInstance(
                    titles[0],
                    detailTitle1s[0],
                    details[0],
                    imgReses[0],
                    200,
                    true
                )
                1 -> IntroFragmentViewPager.newInstance(
                    titles[1],
                    detailTitle1s[1],
                    details[1],
                    imgReses[1],
                    200,
                    false
                )
                2 -> IntroFragmentViewPager.newInstance(
                    titles[2],
                    detailTitle1s[2],
                    details[2],
                    imgReses[2],
                    200,
                    true
                )
                3 -> IntroFragmentViewPager.newInstance(
                    titles[3],
                    detailTitle1s[3],
                    details[3],
                    imgReses[3],
                    200,
                    true
                )

                else -> IntroFragmentViewPager.newInstance(
                    titles[0],
                    detailTitle1s[0],
                    details[0],
                    imgReses[0],
                    200,
                    true
                )
            }
        }

        override fun getCount(): Int {
            return 4
        }
    }

}