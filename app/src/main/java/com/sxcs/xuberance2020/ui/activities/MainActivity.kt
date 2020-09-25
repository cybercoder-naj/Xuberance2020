package com.sxcs.xuberance2020.ui.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.Constants.EXTRA_ENTRY_FRAGMENT
import com.sxcs.xuberance2020.data.Constants.FRAGMENT_ABOUT
import com.sxcs.xuberance2020.data.Constants.FRAGMENT_EVENTS
import com.sxcs.xuberance2020.data.Constants.FRAGMENT_SCHEDULE
import com.sxcs.xuberance2020.data.Constants.FRAGMENT_SCHOOL
import com.sxcs.xuberance2020.data.Constants.FRAGMENT_SPONSORS
import com.sxcs.xuberance2020.data.Constants.FRAGMENT_TEAM
import com.sxcs.xuberance2020.databinding.ActivityMainBinding
import com.sxcs.xuberance2020.firebase.Database

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            imageAbout.setOnClickListener(this@MainActivity)
            imageSchool.setOnClickListener(this@MainActivity)
            imageSchedule.setOnClickListener(this@MainActivity)
            imageEvents.setOnClickListener(this@MainActivity)
            imageSponsors.setOnClickListener(this@MainActivity)
            imageTeam.setOnClickListener(this@MainActivity)
        }

        val curVersion = packageManager.getPackageInfo(packageName, 0).versionName
        Database.getLatestVersion { latestVersion ->
            if (latestVersion > curVersion)
                showDialog(curVersion, latestVersion)
        }
    }

    private fun showDialog(curVersion: String, latestVersion: String) {
        AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert)
            .setTitle("New Version Available!")
            .setCancelable(false)
            .setMessage(
                "You are current using version $curVersion\nHowever, version $latestVersion is out!" +
                        "\nYou cannot use the app without installing the latest version."
            )
            .setPositiveButton("Go to Google Play") { dialog, _ ->
                openGooglePlay()
                dialog.dismiss()
            }
            .setNegativeButton("Close App") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .create().show()
    }

    private fun openGooglePlay() {
        val marketLink = "market://details?id=$packageName"
        val webLink = "https://play.google.com/store/apps/details?id=$packageName"
        try {
            Intent(Intent.ACTION_VIEW, Uri.parse(marketLink)).also {
                startActivity(it)
            }
        } catch (e: ActivityNotFoundException) {
            Intent(Intent.ACTION_VIEW, Uri.parse(webLink)).also {
                startActivity(it)
            }
        }
    }

    override fun onClick(v: View?) {
        Intent(this, SectionsActivity::class.java).apply {
            putExtra(
                EXTRA_ENTRY_FRAGMENT,
                when (v?.id) {
                    R.id.image_about -> FRAGMENT_ABOUT
                    R.id.image_school -> FRAGMENT_SCHOOL
                    R.id.image_schedule -> FRAGMENT_SCHEDULE
                    R.id.image_events -> FRAGMENT_EVENTS
                    R.id.image_sponsors -> FRAGMENT_SPONSORS
                    R.id.image_team -> FRAGMENT_TEAM
                    else -> FRAGMENT_ABOUT
                }
            )
        }.also {
            startActivity(it)
        }
    }
}