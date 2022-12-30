package com.example.weatherapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import kotlin.math.ceil

//https://api.openweathermap.org/data/2.5/weather?q=delhi&appid=d782c3641dcc9cbcb8528dfb0245b3e4

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lat = intent.getStringExtra("Lat")
        val long = intent.getStringExtra("Long")
//        Toast.makeText(this, "Lat: " + lat + " Long: " + long, Toast.LENGTH_SHORT).show()
        getJsonData(lat , long)
    }

    private fun getJsonData(lat: String?, long: String?)
    {
        val queue = Volley.newRequestQueue(this)
        val APIKey = "d782c3641dcc9cbcb8528dfb0245b3e4"
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${APIKey}"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
//                Toast.makeText(this, response.toString() , Toast.LENGTH_LONG).show()
                setValues(response)
            },
            Response.ErrorListener {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonRequest)
    }
    private fun setValues(response: JSONObject?) {
        city.text = response?.getString("name")
//        type.text = response?.getJSONArray("weather")?.getJSONObject(1)?.getString("main")

        var temper = response?.getJSONObject("main")?.getString("temp")
        temper = ((((temper)?.toFloat()?.minus(273.15)))?.toInt()).toString()
        curr_temp.text = temper

        var minTemp = response?.getJSONObject("main")?.getString("temp_min")
        minTemp = ((((minTemp)?.toFloat()?.minus(273.15)))?.toInt()).toString()
        min_temp.text = minTemp + "°"

        var maxTemp = response?.getJSONObject("main")?.getString("temp_max")
        maxTemp = (((maxTemp)?.toFloat()?.minus(273.15)?.let { ceil(it) })?.toInt()).toString()
        max_temp.text = maxTemp + "°"

        pressure.text = "Pressure: " + response?.getJSONObject("main")?.getString("pressure") + "Pa"
        humidity.text = "Humidity: " + response?.getJSONObject("main")?.getString("humidity") + "%"
        wind_speed.text = "Wind Speed: " + response?.getJSONObject("wind")?.getString("speed") + "kmph"
        wind_degree.text = "Wind Degree: " + response?.getJSONObject("wind")?.getString("deg") + "°"
    }
}