package com.example.myapplication

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode

import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

var countryList = ArrayList<CountryStats>()
var globalStats: GlobalStats? = null

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val api = "https://api.covid19api.com/summary"
        AsyncTaskHandleJson().execute(api)
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val resultString = AsyncTaskHandleJson().doInBackground("")
        countryList = AsyncTaskHandleJson().handleJson(resultString)

        loadFragment(VideoFragment())

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navinfo -> {
                    loadFragment(VideoFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navstats -> {
                    loadFragment(StatsFragment())
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false
        }

    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}


class CountryStats(var Country: String, var NewConfirmed: String, var TotalConfirmed: String, var NewDeaths: String, var TotalDeaths: String, var NewRecovered: String, var TotalRecovered: String, var Date: String) {
    var expanded = false
}

class GlobalStats(var NewConfirmed: String, var TotalConfirmed: String, var NewDeaths: String, var TotalDeaths: String, var NewRecovered: String, var TotalRecovered: String) {

}

class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
    override public fun doInBackground(vararg params: String?): String {
        //To change body of created functions use File | Settings | File Templates.
        var text: String

        val connection =
            URL("https://api.covid19api.com/summary").openConnection() as HttpURLConnection
        try {
            connection.connect()
            text =
                URL("https://api.covid19api.com/summary").readText()


        } finally {
            connection.disconnect()
        }
        return text
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        handleJson(result)
    }

    public fun handleJson(jsonString: String?): ArrayList<CountryStats> {
        var countryList = ArrayList<CountryStats>()

        var APIjsonObject: JSONObject = JSONObject(jsonString!!)
        var countryArray: JSONArray = APIjsonObject.getJSONArray("Countries")

        var globalObj: JSONObject = APIjsonObject.getJSONObject("Global")

        globalStats = GlobalStats(globalObj.getString("NewConfirmed"), globalObj.getString("TotalConfirmed"), globalObj.getString("NewDeaths"), globalObj.getString("TotalDeaths"), globalObj.getString("NewConfirmed"), globalObj.getString("TotalRecovered"))

        for (i in (0..countryArray.length() - 1)) {
            var COVIDobject: JSONObject = countryArray.getJSONObject(i)
            var Covid:CountryStats = CountryStats(
                COVIDobject.getString("Country"),
                COVIDobject.getString("NewConfirmed"),
                COVIDobject.getString("TotalConfirmed"),
                COVIDobject.getString("NewDeaths"),
                COVIDobject.getString("TotalDeaths"),
                COVIDobject.getString("NewRecovered"),
                COVIDobject.getString("TotalRecovered"),
                COVIDobject.getString("Date")
                )
            countryList.add(Covid)
        }


        return countryList
    }
}

