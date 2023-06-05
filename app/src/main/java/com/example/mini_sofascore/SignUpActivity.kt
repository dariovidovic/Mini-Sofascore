package com.example.mini_sofascore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mini_sofascore.data.User
import com.example.mini_sofascore.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.userEmail.addTextChangedListener(textWatcher)
        binding.userPassword.addTextChangedListener(textWatcher)
        binding.userConfirmPassword.addTextChangedListener(textWatcher)

        binding.registerButton.isEnabled = false

        database =
            FirebaseDatabase.getInstance("https://mini-sofascore-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")
        binding.registerButton.setOnClickListener {
            createAccount()
        }


    }


    private fun createAccount() {
        auth.createUserWithEmailAndPassword(
            binding.userEmail.text.toString(),
            binding.userPassword.text.toString()
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = User(
                    binding.userEmail.text.toString(),
                    binding.userPassword.text.toString()
                )
                database.child(auth.currentUser?.uid.toString()).setValue(user)
                Toast.makeText(
                    baseContext,
                    "User created!",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, LoginActivity::class.java))
                auth.signOut()
                finish()
            } else {
                Toast.makeText(
                    baseContext,
                    "User already exists!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.run {
                binding.userEmailContainer.helperText = validateEmail()
                binding.userPasswordContainer.helperText = validatePassword()
                binding.userConfirmPasswordContainer.helperText = validateConfirmPassword()
                binding.registerButton.isEnabled =
                    (binding.userEmailContainer.helperText == null && binding.userPasswordContainer.helperText == null &&
                            binding.userConfirmPasswordContainer.helperText == null)

            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }

    private fun validateEmail(): String? {
        val userEmailText = binding.userEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmailText).matches()) {
            return resources.getString(R.string.invalid_email)
        }
        return null
    }

    private fun validatePassword(): String? {
        val userPassword = binding.userPassword.text.toString()
        if (userPassword.length < 8) {
            return resources.getString(R.string.invalid_password)
        }
        return null
    }

    private fun validateConfirmPassword(): String? {
        val userConfirmPassword = binding.userConfirmPassword.text.toString()
        if (userConfirmPassword != binding.userPassword.text.toString()) {
            return resources.getString(R.string.password_no_match)
        }
        return null
    }


    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}