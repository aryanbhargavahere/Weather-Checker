package com.example.weatherchecker

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigationevent.NavigationEventDispatcher
import com.example.weatherchecker.model.WeatherResponse
import com.example.weatherchecker.utild.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    private lateinit var locationCallback: LocationCallback
    private val REQUEST_LOCATON_CODE = 123321
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (!islocationon()) {

            Toast.makeText(this, "The Location Is Not On", Toast.LENGTH_SHORT).show()

            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        } else {
            requestpermission()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == REQUEST_LOCATON_CODE && grantResults.size > 0) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            requestlocationdata()
        } else {
            Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestlocationdata() { // to get the location of the user
        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation ?: return
                Toast.makeText(
                    this@MainActivity,
                    "Latitude: ${location.latitude}\nLongitude: ${location.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
                getlocationweatherdetails(
                    locationResult.lastLocation?.latitude!!,
                    locationResult.lastLocation?.longitude!!
                )
                // to stop update of current location
                mFusedLocationClient.removeLocationUpdates(this)
            }
        }
        mFusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun islocationon(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestpermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            showRequestDialog() //if this permission wad denied it activates shoreequest function and the function tells why we need
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            requestpermission()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_LOCATON_CODE
            )
        }
    }

    private fun showRequestDialog() {
        AlertDialog.Builder(this)
            .setPositiveButton("GO TO SETTING") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CLOSE") { dialog, _ ->
                dialog.cancel()
            }.setTitle("Location Permission Needed")
            .setMessage("This is needed for accesing the location")
            .show()
    }

    private fun getlocationweatherdetails(Latitude: Double, Longitude: Double) {
        if (Constants.isnetworkavailable(this)) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val serviceapi = retrofit.create(WeatherServiceapi::class.java)

            val call = serviceapi.getweatherdetails(
                Latitude,
                Longitude,
                Constants.APP_ID,
                Constants.METRIC_UNIT
            )
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.isSuccessful) {
                        val weather = response.body()
                        Log.d("WEATHER", weather.toString())
                        for (i in weather?.weather?.indices!!) {
                            findViewById<TextView>(R.id.text_view_sunset).text = converttime(weather.sys.sunset.toLong())
                            findViewById<TextView>(R.id.text_view_sunrise).text = converttime(weather.sys.sunrise.toLong())
                            findViewById<TextView>(R.id.text_view_status).text = weather.weather[i].description
                            findViewById<TextView>(R.id.text_view_address).text = weather.name
                            findViewById<TextView>(R.id.text_view_temp_max).text = weather.main.temp_max.toString()
                            findViewById<TextView>(R.id.text_view_temp_min).text = weather.main.temp_min.toString()
                            findViewById<TextView>(R.id.text_view_temp).text = weather.main.temp.toString()
                            findViewById<TextView>(R.id.text_view_humidity).text = weather.main.humidity.toString()
                            findViewById<TextView>(R.id.text_view_pressure).text = weather.main.pressure.toString()
                            findViewById<TextView>(R.id.text_view_wind).text = weather.wind.speed.toString()
                        }

                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Something Went Wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<WeatherResponse?>,
                    t: Throwable
                ) {

                }

            })
        } else {
            Toast.makeText(this, "No network connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun converttime(time: Long): String {
        val date= Date(time*1000)
        val timeformatted= SimpleDateFormat("HH:mm", Locale.UK)
        timeformatted.timeZone= TimeZone.getDefault()
        return timeformatted.format(date)
    }

}