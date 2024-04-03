package org.d3if0122.assesment1mobpro.navigation

sealed class Screen (val route: String){
    data object Home: Screen("mainScreen")
    data object About: Screen("aboutScreen")
}