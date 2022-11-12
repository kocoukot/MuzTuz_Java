package com.muztus.creators_feature

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.muztus.core.ext.viewBinding
import com.muztus.creators_feature.databinding.ActivitySozdateliBinding

class CreatorsFragment : Fragment(R.layout.activity_sozdateli) {
    private val binding by viewBinding(ActivitySozdateliBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val uri = Uri.parse("https://yadi.sk/i/Gd7vNnOuFIgfQw")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}