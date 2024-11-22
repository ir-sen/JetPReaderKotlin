package kis.kan.jetreadearapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kis.kan.jetreadearapp.screens.ReaderSplashScreen
import kis.kan.jetreadearapp.screens.details.BookDetailsScreen
import kis.kan.jetreadearapp.screens.home.ReaderHomeScreen
import kis.kan.jetreadearapp.screens.login.ReaderLoginScreen
import kis.kan.jetreadearapp.screens.search.BookSearchViewModel
import kis.kan.jetreadearapp.screens.search.ReaderBookSearchScreen
import kis.kan.jetreadearapp.screens.search.SearchViewModelVersion2
import kis.kan.jetreadearapp.screens.starts.ReaderStartsScreen


@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ReaderScreens.SplashScreen.name
    ) {

        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name) {
            ReaderHomeScreen(navController = navController)
        }

        composable(ReaderScreens.BookDetailsScreen.name) {
            BookDetailsScreen(navController = navController)
        }

        composable(ReaderScreens.SearchScreen.name) {
            val searchViewModel = hiltViewModel<SearchViewModelVersion2>()

            ReaderBookSearchScreen(navController = navController, viewModel = searchViewModel)
        }

        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }

        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderStatsScreen.name) {
            ReaderStartsScreen(navController = navController)
        }

    }


}