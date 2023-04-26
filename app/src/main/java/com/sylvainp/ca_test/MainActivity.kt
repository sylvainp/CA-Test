package com.sylvainp.ca_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sylvainp.ca_test.ui.AccountDetailScreen.AccountDetailScreen
import com.sylvainp.ca_test.ui.AccountDetailScreen.AccountDetailViewModel
import com.sylvainp.ca_test.ui.BankListScreen.BanksScreen
import com.sylvainp.ca_test.ui.BankListScreen.BanksViewModel
import com.sylvainp.ca_test.ui.theme.CA_TestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CA_TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationView()
                }
            }
        }

    }
}

@Composable
fun NavigationView() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "banks") {
        composable("banks") {
            val viewModel = hiltViewModel<BanksViewModel>()
            BanksScreen(navController, viewModel)
        }
        composable(
            "banks/{bankName}/{accountId}",
            arguments = listOf(
                navArgument("bankName") { type = NavType.StringType },
                navArgument("accountId") { type = NavType.StringType },

                )
        ) {
            val bankName = it.arguments?.getString("bankName")
            val accountId = it.arguments?.getString("accountId")
            val viewModel = hiltViewModel<AccountDetailViewModel>()
            AccountDetailScreen(navController, viewModel, bankName, accountId);
        }
    }
}