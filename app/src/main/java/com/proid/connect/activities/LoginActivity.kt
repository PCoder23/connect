package com.proid.connect.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.proid.connect.R
import com.proid.connect.models.UsersData
import com.proid.connect.utiils.DatabaseHelper

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var databaseHelper = DatabaseHelper.getDb(this)
        val db = Firebase.firestore


        var usrName = findViewById<EditText>(R.id.usrName)
        var userId = findViewById<EditText>(R.id.userId)
        var loginBtn = findViewById<Button>(R.id.loginBtn)

        var pref : SharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        var editor = pref.edit()


        loginBtn.setOnClickListener{
            var name : String= usrName.text.toString()
            var userId : String = userId.text.toString()


            db.collection("users").document(userId)
                .set(UsersData(userId,userId,name,R.drawable.baseline_person_24))
                .addOnSuccessListener {
                    editor.putBoolean("isLogin",true)
                    editor.putString("userName",name)
                    editor.putString("userId",userId)
                    editor.apply()

                    db.collection("users").document(userId).collection("connectedUsers")
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                var doc = document.data
                                databaseHelper.usersDao().addCu(UsersData(doc.get("id").toString(),doc.get("userId").toString(),doc.get("userName").toString(),doc.get("userImg").toString().toInt()))
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w("TAG", "Error getting documents.", exception)
                        }


                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"ID already present",Toast.LENGTH_LONG)
                }
        }

    }
}