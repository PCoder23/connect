package com.proid.connect.activities

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.proid.connect.R
import com.proid.connect.adapters.SearchedUserRecyclerAdapter
import com.proid.connect.models.UsersData

class SearchUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_user)

        val db = Firebase.firestore

        var srchText = findViewById<EditText>(R.id.searchBar)
        var sBtn = findViewById<Button>(R.id.sBtn)
        var users : ArrayList<UsersData> =ArrayList()

        var pref = getSharedPreferences("login", MODE_PRIVATE)


        db.collection("users")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    var doc = document.data
                    users.add(UsersData(document.id,doc.get("userId").toString(),doc.get("userName").toString(),doc.get("userImg").toString().toInt()))
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Tag", "Error getting documents.", exception)
            }
        var recyView = findViewById<RecyclerView>(R.id.usersRecyclerView)
        recyView.layoutManager = LinearLayoutManager(this@SearchUserActivity)

        sBtn.setOnClickListener{
            var searchedUser : ArrayList<UsersData> = ArrayList()
            var txt = srchText.text
            for (user in users){
                if(user.userId.contains(txt) || user.userName.contains(txt) && user.userId!=pref.getString("userId","name").toString()){
                    searchedUser.add(user)
                }
            }
            var adpater = SearchedUserRecyclerAdapter(this,searchedUser)
            recyView.adapter= adpater
            adpater.notifyDataSetChanged()



        }



    }
}