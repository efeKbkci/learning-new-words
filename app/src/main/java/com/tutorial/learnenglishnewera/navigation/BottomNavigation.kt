package com.tutorial.learnenglishnewera.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.navigation.AllDestinations.HOME
import com.tutorial.learnenglishnewera.navigation.AllDestinations.SAVED
import com.tutorial.learnenglishnewera.navigation.AllDestinations.TEST
import com.tutorial.learnenglishnewera.navigation.AllDestinations.WORD
import com.tutorial.learnenglishnewera.reuseables.UpdateMessenger
import com.tutorial.learnenglishnewera.screens.HomeScreen
import com.tutorial.learnenglishnewera.screens.SavedScreen
import com.tutorial.learnenglishnewera.screens.TestScreen
import com.tutorial.learnenglishnewera.screens.WordScreen

sealed class BottomNavigationItem (
    val label:String,
    val selectedIcon:ImageVector,
    val unselectedIcon:ImageVector,
){
    class Home : BottomNavigationItem("Home",Icons.Filled.Home, Icons.Outlined.Home)
    class Saved : BottomNavigationItem("Saved",Icons.Filled.Save, Icons.Outlined.Save)
    class Test : BottomNavigationItem("Test",Icons.Filled.Assessment, Icons.Outlined.Assessment)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigation(viewModel: MyViewModel){

    val navController:NavHostController = rememberNavController()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val navObject = remember(navController){ NavigateInMyApp(navController, viewModel) }
    var enableNavigation by rememberSaveable { viewModel.enableNavigation }

    val snackbarHostState by viewModel.snackbarState.collectAsState()

    val itemList = listOf(
        BottomNavigationItem.Home(),
        BottomNavigationItem.Saved(),
        BottomNavigationItem.Test()
    )

    Scaffold(

        modifier = Modifier.navigationBarsPadding() ,

        snackbarHost = { SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(bottom = 34.dp)
        ) },

        bottomBar = {
            NavigationBar {
                itemList.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            handleOnClick(item, navObject)
                        },
                        icon = {
                            Icon(
                                imageVector = if (selectedItemIndex == index){
                                    item.selectedIcon
                                } else {
                                   item.unselectedIcon
                                },
                                contentDescription = item.label
                            )
                        },
                        label = { Text(text = item.label) },
                        enabled = enableNavigation
                    )
                }
            }
        }
    ) {
        
        UpdateMessenger(viewModel = viewModel)
        
        NavHost(navController = navController, startDestination = HOME){
            composable(HOME){ HomeScreen(viewModel = viewModel){navObject.goToWord()} }
            composable(SAVED){ SavedScreen(viewModel = viewModel){navObject.goToWord() } }
            composable(TEST){ TestScreen(viewModel = viewModel) }
            composable(WORD){ WordScreen(viewModel = viewModel){navObject.goToSaved()} }
        }
    }
}

fun handleOnClick(item:BottomNavigationItem, navObject:NavigateInMyApp){
    when(item){
        is BottomNavigationItem.Home -> { navObject.goHome() }
        is BottomNavigationItem.Saved -> { navObject.goToSaved() }
        is BottomNavigationItem.Test -> { navObject.goToTest() }
    }
}