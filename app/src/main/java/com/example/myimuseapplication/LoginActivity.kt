package com.example.myimuseapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myimuseapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_IMUSEApplication)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        RegisterActivity.auth = FirebaseAuth.getInstance()


        binding.registerTV.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        binding.forgotPwdBtn.setOnClickListener {

            val intent = Intent(this,ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()

        }




        binding.loginBtn.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()

            if (email.isEmpty() && password.isNotEmpty())
            {
                Toast.makeText(this@LoginActivity,"Please Enter Email Address",Toast.LENGTH_LONG).show()
            }
            if (email.isNotEmpty() && password.isEmpty())
            {
                Toast.makeText(this@LoginActivity,"Please Enter Password",Toast.LENGTH_LONG).show()
            }

            if (email.isEmpty() && password.isEmpty())
            {
                Toast.makeText(this@LoginActivity,"Please Enter Email Address and Password",Toast.LENGTH_LONG).show()
            }

            if (email.isNotEmpty() && password.isNotEmpty())
                RegisterActivity.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                            Toast.makeText(this, "SignIn Successful !!", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
        }

    }
}




