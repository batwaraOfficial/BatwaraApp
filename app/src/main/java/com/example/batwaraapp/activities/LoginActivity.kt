package com.example.batwaraapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.batwaraapp.R
import com.example.batwaraapp.databinding.ActivityLoginBinding
import com.example.batwaraapp.datamodels.UserModel
import com.example.batwaraapp.utils.EmailForUse
import com.example.batwaraapp.utils.InterfaceUtils.uniClick
import com.example.batwaraapp.utils.SharedPrefsKeys
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private val createHomeScreen = registerForActivityResult(HomeScreen) {}
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        supportActionBar?.hide()

        val sharedPreferences: SharedPreferences? = this.getSharedPreferences(SharedPrefsKeys.MAIN_PREF_KEY, MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val authPrefKey = sharedPreferences?.getString(SharedPrefsKeys.AUTH_PREF_KEY, "")

        if(!authPrefKey.isNullOrEmpty()) {
            createHomeScreen.launch(null)
        }

        binding.saveButton.uniClick(true) {
            binding.emailInput.text.let { email ->
                if(!email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    try {
                        db.collection("user").document(EmailForUse.getFirstPartEmail(email.toString())).get().addOnSuccessListener {
                            if(!it.exists()) {
                                db.collection("user").document(EmailForUse.getFirstPartEmail(email.toString())).set(UserModel()).addOnSuccessListener {
                                    editor?.putString(SharedPrefsKeys.AUTH_PREF_KEY, email.toString())
                                    editor?.apply()
                                    Toast.makeText(this, "Logged In.", Toast.LENGTH_SHORT).show()
                                    createHomeScreen.launch(null)
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Some Error Occurred, Try Again.", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                editor?.putString(SharedPrefsKeys.AUTH_PREF_KEY, email.toString())
                                editor?.apply()
                                createHomeScreen.launch(null)
                            }
                        }.addOnFailureListener {
                            Toast.makeText(this, "${it}", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: Exception) {
                        Toast.makeText(this, "Exception Caught.", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this, "Either empty or wrong format for Email!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object : ActivityResultContract<String?, Boolean>() {
        override fun createIntent(context: Context, params: String?): Intent {
            val intent = Intent(context, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            return intent
        }
        override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
            return resultCode == RESULT_OK
        }
    }
}