package com.example.kitties

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.koushikdutta.ion.Ion
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val REQ_CODE = 124

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        downloadProcessData()
    }

    fun downloadProcessData(){

        Ion.with(this)
            .load("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/6/1")
            .asString()
            .setCallback { _, result ->
                processData(result)
            }


    }



    //  example of JSON object:
    //
//    {
//        "error": false,
//        "results": [
//        {
//            "_id": "5ccdbc219d212239df927a93",
//            "createdAt": "2019-05-04T16:21:53.523Z",
//            "desc": "2019-05-05",
//            "publishedAt": "2019-05-04T16:21:59.733Z",
//            "source": "web",
//            "type": "福利",
//            "url": "http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg",
//            "used": true,
//            "who": "lijinshanmx"
//        }
//        ]
//    }
    fun processData(result: String){
        val json = JSONObject(result)
        val resultsArray = json.getJSONArray("results")

        grid.removeAllViews()
        for(i in 0 until resultsArray.length()){

            val itemArray = resultsArray.getJSONObject(i)
            val url:String = itemArray.getString("url")
            val img = ImageButton(this)
            img.layoutParams = ViewGroup.LayoutParams(
                600,
                700
            )
            img.setPadding(5, 10, 5, 10) // todo: useless if we use ImageButton instead of Imageview

            grid.addView(img)
            Picasso.get()
                .load(url)
                .into(img)

            img.setOnClickListener {
                val myIntent = Intent(this, Detail::class.java)
                myIntent.putExtra("url", url)
                startActivityForResult(myIntent, REQ_CODE) // jump to the story page
            }
        }
    }

//    fun clicker){
//        // todo: 1.change the imageview to imagebutton, 2.get the ID from it, 3. send the url to the activity
////        myIntent.putExtra("story", Story_title)
////        startActivityForResult(myIntent, REQ_CODE) // jump to the story page
//    }
}
