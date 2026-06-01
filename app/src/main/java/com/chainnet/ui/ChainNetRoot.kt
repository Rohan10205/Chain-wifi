package com.chainnet.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chainnet.navigation.Screen
import com.chainnet.ui.home.HomeScreen
import com.chainnet.ui.node.NodeDetailsScreen
import com.chainnet.ui.pairing.PairingScreen
import com.chainnet.ui.settings.SettingsScreen
import com.chainnet.ui.topology.TopologyScreen

@Composable
fun ChainNetRoot() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                Screen.values().forEach { screen ->
                    val selected = currentRoute == screen.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            when (screen) {
                                Screen.Home -> Icon(Icons.Default.Home, contentDescription = null)
                                Screen.Topology -> Icon(Icons.Default.Link, contentDescription = null)
                                Screen.Pairing -> Icon(Icons.Default.QrCode, contentDescription = null)
                                Screen.Settings -> Icon(Icons.Default.Settings, contentDescription = null)
                            }
                        },
                        label = { androidx.compose.material3.Text(screen.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
        ) {
            composable(Screen.Home.route) { HomeScreen(padding) }
            composable(Screen.Topology.route) { TopologyScreen(padding) }
            composable(Screen.Pairing.route) { PairingScreen(padding) }
            composable(Screen.Settings.route) { SettingsScreen(padding) }
            composable(\"node/{nodeId}\") { entry ->
                val nodeId = entry.arguments?.getString(\"nodeId\").orEmpty()
                NodeDetailsScreen(padding, nodeId)
            }
        }
    }
}
