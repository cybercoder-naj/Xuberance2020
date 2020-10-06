package com.sxcs.xuberance2020.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.databinding.ActivityBetBinding
import com.sxcs.xuberance2020.firebase.Database
import com.sxcs.xuberance2020.utils.MyTextWater
import com.sxcs.xuberance2020.utils.getInt
import com.sxcs.xuberance2020.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBetBinding

    companion object {
        fun getIntent(context: Context) = Intent(context, BetActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Default).launch {
            loadInitialLogs()
            setTextWatchers()
        }

        Database.hasInvested {
            if (it)
                disableCommands()
        }

        with(binding) {
            btnInvest.setOnClickListener {
                if (getXPoints() >= 0) {
                    AlertDialog.Builder(this@BetActivity)
                        .setTitle("Investing")
                        .setMessage("Do you want to invest? You cannot undo this action.")
                        .setPositiveButton("Confirm") { dialog, _ ->
                            val map = mapOf(
                                "X-RAGA" to event1.getInt(),
                                "X-REEL" to event2.getInt(),
                                "X-VOGUE" to event3.getInt(),
                                "X-GAG" to event4.getInt(),
                                "X-ACOUSTIC" to event5.getInt(),
                                "X-TRAVAGANCE" to event6.getInt()
                            )
                            Database.invest(map) {
                                if (it) {
                                    disableCommands()
                                    dialog.dismiss()
                                    toast("You have successfully invested")
                                } else
                                    toast("Error investing. Please Try again.")
                            }
                        }
                        .setNegativeButton("Review Again") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create().show()
                } else {
                    toast("You are investing more than 1000 X-Points!")
                }
            }
        }
    }

    private fun disableCommands() {
        binding.event1.isEnabled = false
        binding.event2.isEnabled = false
        binding.event3.isEnabled = false
        binding.event4.isEnabled = false
        binding.event5.isEnabled = false
        binding.event6.isEnabled = false
        binding.btnInvest.isEnabled = false
    }

    private fun setTextWatchers() {
        binding.event1.addTextChangedListener(MyTextWater(binding.event1) { investCoins() })
        binding.event2.addTextChangedListener(MyTextWater(binding.event2) { investCoins() })
        binding.event3.addTextChangedListener(MyTextWater(binding.event3) { investCoins() })
        binding.event4.addTextChangedListener(MyTextWater(binding.event4) { investCoins() })
        binding.event5.addTextChangedListener(MyTextWater(binding.event5) { investCoins() })
        binding.event6.addTextChangedListener(MyTextWater(binding.event6) { investCoins() })
    }

    private fun investCoins() {
        with(binding) {
            var coins = getXPoints()

            if (coins <= 0) {
                coins = 0
                textViewCoins.setTextColor(Color.RED)
            } else
                textViewCoins.setTextColor(Color.BLACK)
            textViewCoins.text = String.format(getString(R.string.you_have_d_coins), coins)
        }
    }

    private fun getXPoints() =
        with(binding) {
            (1000 - event1.getInt() - event2.getInt() - event3.getInt() - event4.getInt() - event5.getInt() - event6.getInt())
        }

    private suspend fun loadInitialLogs() {
        val job = CoroutineScope(Default).launch {
            Database.getLogForEvent("X-RAGA") {
                if (it?.toString() ?: "0" == "0")
                    binding.event1.hint = "0"
                else
                    binding.event1.setText(it?.toString() ?: "0")
            }
            Database.getLogForEvent("X-REEL") {
                if (it?.toString() ?: "0" == "0")
                    binding.event2.hint = "0"
                else
                    binding.event2.setText(it?.toString() ?: "0")
            }
            Database.getLogForEvent("X-VOGUE") {
                if (it?.toString() ?: "0" == "0")
                    binding.event3.hint = "0"
                else
                    binding.event3.setText(it?.toString() ?: "0")
            }
            Database.getLogForEvent("X-GAG") {
                if (it?.toString() ?: "0" == "0")
                    binding.event4.hint = "0"
                else
                    binding.event4.setText(it?.toString() ?: "0")
            }
            Database.getLogForEvent("X-ACOUSTIC") {
                if (it?.toString() ?: "0" == "0")
                    binding.event5.hint = "0"
                else
                    binding.event5.setText(it?.toString() ?: "0")
            }
            Database.getLogForEvent("X-TRAVAGANCE") {
                if (it?.toString() ?: "0" == "0")
                    binding.event6.hint = "0"
                else
                    binding.event6.setText(it?.toString() ?: "0")
            }
        }
        job.join()
        withContext(Main) {
            binding.textViewCoins.visibility = VISIBLE
            investCoins()
        }
    }
}