package com.example.coopproject

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
import androidx.core.app.ActivityCompat
import com.example.coopproject.navigation.AppNavigation
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
                    Log.d("ARAF",resources.configuration.locales[0].toString())
                    AppNavigation(sharedViewModel = sharedViewModel)
                }
            }
        }
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

