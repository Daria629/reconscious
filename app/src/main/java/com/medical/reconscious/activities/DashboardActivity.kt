package com.medical.reconscious.activities

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.medical.reconscious.R
import com.medical.reconscious.fragments.*
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(
            R.id.container,
            fragment,
            fragment.javaClass.getSimpleName()
        )
            .commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.navigation_home -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.container,
                    fragment,
                    fragment.javaClass.getSimpleName()
                )
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_integrations -> {
                val fragment = IntegrationFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.container,
                    fragment,
                    fragment.javaClass.getSimpleName()
                )
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_journal -> {
                val fragment = JournalFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.container,
                    fragment,
                    fragment.javaClass.getSimpleName()
                )
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_messages -> {
                val fragment = MessageFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.container,
                    fragment,
                    fragment.javaClass.getSimpleName()
                )
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction().replace(
                    R.id.container,
                    fragment,
                    fragment.javaClass.getSimpleName()
                )
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}