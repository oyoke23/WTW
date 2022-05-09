package com.pake.nsqlproject.ui.createaccount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pake.nsqlproject.MainActivity
import com.pake.nsqlproject.data.*
import com.pake.nsqlproject.databinding.ActivityCreateAccountBinding
import com.pake.nsqlproject.model.ManageData
import java.math.BigInteger
import java.security.MessageDigest

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
        binding.btnLogin.setOnClickListener {
            val md5String = Md5(binding.etUsername.text.toString() + binding.etEmail.text.toString())

            val alphabet: CharRange = ('0'..'z')
            var randomString: String = List(30) { alphabet.random() }.joinToString("")
            randomString = md5String + randomString
            manageData.setData( AllData(
                PersonalInfo(
                    "-1",
                    binding.etUsername.text.toString(),
                    binding.etEmail.text.toString(),
                    randomString
                ),
                personalList,
                friendsList
            ))
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun Md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}
