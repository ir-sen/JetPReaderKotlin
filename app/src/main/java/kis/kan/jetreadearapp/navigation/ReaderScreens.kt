package kis.kan.jetreadearapp.navigation

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    SearchScreen,
    DetailsScreen,
    UpdateScreen,
    BookDetailsScreen,
    StartsScreen,
    ReaderStatsScreen;

    companion object {
        fun fromRoute(route: String?): ReaderScreens =
            when(route?.substringBefore("/")) {
                SplashScreen.name -> SplashScreen
                LoginScreen.name -> LoginScreen
                CreateAccountScreen.name -> CreateAccountScreen
                ReaderHomeScreen.name -> ReaderHomeScreen
                SearchScreen.name -> SearchScreen
                DetailsScreen.name -> DetailsScreen
                UpdateScreen.name -> UpdateScreen
                BookDetailsScreen.name -> BookDetailsScreen
                ReaderStatsScreen.name -> ReaderStatsScreen
                StartsScreen.name -> StartsScreen
                null -> ReaderHomeScreen
                else -> throw IllegalArgumentException("Route $route is not recognized")
            }
    }

}