package com.sandeepk.watchify.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.sandeepk.watchify.ui.screen.favourites.FavoritesScreen
import com.sandeepk.watchify.ui.screen.home.HomeScreen
import com.sandeepk.watchify.ui.screen.home.HomeViewModel
import com.sandeepk.watchify.ui.screen.settings.SettingsScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    var selectedScreen by remember { mutableStateOf("home") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            when (selectedScreen) {
                "home" -> TopAppBar(
                    title = { Text("Watchify") },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )

                "favorites" -> TopAppBar(
                    title = { Text("Favourites") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )

                "settings" -> TopAppBar(
                    title = { Text("Settings") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        },
        bottomBar = {
            NavigationBar {
                Row(modifier = Modifier.fillMaxWidth()) {
                    BottomNavItem(
                        "home",
                        "home",
                        Icons.Default.List,
                        selectedScreen == "home",
                        modifier = Modifier.weight(1f)
                    ) {
                        selectedScreen = "home"
                    }

                    BottomNavItem(
                        "favorites",
                        "Favourites",
                        Icons.Default.Star,
                        selectedScreen == "favorites",
                        modifier = Modifier.weight(1f)
                    ) {
                        selectedScreen = "favorites"
                    }

                    BottomNavItem(
                        "settings",
                        "Settings",
                        Icons.Default.Person,
                        selectedScreen == "settings",
                        modifier = Modifier.weight(1f)
                    ) {
                        selectedScreen = "settings"
                    }
                }
            }

        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            when (selectedScreen) {
                "home" -> HomeScreen()
                "favorites" -> FavoritesScreen()
                "settings" -> SettingsScreen()
            }
        }
    }
}

@Composable
fun BottomNavItem(
    route: String,
    label: String,
    icon: ImageVector,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) },
        modifier = modifier
    )
}

