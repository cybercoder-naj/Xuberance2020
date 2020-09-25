package com.sxcs.xuberance2020.ui.activities

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.Constants.EXTRA_ENTRY_FRAGMENT
import com.sxcs.xuberance2020.data.Constants.FACEBOOK_PAGE_ALT
import com.sxcs.xuberance2020.data.Constants.FRAGMENT_ABOUT
import com.sxcs.xuberance2020.data.Constants.INSTAGRAM_PAGE
import com.sxcs.xuberance2020.data.Constants.YOUTUBE_CHANNEL
import com.sxcs.xuberance2020.databinding.ActivitySectionsBinding
import com.sxcs.xuberance2020.ui.fragments.*
import com.sxcs.xuberance2020.utils.moveGradient

class SectionsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivitySectionsBinding

    private val fragments = arrayOf(
        AboutFragment() to R.id.mAboutFragment,
        SchoolProfileFragment() to R.id.mSchoolProfileFragment,
        ScheduleFragment() to R.id.mScheduleFragment,
        EventsFragment() to R.id.mEventsFragment,
        SponsorsFragment() to R.id.mSponsorsFragment,
        TeamFragment() to R.id.mTeamFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySectionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.apply {
            title = ""
            subtitle = ""
        }
        binding.textViewToolbarTitle.typeface = ResourcesCompat.getFont(this, R.font.atmospheric)

        moveGradient(binding.drawerLayout.background as AnimationDrawable)
        setDrawerToggleButton()
        setImageViewOnClicks()
        setTextFont()

        val entryFragment = intent.getIntExtra(EXTRA_ENTRY_FRAGMENT, FRAGMENT_ABOUT)
        replaceFragment(entryFragment)
        binding.navigationView.apply {
            setNavigationItemSelectedListener(this@SectionsActivity)
            setCheckedItem(fragments[entryFragment].second)
        }
    }

    private fun setImageViewOnClicks() {
        binding.imageShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "" // TODO add text here.
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share the app via:"))
        }

        binding.imageYoutube.setOnClickListener {
            Intent(ACTION_VIEW).apply {
                `package` = "com.google.android.youtube"
                data = Uri.parse(YOUTUBE_CHANNEL)
            }.also {
                startActivity(it)
            }
        }

        binding.imageInsta.setOnClickListener {
            Intent(ACTION_VIEW).apply {
                `package` = "com.instagram.android"
                data = Uri.parse(INSTAGRAM_PAGE)
            }.also {
                startActivity(it)
            }
        }

        binding.imageFb.setOnClickListener {
            Intent(ACTION_VIEW, Uri.parse(FACEBOOK_PAGE_ALT)).also {
                startActivity(it)
            }
        }
    }

    private fun setTextFont() {
        binding.navigationView.menu.findItem(R.id.mHomePage).also {
            val spannable = SpannableString(it.title).apply {
                setSpan(
                    TextAppearanceSpan(this@SectionsActivity, R.style.FontTextNavigation),
                    0,
                    length,
                    0
                )
            }
            it.title = spannable
        }
        binding.navigationView.menu.findItem(R.id.mAboutFragment).also {
            val spannable = SpannableString(it.title).apply {
                setSpan(
                    TextAppearanceSpan(this@SectionsActivity, R.style.FontTextNavigation),
                    0,
                    length,
                    0
                )
            }
            it.title = spannable
        }
        binding.navigationView.menu.findItem(R.id.mSchoolProfileFragment).also {
            val spannable = SpannableString(it.title).apply {
                setSpan(
                    TextAppearanceSpan(this@SectionsActivity, R.style.FontTextNavigation),
                    0,
                    length,
                    0
                )
            }
            it.title = spannable
        }
        binding.navigationView.menu.findItem(R.id.mScheduleFragment).also {
            val spannable = SpannableString(it.title).apply {
                setSpan(
                    TextAppearanceSpan(this@SectionsActivity, R.style.FontTextNavigation),
                    0,
                    length,
                    0
                )
            }
            it.title = spannable
        }
        binding.navigationView.menu.findItem(R.id.mEventsFragment).also {
            val spannable = SpannableString(it.title).apply {
                setSpan(
                    TextAppearanceSpan(this@SectionsActivity, R.style.FontTextNavigation),
                    0,
                    length,
                    0
                )
            }
            it.title = spannable
        }/*
        binding.navigationView.menu.findItem(R.id.mLiveStreams).also {
            val spannable = SpannableString(it.title).apply {
                setSpan(TextAppearanceSpan(this@SectionsActivity, R.style.FontTextNavigation), 0, length, 0)
            }
            it.title = spannable
        }*/
        binding.navigationView.menu.findItem(R.id.mSponsorsFragment).also {
            val spannable = SpannableString(it.title).apply {
                setSpan(
                    TextAppearanceSpan(this@SectionsActivity, R.style.FontTextNavigation),
                    0,
                    length,
                    0
                )
            }
            it.title = spannable
        }
        binding.navigationView.menu.findItem(R.id.mTeamFragment).also {
            val spannable = SpannableString(it.title).apply {
                setSpan(
                    TextAppearanceSpan(this@SectionsActivity, R.style.FontTextNavigation),
                    0,
                    length,
                    0
                )
            }
            it.title = spannable
        }
    }


    private fun replaceFragment(fragmentID: Int) {
        replaceFragment(fragments[fragmentID].first)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            fragment,
            fragment::class.simpleName
        ).commit()

        binding.textViewToolbarTitle.text = when (fragment) {
            is AboutFragment -> "ABOUT US"
            is SchoolProfileFragment -> "SCHOOL PROFILE"
            is ScheduleFragment -> "SCHEDULE"
            is EventsFragment -> "EVENTS"
            is LiveStreamsFragment -> "WATCH NOW"
            is SponsorsFragment -> "SPONSORS"
            is TeamFragment -> "THE TEAM"
            else -> ""
        }
    }

    private fun setDrawerToggleButton() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.nav_app_bar_open_drawer_description,
            R.string.nav_app_bar_close_drawer_description
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mHomePage) {
            finish()
            return true
        }

        for (id in fragments)
            if (id.second == item.itemId)
                replaceFragment(id.first)
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        when (item.itemId) {
            R.id.mActionShare -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    "" // TODO add text here.
                )
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share the app via:"))
            }

            R.id.mYoutube -> {
                Intent(ACTION_VIEW).apply {
                    `package` = "com.google.android.youtube"
                    data = Uri.parse(YOUTUBE_CHANNEL)
                }.also {
                    startActivity(it)
                }
            }

            R.id.mInstagram -> {
                Intent(ACTION_VIEW).apply {
                    `package` = "com.instagram.android"
                    data = Uri.parse(INSTAGRAM_PAGE)
                }.also {
                    startActivity(it)
                }
            }

            R.id.mFacebook -> {
                Intent(ACTION_VIEW, Uri.parse(FACEBOOK_PAGE_ALT)).also {
                    startActivity(it)
                }
            }
        }
        return true
    }
}