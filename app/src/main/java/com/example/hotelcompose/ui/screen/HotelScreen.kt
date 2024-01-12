package com.example.hotelcompose.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hotelcompose.R
import com.example.hotelcompose.nav.NavItem
import com.example.hotelcompose.nav.NavScreen

@Preview
@Composable
fun HotelScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold (
        bottomBar = {
            if (currentRoute != NavScreen.DetailHotel.route){
                BottomNav(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavScreen.Main.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavScreen.Main.route) {
                MainScreen(
                    navigateToDetail = {hotelId ->
                        navController.navigate(NavScreen.DetailHotel.createRoute(hotelId))
                    }
                )
            }
            composable(NavScreen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = NavScreen.DetailHotel.route,
                arguments = listOf(
                    navArgument("hotelId"){type = NavType.IntType})
            ) {
                val id = it.arguments?.getInt("hotelId") ?: 0
                DetailScreen(hotelId = id)
            }
        }
    }
}

@Composable
private fun BottomNav(
    navController: NavHostController,
    modifier: Modifier= Modifier) {
    BottomNavigation(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavItem(
                title = stringResource(R.string.main),
                icon = Icons.Default.Home,
                navScreen = NavScreen.Main
            ),
            NavItem(
                title = stringResource(R.string.about),
                icon = Icons.Default.AccountCircle,
                navScreen = NavScreen.Profile
            )
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.navScreen.route,
                    onClick = {
                        navController.navigate(item.navScreen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
