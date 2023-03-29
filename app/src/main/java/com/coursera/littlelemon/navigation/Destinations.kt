package com.coursera.littlelemon.navigation

interface Destinations {
    val route: String
}
object Home: Destinations {
    override val route: String
        get() = "Home"
}
object Profile: Destinations {
    override val route: String
        get() = "Profile"
}
object OnBoarding: Destinations {
    override val route: String
        get() = "OnBoarding"
}