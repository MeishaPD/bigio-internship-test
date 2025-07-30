package brawijaya.putradewan.bigio.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import brawijaya.putradewan.bigio.ui.screens.detail.DetailScreen
import brawijaya.putradewan.bigio.ui.screens.home.HomeScreen

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Detail: Screen("detail/{characterId}") {
        fun createRoute(characterId: Int) = "detail/$characterId"
    }
    object Favorites: Screen("favorites")
    object Search: Screen("search")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("characterId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
            DetailScreen(
                navController = navController,
                characterId = characterId
            )
        }
    }
}