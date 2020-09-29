package com.sxcs.xuberance2020.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hzn.lib.EasyTransition
import com.squareup.picasso.Picasso
import com.sxcs.xuberance2020.data.models.EventDetails
import com.sxcs.xuberance2020.databinding.ActivityEventDetailsBinding
import com.sxcs.xuberance2020.utils.getDate

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailsBinding

    companion object {
        private const val PARAM_1 = "param1"

        fun getIntent(context: Context, param1: EventDetails) =
            Intent(context, EventDetailsActivity::class.java).apply {
                putExtra(PARAM_1, param1)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        EasyTransition.enter(this)

        val eventDetails = intent.getSerializableExtra(PARAM_1) as EventDetails
        with(binding) {
            textviewTitle.text = eventDetails.name
            textviewDate.text = eventDetails.date
            textviewTime.text = eventDetails.time
            textviewType.text = eventDetails.type
            textviewDescription.text = eventDetails.shortDesc
            Picasso
                .with(this@EventDetailsActivity)
                .load(eventDetails.imageUrl)
                .into(circleImageView)
            textviewRules.setOnClickListener {
                EventRulesActivity.getIntent(this@EventDetailsActivity, eventDetails).also {
                    startActivity(it)
                }
            }
        }
    }

    override fun onBackPressed() {
        EasyTransition.exit(this)
        super.onBackPressed()
    }
}