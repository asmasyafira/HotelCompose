package com.example.hotelcompose.nav

sealed class NavScreen(val route: String) {
    data object Main: NavScreen("main")
    data object Profile: NavScreen("profile")
    data object DetailHotel: NavScreen("main/{hotelId}") {
        fun createRoute(hotelId: Int) = "main/$hotelId"
    }
}