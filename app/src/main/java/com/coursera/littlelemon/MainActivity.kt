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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.coursera.littlelemon.data.AppDatabase
import com.coursera.littlelemon.navigation.Home
import com.coursera.littlelemon.navigation.OnBoarding
import com.coursera.littlelemon.navigation.Profile
import com.coursera.littlelemon.network.MenuItemNetwork
import com.coursera.littlelemon.network.MenuNetwork
import com.coursera.littlelemon.screens.HomeScreen
import com.coursera.littlelemon.screens.OnBoarding
import com.coursera.littlelemon.screens.ProfileScreen
import com.coursera.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java,"menu_database").build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstNameLiveData.value = sharedPreferences.getString("first_name", "")
        lastNameLiveData.value = sharedPreferences.getString("last_name", "")
        emailLiveData.value = sharedPreferences.getString("email", "")
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener)
        setContent {
            LittleLemonTheme {
                val databaseItems by database.menuItemDao().getAll().observeAsState(emptyList())
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = if (sharedPreferences.all.isEmpty()) OnBoarding.route else Home.route) {
                    composable(OnBoarding.route) {
                        OnBoarding(navController = navController)
                    }
                    composable(Home.route) {
                        HomeScreen(navController = navController, databaseItems)
                    }
                    composable(Profile.route) {
                        ProfileScreen(navController = navController)
                    }
                }
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty())
                saveMenuToDatabase(fetchMenu())
        }
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val response = httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuNetwork>()
        return response.menu
    }

    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        val menuItemsRoom = menuItemsNetwork.map { it.toRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LittleLemonTheme {

    }
}