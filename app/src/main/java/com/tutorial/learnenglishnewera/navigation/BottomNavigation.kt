package com.tutorial.learnenglishnewera.navigation

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Subscript
import androidx.compose.material.icons.filled.Surfing
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tutorial.learnenglishnewera.navigation.AllDestinations.HOME
import com.tutorial.learnenglishnewera.navigation.AllDestinations.SAVED
import com.tutorial.learnenglishnewera.navigation.AllDestinations.TEST
import com.tutorial.learnenglishnewera.screens.HomeScreen
import com.tutorial.learnenglishnewera.screens.SavedScreen
import com.tutorial.learnenglishnewera.screens.TestScreen

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
fun BottomNavigation(){

    val navController:NavHostController = rememberNavController()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    val navObject = remember(navController){ NavigateInMyApp(navController) }

    val itemList = listOf(
        BottomNavigationItem.Home(),
        BottomNavigationItem.Saved(),
        BottomNavigationItem.Test()
    )

    Scaffold(
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
                        label = { Text(text = item.label) }
                    )
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = HOME){
            composable(HOME){ HomeScreen() }
            composable(SAVED){ SavedScreen() }
            composable(TEST){ TestScreen() }
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