package com.coursera.littlelemon

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coursera.littlelemon.navigation.Home
import com.coursera.littlelemon.navigation.OnBoarding
import com.coursera.littlelemon.navigation.Profile
import com.coursera.littlelemon.screens.HomeScreen
import com.coursera.littlelemon.screens.OnBoarding
import com.coursera.littlelemon.screens.ProfileScreen
import com.coursera.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {
    private val sharedPreferences by lazy {
        getSharedPreferences("USER_PREFERENCES", MODE_PRIVATE)
    }
    private val firstNameLiveData = MutableLiveData<String>()
    private val lastNameLiveData = MutableLiveData<String>()
    private val emailLiveData = MutableLiveData<String>()

    private val sharedPreferenceChangeListener = OnSharedPreferenceChangeListener { sharedPreferences, key ->
        when(key) {
            "first_name" -> firstNameLiveData.value = sharedPreferences.getString(key, "")
            "last_name" -> lastNameLiveData.value = sharedPreferences.getString(key, "")
            "email" -> emailLiveData.value = sharedPreferences.getString(key, "")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstNameLiveData.value = sharedPreferences.getString("first_name", "")
        lastNameLiveData.value = sharedPreferences.getString("last_name", "")
        emailLiveData.value = sharedPreferences.getString("email", "")
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = if (sharedPreferences.all.isEmpty()) OnBoarding.route else Home.route) {
                    composable(OnBoarding.route) {
                        OnBoarding(navController = navController)
                    }
                    composable(Home.route) {
                        HomeScreen(navController = navController)
                    }
                    composable(Profile.route) {
                        ProfileScreen(navController = navController)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LittleLemonTheme {

    }
}