package com.sxcs.xuberance2020.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sxcs.xuberance2020.data.models.EventDetails
import com.sxcs.xuberance2020.databinding.ActivityEventRulesBinding

class EventRulesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventRulesBinding

    companion object {
        private const val PARAM1 = "param1"

        fun getIntent(context: Context, eventDetails: EventDetails) =
            Intent(context, EventRulesActivity::class.java).apply {
                putExtra(PARAM1, eventDetails)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventRulesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventDetails = intent.getSerializableExtra(PARAM1) as EventDetails
        binding.name.text = eventDetails.name
        binding.content.text = eventDetails.rules
    }
}