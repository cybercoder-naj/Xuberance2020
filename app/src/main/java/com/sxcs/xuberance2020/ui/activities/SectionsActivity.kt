package com.sxcs.xuberance2020.ui.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.Constants.FACEBOOK_PAGE_ALT
import com.sxcs.xuberance2020.data.Constants.INSTAGRAM_PAGE
import com.sxcs.xuberance2020.data.Constants.YOUTUBE_CHANNEL
import com.sxcs.xuberance2020.databinding.ActivitySectionsBinding
import com.sxcs.xuberance2020.firebase.Authentication
import com.sxcs.xuberance2020.firebase.Database
import com.sxcs.xuberance2020.ui.fragments.*
import com.sxcs.xuberance2020.utils.moveGradient

class SectionsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivitySectionsBinding

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
        setProfileVisibility()

        val curVersion = packageManager.getPackageInfo(packageName, 0).versionName
        Database.getLatestVersion {
            if (it > curVersion)
                showDialog(curVersion, it)
        }

        replaceFragment(AboutFragment())
        binding.navigationView.apply {
            setNavigationItemSelectedListener(this@SectionsActivity)
            setCheckedItem(R.id.mAboutFragment)
        }
    }

    private fun setProfileVisibility() {
        binding.navigationView.menu.findItem(R.id.mProfileFragment).isVisible =
            Authentication.user != null
    }

    private fun setImageViewOnClicks() {
        binding.imageShare.setOnClickListener {
            val link = "https://play.google.com/store/apps/details?id=$packageName"
            Intent(Intent.ACTION_SEND).apply {
                putExtra(
                    Intent.EXTRA_TEXT,
                    "St. Xavier's Collegiate School presents \n\n\"X-Uberance '20\"\nDownload the app on Google Play Store now: $link"
                )
                type = "text/plain"
            }.also {
                startActivity(Intent.createChooser(it, "Share the app via:"))
            }
        }

        binding.imageYoutube.setOnClickListener {
            try {
                Intent(ACTION_VIEW).apply {
                    `package` = "com.google.android.youtube"
                    data = Uri.parse(YOUTUBE_CHANNEL)
                }.also {
                    startActivity(it)
                }
            } catch (e: Exception) {
                Intent(ACTION_VIEW, Uri.parse(YOUTUBE_CHANNEL)).also {
                    startActivity(it)
                }
            }
        }

        binding.imageInsta.setOnClickListener {
            try {
                Intent(ACTION_VIEW).apply {
                    `package` = "com.instagram.android"
                    data = Uri.parse(INSTAGRAM_PAGE)
                }.also {
                    startActivity(it)
                }
            } catch (e: Exception) {
                Intent(ACTION_VIEW, Uri.parse(INSTAGRAM_PAGE)).also {
                    startActivity(it)
                }
            }
        }

        binding.imageFb.setOnClickListener {
            // TODO facebook page id
            Intent(ACTION_VIEW, Uri.parse(FACEBOOK_PAGE_ALT)).also {
                startActivity(it)
            }
        }
    }

    private fun setTextFont() {
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
        binding.navigationView.menu.findItem(R.id.mProfileFragment).also {
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

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            fragment,
            fragment::class.simpleName
        ).commit()

        if (fragment is ProfileFragment) {
            binding.textViewToolbarTitle.isVisible = false
        }

        binding.textViewToolbarTitle.text = when (fragment) {
            is AboutFragment -> "ABOUT US"
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
        replaceFragment(
            when (item.itemId) {
                R.id.mAboutFragment -> AboutFragment()
                R.id.mScheduleFragment -> ScheduleFragment()
                R.id.mEventsFragment -> EventsFragment()
                R.id.mSponsorsFragment -> SponsorsFragment()
                R.id.mTeamFragment -> TeamFragment()
                R.id.mProfileFragment -> ProfileFragment()
                else -> AboutFragment()
            }
        )
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showDialog(curVersion: String, latestVersion: String) {
        AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
            .setTitle("New Version Available!")
            .setCancelable(false)
            .setMessage(
                "You are current using version $curVersion\nHowever, version $latestVersion is out!" +
                        "\nYou cannot use the app without installing the latest version."
            )
            .setPositiveButton("Go to Google Play", null)
            .setNegativeButton("Close App") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .create().apply {
                setOnShowListener {
                    getButton(AlertDialog.BUTTON_POSITIVE).apply {
                        setOnClickListener {
                            openGooglePlay()
                        }
                    }
                }
            }.show()
    }

    private fun openGooglePlay() {
        val marketLink = "market://details?id=$packageName"
        val webLink = "https://play.google.com/store/apps/details?id=$packageName"
        try {
            Intent(ACTION_VIEW, Uri.parse(marketLink)).also {
                startActivity(it)
            }
        } catch (e: ActivityNotFoundException) {
            Intent(ACTION_VIEW, Uri.parse(webLink)).also {
                startActivity(it)
            }
        }
    }
}