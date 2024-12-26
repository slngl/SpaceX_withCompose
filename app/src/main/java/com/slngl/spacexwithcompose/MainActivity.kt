package com.slngl.spacexwithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.slngl.spacexwithcompose.ui.theme.SpaceXWithComposeTheme
import com.slngl.spacexwithcompose.view.HistoryDetailScreen
import com.slngl.spacexwithcompose.view.HistoryListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpaceXWithComposeTheme {
/*                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "history_list_screen") {
                    //HistoryListScreen
                    composable("history_list_screen") {
                        HistoryListScreen(navController)
                    }
                    //HistoryDetailScreen
                    composable(
                        "history_detail_screen/{id}",
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.StringType
                            },
                        ),
                    ) {

                        val historyId = remember {
                            it.arguments?.getString("id")
                        }
                        HistoryDetailScreen(
                            id = historyId ?: "",
                        )

                    }
                }
            }
        }
    }
}

