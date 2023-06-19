package com.example.mini_sofascore

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mini_sofascore.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.userEmail.addTextChangedListener(textWatcher)
        binding.userPassword.addTextChangedListener(textWatcher)
        binding.loginButton.isEnabled = false

        binding.loginButton.setOnClickListener {
            logIn()
        }

        binding.noAccountButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        var forgottenPasswordEmail: String

        binding.forgotPasswordButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val forgotPasswordLayout = layoutInflater.inflate(R.layout.forgot_password_layout, null)
            val enterEmail = forgotPasswordLayout.findViewById<EditText>(R.id.enter_email)
            with(builder) {
                setTitle(resources.getString(R.string.enter_email))
                setPositiveButton(resources.getString(R.string.send)) { _, _ ->
                    forgottenPasswordEmail = enterEmail.text.toString()
                    auth.sendPasswordResetEmail(forgottenPasswordEmail)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    baseContext,
                                    resources.getString(R.string.email_success),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else
                                Toast.makeText(
                                    baseContext,
                                    resources.getString(R.string.email_failure),
                                    Toast.LENGTH_SHORT
                                ).show()
                        }


                }
                setNegativeButton(resources.getString(R.string.go_back)) { _, _ ->
                }
                setView(forgotPasswordLayout)
                show()
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
                binding.loginButton.isEnabled =
                    (binding.userEmailContainer.helperText == null && binding.userPasswordContainer.helperText == null)

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


    private fun logIn() {
        auth.signInWithEmailAndPassword(
            binding.userEmail.text.toString(),
            binding.userPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(
                        baseContext,
                        "User doesn't exist!",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }


    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }


    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }
}