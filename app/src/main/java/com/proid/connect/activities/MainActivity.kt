package com.proid.connect.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.proid.connect.R
import com.proid.connect.adapters.ViewPagerMainAdapter
import com.proid.connect.services.ReceivingMessagesServices
import com.proid.connect.utiils.DatabaseHelper

class MainActivity : AppCompatActivity() {
    lateinit var mainTabLayOut : TabLayout
    lateinit var mainViewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainTabLayOut = findViewById(R.id.mainTabLayout)
        mainViewPager = findViewById(R.id.mainViewPager)

        var databaseHelper = DatabaseHelper.getDb(this)

        var logoutBtn = findViewById<Button>(R.id.logoutBtn)

        logoutBtn.setOnClickListener {
            var connectedUsers = databaseHelper.usersDao().getConnectedUsers()
            for (messages in databaseHelper.messagesDao().getAllMessages()){
                databaseHelper.messagesDao().deleteTm(messages)
            }
            for (user in connectedUsers){
                databaseHelper.usersDao().deleteCu(user)
            }
            var pref : SharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
            var spref : SharedPreferences = getSharedPreferences("timestamp", MODE_PRIVATE)
            var sEditor = spref.edit()
            sEditor.clear()
            sEditor.apply()
            var editor = pref.edit()
            editor.putBoolean("isLogin",false)
            editor.apply()
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
            finish()

        }

        val tabAdapter = ViewPagerMainAdapter(this);
        mainViewPager.adapter = tabAdapter

        startService(Intent(this@MainActivity,ReceivingMessagesServices::class.java))

        TabLayoutMediator(mainTabLayOut, mainViewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Chat"

            } else {
                tab.text = "Meet"
            }

        }.attach()
    }
}