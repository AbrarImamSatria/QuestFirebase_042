package com.example.pertemuan14.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pertemuan14.ui.home.pages.DetailScreen
import com.example.pertemuan14.ui.home.pages.HomeScreen
import com.example.pertemuan14.ui.home.pages.InsertMhsView

@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToltemEntry = {
                    navController.navigate(DestinasiInsert.route)
                },
                onDetailClick = { nim ->
                    navController.navigate("${DestinasiDetail.route.replace("{nim}", nim)}")
                }
            )
        }

        composable(DestinasiInsert.route) {
            InsertMhsView(
                onBack = { navController.popBackStack() },
                onNavigate = {
                    navController.navigate(DestinasiHome.route)
                }
            )
        }

        composable(
            route = DestinasiDetail.route,
            arguments = listOf(navArgument(DestinasiDetail.nimArg) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val nimArg = backStackEntry.arguments?.getString(DestinasiDetail.nimArg)
            DetailScreen(
                nim = nimArg ?: "",
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}
