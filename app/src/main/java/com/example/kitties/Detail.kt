package com.example.kitties

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class Detail : AppCompatActivity() {

    private var private_mode = 0
    private val pref_name = "ratingData"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val sharedPreference: SharedPreferences = this.getSharedPreferences(pref_name, private_mode)

        val url= intent.getStringExtra("url")
        Picasso.get()
        .load(url)
        .into(beauty)

        if (!sharedPreference.getFloat(url, 0f).equals(0.0)) // since url is unique, which can be used as id
            ratingBar.rating = sharedPreference.getFloat(url, 0f) // who knows wtf is defValue?

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            val editor: SharedPreferences.Editor = sharedPreference.edit()
            editor.putFloat(url, rating)
            editor.commit()

        }
    }
}
