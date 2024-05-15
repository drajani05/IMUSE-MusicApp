package com.example.myimuseapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myimuseapplication.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding =ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            val email: String = binding.emailForgot.text.toString().trim{ it <= ' '}

            if (email.isEmpty()){

                Toast.makeText(this@ForgotPasswordActivity,
                "Please Enter Email address",
                    Toast.LENGTH_SHORT
                    ).show()
            }
            else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful){

                            Toast.makeText(this@ForgotPasswordActivity,
                            "Email is Sent Successfully!!",
                                Toast.LENGTH_LONG
                                ).show()
                            startActivity(Intent(this@ForgotPasswordActivity,LoginActivity::class.java))
                            finish()

                        }
                        else{

                            Toast.makeText(
                                this@ForgotPasswordActivity,task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
    startActivity(Intent(this@ForgotPasswordActivity,LoginActivity::class.java))
        finish()
    }
}
