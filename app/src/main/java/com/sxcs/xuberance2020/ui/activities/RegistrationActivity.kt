package com.sxcs.xuberance2020.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sxcs.xuberance2020.adapters.RegistrationPagerAdapter
import com.sxcs.xuberance2020.data.models.Registration
import com.sxcs.xuberance2020.databinding.ActivityRegistrationBinding
import com.sxcs.xuberance2020.firebase.Database
import com.sxcs.xuberance2020.ui.fragments.ConfirmationFragment
import com.sxcs.xuberance2020.ui.fragments.RegisterEventFragment

class RegistrationActivity : AppCompatActivity(), RegisterEventFragment.OnButtonClickListener,
    ConfirmationFragment.OnFinishRegistrationListener {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var registrationAdapter: RegistrationPagerAdapter
    private val registrationMap = HashMap<String, Registration>()

    companion object {
        fun getIntent(context: Context) = Intent(context, RegistrationActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Database.getAllEvents {
            registrationAdapter = RegistrationPagerAdapter(supportFragmentManager, it)
            binding.viewPagerRegistration.adapter = registrationAdapter
        }
    }

    override fun nextPage(key: String, value: Registration) {
        registrationMap[key] = value
        binding.viewPagerRegistration.currentItem++
    }

    override fun backPage() {
        binding.viewPagerRegistration.currentItem--
    }

    override fun finishPage(key: String, value: Registration) {
        registrationMap[key] = value
        registrationAdapter.getItem(++binding.viewPagerRegistration.currentItem) as ConfirmationFragment
    }

    override fun getCurrentPosition() = binding.viewPagerRegistration.currentItem

    override fun getChildCount() = registrationAdapter.count

    override fun getRegistrationMap() = registrationMap
}