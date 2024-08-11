package com.tutorial.learnenglishnewera.navigation

import androidx.navigation.NavHostController
import com.tutorial.learnenglishnewera.MyViewModel
import com.tutorial.learnenglishnewera.navigation.AllDestinations.HOME
import com.tutorial.learnenglishnewera.navigation.AllDestinations.SAVED
import com.tutorial.learnenglishnewera.navigation.AllDestinations.TEST
import com.tutorial.learnenglishnewera.navigation.AllDestinations.WORD

object AllDestinations {
    const val HOME: String = "home"
    const val SAVED: String = "saved"
    const val TEST:String = "test"
    const val WORD:String = "word"
}

class NavigateInMyApp(private val navHostController: NavHostController, private val viewModel: MyViewModel){
    fun goHome(){
        navHostController.navigate(HOME)
    }

    fun goToSaved(){
        navHostController.navigate(SAVED)
    }

    fun goToTest(){
        navHostController.navigate(TEST)
    }

    fun goToWord(){
        navHostController.navigate(WORD)
        viewModel.previousRoute = navHostController.previousBackStackEntry?.destination?.route!!
    }
}