package com.pake.nsqlproject.ui.settings/* override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }*/


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.preference.Preference

import androidx.preference.PreferenceFragmentCompat
import com.pake.nsqlproject.R
import com.pake.nsqlproject.data.AllData
import com.pake.nsqlproject.model.ManageData
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

class SettingsFragment : PreferenceFragmentCompat() {

    private val manageData : ManageData by lazy {
        ManageData(requireContext())
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        main()
    }

    private fun main() {
        findPreference<Preference>("import_data")?.setOnPreferenceClickListener {
            Toast.makeText(context, "import", Toast.LENGTH_SHORT).show()
            Log.d("SettingsFragment", "import")
            true
        }

        findPreference<Preference>("export_data")?.setOnPreferenceClickListener {
            Toast.makeText(context, "export", Toast.LENGTH_SHORT).show()
            Log.d("SettingsFragment", "export")

            val allData = manageData.getData()!!

            // Remove friends from data
            allData.friendList.clear()
            // Remove email from personalInfo
            allData.personalInfo.email = ""

            // Encode data
            val json = Json.encodeToString(allData)

            // Create temp file
            val tempFile = File.createTempFile("ListsAndBooks", ".json", context?.cacheDir)
            tempFile.writeText(json)

            // Get URI from the file
            val uri = FileProvider.getUriForFile(requireContext(), "com.pake.nsqlproject.fileprovider", tempFile)

            // Share the file
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(shareIntent, "Share file to..."))

            true
        }

    }
}