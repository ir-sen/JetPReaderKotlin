package kis.kan.jetreadearapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

        // how to add arguments to screen and get it
        val detailName = ReaderScreens.DetailsScreen.name
        composable("$detailName/{bookId}", arguments = listOf(navArgument("bookId") {
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(navController = navController, bookId = it.toString())
            }
        }

    }


}