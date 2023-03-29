package com.coursera.littlelemon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coursera.littlelemon.screens.HomeScreen
import com.coursera.littlelemon.screens.OnBoarding
import com.coursera.littlelemon.screens.ProfileScreen

@Composable
fun NavigationComposable(navController: NavHostController) {
    NavHost(navController = navController, startDestination = OnBoarding.route) {

    }
}