package com.sxcs.xuberance2020.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.sxcs.xuberance2020.R
import com.sxcs.xuberance2020.data.models.Participant
import com.sxcs.xuberance2020.databinding.LayoutEntryBinding
import kotlin.math.floor
import kotlin.math.log10

class ParticipantEntry @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var binding: LayoutEntryBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_entry, this, true)
        binding = LayoutEntryBinding.bind(view)
    }

    var participantId: Int
        get() = binding.participantId.text.last() - '0'
        set(value) {
            binding.participantId.text =
                String.format(context.getString(R.string.participant_id), value)
        }

    private var participantName: String
        get() = binding.participantName.text.toString()
        set(value) {
            binding.participantName.setText(value)
        }

    private var participantNumber: Long?
        get() {
            return try {
                binding.participantNumber.text.toString().toLong()
            } catch (e: Exception) {
                null
            }
        }
        set(value) {
            binding.participantNumber.setText(value.toString())
        }

    var participant: Participant?
        get() = when {
            participantName.isBlank() -> {
                binding.participantName.apply {
                    error = context.getString(R.string.cannot_be_empty)
                    requestFocus()
                }
                null
            }
            participantNumber == null -> {
                binding.participantNumber.apply {
                    error = context.getString(R.string.cannot_be_empty)
                    requestFocus()
                }
                null
            }
            floor(log10(participantNumber!!.toDouble())) + 1 != 10.0 -> {
                binding.participantNumber.apply {
                    error = context.getString(R.string.invalid_phone)
                    requestFocus()
                }
                null
            }
            else -> Participant(participantName, participantNumber!!)
        }
        set(value) {
            participantName = value!!.name
            participantNumber = value.phoneNumber
        }
}