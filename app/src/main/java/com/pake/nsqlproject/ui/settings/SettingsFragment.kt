package com.pake.nsqlproject.ui.settings


import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.preference.Preference

import androidx.preference.PreferenceFragmentCompat
import com.pake.nsqlproject.R
import com.pake.nsqlproject.SharedViewModel
import com.pake.nsqlproject.data.AllData
import com.pake.nsqlproject.data.Friend
import com.pake.nsqlproject.data.PersonalList
import com.pake.nsqlproject.model.ManageData
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File
import java.io.InputStreamReader

class SettingsFragment : PreferenceFragmentCompat() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val manageData : ManageData by lazy {
        ManageData(requireContext())
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Settings"
        main()
    }

    var fromOption: Int? = null;

    private fun main() {
        findPreference<Preference>("import_data")?.setOnPreferenceClickListener {
            Log.d("SettingsFragment", "import")
            AlertDialog.Builder(requireContext())
                .setTitle("This lists are from...?")
                .setItems(R.array.import_options) { _, which ->
                    when (which) {
                        0 -> {
                            fromOption = 0
                            getFileFromUser()
                        }
                        1 -> {
                            fromOption = 1
                            getFileFromUser()
                        }
                    }
                }
                .show()


            true
        }

        findPreference<Preference>("export_data")?.setOnPreferenceClickListener {
            // Get all data
            val allData = sharedViewModel.allData.value!!

            // Remove friends from data
            allData.friendList.clear()

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
        findPreference<Preference>("about_us")?.setOnPreferenceClickListener {
            //abre el fragment de about us
            findNavController(this).navigate(R.id.aboutUsFragment)
            true
        }
    }

    private fun getFileFromUser() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
        }
        resultLauncher.launch(intent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val stringBuilder = StringBuilder()
            val inputStream = requireContext().contentResolver.openInputStream(data?.data!!)
            val reader = InputStreamReader(inputStream)
            reader.forEachLine {
                stringBuilder.append(it)
            }

            try {
                if (stringBuilder.isNotEmpty()) {
                    val tempData = sharedViewModel.allData.value!!
                    if (fromOption != null) {
                        when (fromOption) {
                            0 -> {
                                // Parse data from file
                                val jsonObject = Json.decodeFromString(
                                    AllData.serializer(),
                                    stringBuilder.toString()
                                )

                                // Add the lists
                                jsonObject.personalList.forEach { list ->
                                    // get the count of the list with the same name or name(number)
                                    val count = tempData.personalList.count { it1 ->
                                        it1.name == list.name ||
                                                it1.name == list.name + "(" + tempData.personalList.count { it.name == list.name } + ")"
                                    }

                                    if (tempData.personalList.isEmpty()) {
                                        tempData.personalList.add(
                                            PersonalList(
                                                "1",
                                                "-1",
                                                if (count == 0) list.name else list.name + ("($count)"),
                                                list.books
                                            )
                                        )
                                    } else {
                                        tempData.personalList.add(
                                            PersonalList(
                                                tempData.personalList.last().id.toInt().plus(1)
                                                    .toString(),
                                                "-1",
                                                if (count == 0) list.name else list.name + ("($count)"),
                                                list.books
                                            )
                                        )
                                    }

                                }
                                //set new data
                                manageData.setData(tempData)
                            }
                            1 -> {
                                // Parse data from file
                                val jsonObject = Json.decodeFromString(
                                    AllData.serializer(),
                                    stringBuilder.toString()
                                )

                                var userId: String? = null;
                                // Check if the user is in the friend list
                                if (tempData.friendList.isEmpty()) {
                                    // Add the user to the friend list
                                    tempData.friendList.add(
                                        Friend(
                                            "1",
                                            jsonObject.personalInfo.name,
                                            jsonObject.personalInfo.hash
                                        )
                                    )
                                    userId = "1"
                                } else {
                                    // if the friend is already in the list, we don't add it
                                    if (tempData.friendList.none { it.hash == jsonObject.personalInfo.hash }) {
                                        // Add the user to the friend list
                                        tempData.friendList.add(
                                            Friend(
                                                tempData.friendList.last().id.toInt().plus(1)
                                                    .toString(),
                                                jsonObject.personalInfo.name,
                                                jsonObject.personalInfo.hash
                                            )
                                        )

                                        // Get the user id
                                        userId = tempData.friendList.last().id
                                    } else {
                                        // Only get the user id from the friend list
                                        userId =
                                            tempData.friendList.find { it.hash == jsonObject.personalInfo.hash }?.id
                                    }
                                }


                                // Add the lists with the user id
                                jsonObject.personalList.forEach { list ->
                                    val count = tempData.personalList.count { it1 ->
                                        it1.name == list.name ||
                                                it1.name == list.name + "(" + tempData.personalList.count { it.name == list.name } + ")"
                                    }

                                    if (tempData.personalList.isEmpty()) {
                                        tempData.personalList.add(
                                            PersonalList(
                                                "1",
                                                userId!!,
                                                if (count == 0) list.name else list.name + ("($count)"),
                                                list.books
                                            )
                                        )
                                    } else {
                                        tempData.personalList.add(
                                            PersonalList(
                                                tempData.personalList.last().id.toInt().plus(1)
                                                    .toString(),
                                                userId!!,
                                                if (count == 0) list.name else list.name + ("($count)"),
                                                list.books
                                            )
                                        )
                                    }
                                }

                                manageData.setData(tempData)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Oops, something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}