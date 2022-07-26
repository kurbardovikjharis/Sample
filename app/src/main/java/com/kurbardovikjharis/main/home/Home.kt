package com.kurbardovikjharis.main.home

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.kurbardovikjharis.community.R
import com.kurbardovikjharis.main.AppNavigation
import com.kurbardovikjharis.main.Screen

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home() {
    val navController = rememberAnimatedNavController()
    val currentSelectedItem by navController.currentScreenAsState()

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        bottomBar = {
            HomeBottomNavigation(
                selectedNavigation = currentSelectedItem,
                onNavigationSelected = { selected ->
                    navController.navigate(selected.route) {
                        launchSingleTop = true
                        restoreState = true

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                })
        }
    ) {
        AppNavigation(navController)
    }
}

@Composable
internal fun HomeBottomNavigation(
    selectedNavigation: Screen,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        contentColor = contentColorFor(MaterialTheme.colors.surface),
        modifier = modifier
    ) {
        HomeNavigationItems.forEach { item ->
            BottomNavigationItem(
                label = { item.name },
                selected = selectedNavigation == item.toScreen(),
                onClick = { onNavigationSelected(item.toScreen()) },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_android_white_24dp),
                        contentDescription = null
                    )
                }
            )
        }
    }
}

private val HomeNavigationItems = listOf(
    HomeNavigationItem.COMMUNITY,
    HomeNavigationItem.DISCOVER,
    HomeNavigationItem.INBOX,
    HomeNavigationItem.PROFILE
)

enum class HomeNavigationItem {
    COMMUNITY, DISCOVER, INBOX, PROFILE
}

internal fun HomeNavigationItem.toScreen(): Screen {
    return when (this) {
        HomeNavigationItem.COMMUNITY -> Screen.Community
        HomeNavigationItem.DISCOVER -> Screen.Discover
        HomeNavigationItem.INBOX -> Screen.Inbox
        HomeNavigationItem.PROFILE -> Screen.Profile
    }
}

/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Stable
@Composable
private fun NavController.currentScreenAsState(): State<Screen> {
    val selectedItem = remember { mutableStateOf<Screen>(Screen.Community) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == Screen.Community.route } -> {
                    selectedItem.value = Screen.Community
                }
                destination.hierarchy.any { it.route == Screen.Discover.route } -> {
                    selectedItem.value = Screen.Discover
                }
                destination.hierarchy.any { it.route == Screen.Inbox.route } -> {
                    selectedItem.value = Screen.Inbox
                }
                destination.hierarchy.any { it.route == Screen.Profile.route } -> {
                    selectedItem.value = Screen.Profile
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}