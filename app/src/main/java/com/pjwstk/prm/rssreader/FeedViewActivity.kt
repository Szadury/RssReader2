package com.pjwstk.prm.rssreader

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class FeedViewActivity: AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_page_news)
        mAuth = FirebaseAuth.getInstance()
        val webView: WebView = findViewById(R.id.feedWebView)
        setSupportActionBar(findViewById(R.id.toolbarNews))
        val url: String? =  intent.getStringExtra("feedUrl")
        Log.d("Given url in feed ", url)
        if(url != null) {
            webView.loadUrl(url)
        }
        else{
            finish()
            Toast.makeText(this, "Cant find url", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news_feed_menu, menu)
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