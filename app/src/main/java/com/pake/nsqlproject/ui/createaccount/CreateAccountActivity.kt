package com.pake.nsqlproject.ui.createaccount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pake.nsqlproject.MainActivity
import com.pake.nsqlproject.data.*
import com.pake.nsqlproject.databinding.ActivityCreateAccountBinding
import com.pake.nsqlproject.model.ManageData

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    private val manageData : ManageData by lazy {
        ManageData(baseContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val personalList = mutableListOf<PersonalList>()
        val friendsList = mutableListOf<Friend>()
        
        personalList.add(PersonalList("1","-1","Your first list", mutableListOf<Book>()))

        binding.btnLogin.setOnClickListener {
            val alphabet: CharRange = ('0'..'z')
            val randomString: String = List(50) { alphabet.random() }.joinToString("")
            manageData.setData( AllData(
                PersonalInfo(
                    "-1",
                    binding.etUsername.text.toString(),
                    randomString
                ),
                personalList,
                friendsList
            ))
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
