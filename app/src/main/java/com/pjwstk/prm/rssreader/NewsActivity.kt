package com.pjwstk.prm.rssreader

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.pjwstk.prm.rssreader.model.RssJava
import kotlinx.android.synthetic.main.activity_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private val service: FeedRepository = RssAccess.retrofit.create(FeedRepository::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setSupportActionBar(findViewById(R.id.toolbarMain))
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            Log.d("User detail onStart:", currentUser.email)
            Log.d("User detail onStart:", currentUser.uid)
        }
        getFeed()
    }

    override fun onResume() {
        super.onResume()
        if(mAuth.currentUser == null){
            Toast.makeText(applicationContext, "Log in first!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun getFeed() {
        service.getFeeds().enqueue(object : Callback<RssJava> {
            override fun onFailure(call: Call<RssJava>, t: Throwable) {
                Log.d("Response body:", "Failed!")
                Log.d("Response body:", t.message)

                Toast.makeText(this@NewsActivity, "Couldn't get news!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<RssJava>, response: Response<RssJava>) {
                try {
                    Log.d("Response body:", response.body()?.channel?.items.toString())
                    FeedAdapter(
                        this@NewsActivity,
                        response.body()?.channel?.items.orEmpty()
                    ).let {
                        runOnUiThread{feedListView.adapter = it}
                    }
                } catch (e: Exception) {
                    e.printStackTrace()

                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemView = item.itemId

        when(itemView){
            R.id.logoutAction -> {
                Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
                Log.d("Logged out: ", mAuth.currentUser.toString())
                finish()
            }

            R.id.backToNews -> finish()
        }

        return false
    }
}
