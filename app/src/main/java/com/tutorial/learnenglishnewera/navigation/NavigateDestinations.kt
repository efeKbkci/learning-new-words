package com.tutorial.learnenglishnewera.navigation

import androidx.navigation.NavHostController
import com.tutorial.learnenglishnewera.navigation.AllDestinations.HOME
import com.tutorial.learnenglishnewera.navigation.AllDestinations.SAVED
import com.tutorial.learnenglishnewera.navigation.AllDestinations.TEST

object AllDestinations {
    const val HOME: String = "home"
    const val SAVED: String = "saved"
    const val TEST:String = "test"
}

class NavigateInMyApp(private val navHostController: NavHostController){
    fun goHome(){
        navHostController.navigate(HOME)
    }

    fun goToSaved(){
        navHostController.navigate(SAVED)
    }

    fun goToTest(){
        navHostController.navigate(TEST)
    }
}