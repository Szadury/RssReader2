package com.pjwstk.prm.rssreader

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pjwstk.prm.rssreader.model.FeedJava
import com.squareup.picasso.Picasso


class FeedAdapter(context: Context, items: List<FeedJava>): ArrayAdapter<FeedJava>(context, 0, items)
{
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val feed: FeedJava? = getItem(position)
        mAuth = FirebaseAuth.getInstance()
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.feed_layout, parent, false)

        val titleView = rowView.findViewById<TextView>(R.id.feedTitle)
        val descriptionView = rowView.findViewById<TextView>(R.id.feedDescription)
        val imageView = rowView.findViewById<ImageView>(R.id.imageView)
        if (feed != null) {
            checkSeenNews(mAuth.currentUser, feed, rowView)
            descriptionView.text = feed.description
            titleView.text = feed.title
            Picasso.get().load(feed.enclosureUrl).into(imageView)
            rowView.setOnClickListener{
                addSeenNews(mAuth.currentUser, feed)
                openWebPage(feed.link)
            }
        }
        return rowView
    }

    private fun checkSeenNews(
        user: FirebaseUser?,
        feed: FeedJava,
        rowView: View
    ) {
        database = Firebase.database.reference
        val link = feed.link.replace(".", "|").replace(":", "||").replace("/", "|||")
        val ref = database.child("users").child(user?.uid.toString()).child("seenNews")
        val feedListener = object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(link)) {
                    rowView.setBackgroundColor(Color.GRAY)
                }
            }
        }
        ref.addListenerForSingleValueEvent(feedListener)
    }

    private fun openWebPage(url: String){
        val intent = Intent(context, FeedViewActivity::class.java)
        intent.putExtra("feedUrl", url)
        if(intent.resolveActivity(context.packageManager) != null){
            startActivity(context, intent, Bundle.EMPTY)
        }
    }

    private fun addSeenNews(user: FirebaseUser?, feed: FeedJava) {
        val link = feed.link.replace(".", "|").replace(":", "||").replace("/", "|||")
        database = Firebase.database.reference
        val ref = database.child("users").child(user?.uid.toString()).child("seenNews").child(link)
        ref.setValue(feed)
    }

}
