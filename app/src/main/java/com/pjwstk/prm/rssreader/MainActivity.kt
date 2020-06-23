package com.pjwstk.prm.rssreader

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        val thisContext = this
        val emailText = findViewById<EditText>(R.id.emailEditText)
        val passwordText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<Button>(R.id.registerButton)

        loginButton.setOnClickListener{
            mAuth.signInWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                        Log.d("Login status", "shouldStartNews")
                        Log.d("Login status", "signInWithEmail:success")
                        val user = mAuth.currentUser
                        startNewsActivity()

                    } else { // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this@MainActivity, "Authentication failed." + task.exception,
                            Toast.LENGTH_LONG
                        ).show()

                    }
                }
        }

        registerButton.setOnClickListener{
            val database = FirebaseDatabase.getInstance()
            mAuth.createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                        Log.d("Register status", "signUpWithEmail:success")
                        val user = mAuth.currentUser
                        addUserToDatabase(user)
                        startNewsActivity()
                        Log.d("Register status", "should start news intent")
                    } else {
                        Toast.makeText(
                            this@MainActivity, "Authentication failed." + task.exception,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()
        val user = mAuth.currentUser
        if(user != null){
            startNewsActivity()
        }
    }

    private fun addUserToDatabase(user: FirebaseUser?) {
        database = Firebase.database.reference
        val ref = database.child("users").child(user?.uid.toString())
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            Log.d("User info: ", currentUser.toString())
            Log.d("User info: ", currentUser.providerId)
            Log.d("User info: ", currentUser.email)
            Log.d("User info: ", currentUser.uid)
        }
    }

    private fun startNewsActivity(){
        val intent = Intent(this, NewsActivity::class.java)
        startActivity(intent)
    }
}

