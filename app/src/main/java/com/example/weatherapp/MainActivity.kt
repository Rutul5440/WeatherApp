package com.example.weatherapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.WindowCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// 548cd9aee7828f5f4642b9c341716422
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

// To display the activity in full screen and also make the status bar and notification bar transparent in themes.xml


        fetchWeatherData("ahmedabad")
    }

    private fun fetchWeatherData(cityName : String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)
        val response = retrofit.getWeatherData(cityName, "548cd9aee7828f5f4642b9c341716422", units = "metric")
        response.enqueue(object : Callback<WeatherApp>{
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    val temperature = responseBody.main.temp.toString()
                    val condition = responseBody.weather.firstOrNull()?.main?: "unknown"
                    val highTemp = responseBody.main.temp_max
                    val lowTemp = responseBody.main.temp_min
                    val humidity = responseBody.main.humidity
                    val windSpeed = responseBody.wind.speed
                    val sunRise = responseBody.sys.sunrise
                    val sunSet = responseBody.sys.sunset
                    val seaLevel = responseBody.main.pressure

                    // Find the TextView with ID "temp"
                    val tempTextView = findViewById<TextView>(R.id.temprature)
                    val condTextView = findViewById<TextView>(R.id.cond)
                    val highTempTextView = findViewById<TextView>(R.id.high_temp)
                    val lowTempTextView = findViewById<TextView>(R.id.low_temp)
                    val humidityTextView = findViewById<TextView>(R.id.humidity)
                    val windSpeedTextView = findViewById<TextView>(R.id.windspeed)
                    val sunRiseTextView = findViewById<TextView>(R.id.sunrisee)
                    val sunSetTextView = findViewById<TextView>(R.id.sunset)
                    val seaLevelTextView = findViewById<TextView>(R.id.sea)
                    val dayTextView = findViewById<TextView>(R.id.day)
                    val dateTextView = findViewById<TextView>(R.id.date)
                    val cityNameTextView = findViewById<TextView>(R.id.cityName)

                    // Set the variable values to the TextView
                    tempTextView.text = "$temperature°"
                    condTextView.text = condition
                    highTempTextView.text = "H:$highTemp°"
                    lowTempTextView.text = "L:$lowTemp°"
                    humidityTextView.text = "$humidity %"
                    windSpeedTextView.text = "$windSpeed m/s"
                    sunRiseTextView.text = "$sunRise"
                    sunSetTextView.text = "$sunSet"
                    seaLevelTextView.text = "$seaLevel hpa"
                    condTextView.text = condition
                    dayTextView.text = dayName(System.currentTimeMillis())
                    dateTextView.text = date()
                    cityNameTextView.text = "$cityName"





//                    Log.d( "TAG", "onResponse: $temperature")
                }
            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {

            }

        })


    }

    private fun date(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format((Date()))
    }

    fun dayName(timestamp: Long): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format((Date()))
    }
}