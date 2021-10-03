package com.learning.xmlparsing

import android.os.AsyncTask
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    var questions = mutableListOf<Question>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<ListView>(R.id.listView)
        FetchQuestions().execute()
    }

    private inner class FetchQuestions : AsyncTask<Void, Void, MutableList<Question>>() {
        val parser = XMLParser()
        override fun doInBackground(vararg params: Void?): MutableList<Question> {
            val url = URL("https://stackoverflow.com/feeds")
            val urlConnection = url.openConnection() as HttpURLConnection
            questions =
                urlConnection.getInputStream()?.let { parser.parse(it) } as MutableList<Question>
            return questions
        }

        override fun onPostExecute(result: MutableList<Question>?) {
            super.onPostExecute(result)
            val adapter =
                ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, questions)
            listView.adapter = adapter
        }

    }

}