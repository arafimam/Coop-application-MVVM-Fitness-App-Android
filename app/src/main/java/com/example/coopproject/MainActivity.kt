package com.example.coopproject

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.example.coopproject.navigation.AppNavigation
import com.example.coopproject.notifications.Alarm
import com.example.coopproject.screens.SharedViewModel
import com.example.coopproject.ui.theme.CoopProjectTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val sharedViewModel: SharedViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoopProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                )
                {
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                    fetchLocation()
                    val calendar: Calendar = Calendar.getInstance()

                    Log.d("Araf","Here")
                    Log.d("Araf",calendar.get(Calendar.HOUR_OF_DAY).toString())
                    Log.d("Araf",calendar.get(Calendar.MINUTE).toString())
                    setAlarm(context = LocalContext.current)
                    AppNavigation(sharedViewModel = sharedViewModel)
                }
            }
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag", "ShortAlarm")
    private fun setAlarm(context: Context){
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,17)
        calendar.set(Calendar.MINUTE,31)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, Alarm::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT)
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pendingIntent)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,10000,pendingIntent)
    }

    private fun fetchLocation(){
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)
            return
        }
        task.addOnSuccessListener {
            if (it!=null){
                val gcd = Geocoder(this.applicationContext, Locale.getDefault())
                val addresses: List<Address> = gcd.getFromLocation(it.latitude, it.longitude, 1)
                if (addresses.isNotEmpty()){
                    Log.d("msg",addresses[0].countryName)
                    sharedViewModel.countryName = addresses[0].countryName
                }
                Log.d("msg","${it.latitude} AND ${it.longitude}" )
            }
        }
    }
}

