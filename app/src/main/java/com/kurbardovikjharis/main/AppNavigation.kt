/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kurbardovikjharis.main

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.kurbardovikjharis.community.Community
import com.kurbardovikjharis.discovery.Discovery
import com.kurbardovikjharis.inbox.Inbox
import com.kurbardovikjharis.profile.Profile

internal sealed class Screen(val route: String) {
    object Community : Screen("community")
    object Discover : Screen("discover")
    object Inbox : Screen("inbox")
    object Profile : Screen("profile")
}

internal sealed class LeafScreen(
    private val route: String
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Community : LeafScreen("community")
    object Discover : LeafScreen("discover")
    object Inbox : LeafScreen("inbox")
    object Profile : LeafScreen("profile")
}

@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Community.route,
        enterTransition = { defaultEnterTransition(initialState, targetState) },
        exitTransition = { defaultExitTransition(initialState, targetState) },
        popEnterTransition = { defaultPopEnterTransition() },
        popExitTransition = { defaultPopExitTransition() },
        modifier = modifier,
    ) {
        addCommunityTopLevel()
        addDiscoverTopLevel()
        addInboxTopLevel()
        addProfileTopLevel()
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCommunityTopLevel() {
    navigation(
        route = Screen.Community.route,
        startDestination = LeafScreen.Community.createRoute(Screen.Community),
    ) {
        addCommunity(Screen.Community)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addDiscoverTopLevel() {
    navigation(
        route = Screen.Discover.route,
        startDestination = LeafScreen.Discover.createRoute(Screen.Discover),
    ) {
        addDiscover(Screen.Discover)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addInboxTopLevel() {
    navigation(
        route = Screen.Inbox.route,
        startDestination = LeafScreen.Inbox.createRoute(Screen.Inbox),
    ) {
        addInbox(Screen.Inbox)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addProfileTopLevel() {
    navigation(
        route = Screen.Profile.route,
        startDestination = LeafScreen.Profile.createRoute(Screen.Profile),
    ) {
        addProfile(Screen.Profile)
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addCommunity(
    root: Screen,
) {
    composable(
        route = LeafScreen.Community.createRoute(root)
    ) {
        Community()
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addDiscover(
    root: Screen,
) {
    composable(
        route = LeafScreen.Discover.createRoute(root)
    ) {
        Discovery()
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addInbox(
    root: Screen,
) {
    composable(
        route = LeafScreen.Inbox.createRoute(root)
    ) {
        Inbox()
    }
}

@ExperimentalAnimationApi
private fun NavGraphBuilder.addProfile(
    root: Screen,
) {
    composable(
        route = LeafScreen.Profile.createRoute(root)
    ) {
        Profile()
    }
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): EnterTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeIn()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Start)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): ExitTransition {
    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    // If we're crossing nav graphs (bottom navigation graphs), we crossfade
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeOut()
    }
    // Otherwise we're in the same nav graph, we can imply a direction
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Start)
}

private val NavDestination.hostNavGraph: NavGraph
    get() = hierarchy.first { it is NavGraph } as NavGraph

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultPopEnterTransition(): EnterTransition {
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.End)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultPopExitTransition(): ExitTransition {
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.End)
}
