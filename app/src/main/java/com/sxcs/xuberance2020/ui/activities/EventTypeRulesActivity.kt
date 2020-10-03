package com.sxcs.xuberance2020.ui.activities

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.models.EventDetails
import com.sxcs.xuberance2020.databinding.ActivityEventTypeRulesBinding
import com.sxcs.xuberance2020.firebase.Database

class EventTypeRulesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventTypeRulesBinding

    companion object {
        const val EVENT_NAME = "event_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventTypeRulesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventName = intent.getStringExtra(EVENT_NAME)
        eventName?.let {
            if (it != "None")
                Database.getGroupEvents(it) { events ->
                    setRules(events.sorted())
                }
        }
    }

    private fun setRules(events: List<EventDetails>) {
        with(binding) {
            text1.text = HtmlCompat.fromHtml(events[0].rules, HtmlCompat.FROM_HTML_MODE_LEGACY)
            text2.text = HtmlCompat.fromHtml(events[1].rules, HtmlCompat.FROM_HTML_MODE_LEGACY)
            text3.text = HtmlCompat.fromHtml(events[2].rules, HtmlCompat.FROM_HTML_MODE_LEGACY)
            text4.text = HtmlCompat.fromHtml(events[3].rules, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }
}