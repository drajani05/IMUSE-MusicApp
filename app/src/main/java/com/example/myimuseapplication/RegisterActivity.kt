package com.example.myimuseapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.myimuseapplication.databinding.ActivityRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    companion object {
        lateinit var auth: FirebaseAuth
    }
//crete member variables of FDB
    private var mFirebaseDatabaseInstances: FirebaseDatabase? = null
    private var mFirebaseDatabase:DatabaseReference?=null

    //Creating member variable for userId and emailAddress
    private var userId:String?=null
    private var emailAddress:String?=null
    private var Pass:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setTheme(R.style.Theme_IMUSEApplication)

        //Get instance of FirebaseDatabase
        mFirebaseDatabaseInstances= FirebaseDatabase.getInstance()

        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


       //Configure Google sign in
        val gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .requestProfile()
            .build()

        val googleSignInClient= GoogleSignIn.getClient(this,gso)
        binding.loginTV.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.signupBtn.setOnClickListener {
            val email = binding.emailRegister.text.toString()
            val userpassword = binding.passwordRegister.text.toString()
            val user_name= binding.username.text.toString()

            if (user_name.isEmpty() || email.isNotEmpty() || userpassword.isNotEmpty()){
                Toast.makeText(this@RegisterActivity,"Enter Username!",Toast.LENGTH_SHORT).show()
            }
            if (user_name.isNotEmpty() && email.isEmpty() && userpassword.isNotEmpty()){
                Toast.makeText(this@RegisterActivity,"Enter Email Address!",Toast.LENGTH_SHORT).show()
            }

            if (user_name.isNotEmpty() && email.isNotEmpty() && userpassword.isEmpty()){
                Toast.makeText(this@RegisterActivity,"Enter Password!",Toast.LENGTH_SHORT).show()
            }

            if (user_name.isEmpty() && email.isEmpty() && userpassword.isEmpty()){
                Toast.makeText(this@RegisterActivity,"Enter Details!",Toast.LENGTH_LONG).show()
            }

            if (user_name.isEmpty() && email.isEmpty() && userpassword.isNotEmpty()){
                Toast.makeText(this@RegisterActivity,"Enter Username and Email Address!",Toast.LENGTH_SHORT).show()
            }
            if (user_name.isEmpty() && email.isNotEmpty() && userpassword.isEmpty()){
                Toast.makeText(this@RegisterActivity,"Enter Username and Password!",Toast.LENGTH_SHORT).show()
            }
            if (user_name.isNotEmpty() && email.isEmpty() && userpassword.isEmpty()){
                Toast.makeText(this@RegisterActivity,"Enter Email Address and Password!",Toast.LENGTH_SHORT).show()
            }




            if (user_name.isNotEmpty() && email.isNotEmpty() && userpassword.isNotEmpty()) {

                val passHash =
                    BCrypt.withDefaults().hashToString(12, userpassword.toCharArray())
                Log.d("register", "$passHash")

                RegisterActivity.auth.createUserWithEmailAndPassword(email, userpassword)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {


                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                            Toast.makeText(this, "SignUp Successful !!", Toast.LENGTH_SHORT).show()

                            //Getting reference to ?users? node
                            mFirebaseDatabase = mFirebaseDatabaseInstances!!.getReference("users")

                            //Getting current user from FirebaseAuth
                            val user = FirebaseAuth.getInstance().currentUser

                            //add username, email to database
                            userId = user!!.uid
                            emailAddress = user.email

                            //Creating a new user
                            val myUser = User(user_name, emailAddress, userpassword!!)

                            //Writing data into database using setValue() method
                            mFirebaseDatabase!!.child(userId!!).setValue(myUser)


                        }
                    }.addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
            }


        }

        binding.googleBtn.setOnClickListener {

            googleSignInClient.signOut()
        startActivityForResult(googleSignInClient.signInIntent,11)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //

        if (requestCode==11 && resultCode== RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            //
            val account = task.getResult(ApiException::class.java)!!
            FirebaseAuthWithGoogle(account.idToken!!)
        }
    }

    private fun  FirebaseAuthWithGoogle(idToken: String){

        val credential= GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task->
            if (task.isSuccessful){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                Toast.makeText(this, "SignIn Successful !!", Toast.LENGTH_SHORT).show()




            }
        }.addOnFailureListener {
            Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }



}