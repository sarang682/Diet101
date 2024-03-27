package com.ssafy.d101.navigation

sealed class Screens(val route: String) {
    data object Landing : Screens("landing")
    data object Home : Screens("home")
    data object MyPage : Screens("myPage")
    data object BMI : Screens("bmi")
    data object Allergy : Screens("allergy")
    data object Start : Screens("start")
    data object SignUp : Screens("signUp")
    data object SignUpComplete : Screens("signUpComplete")
    data object LoginSuccess : Screens("loginSuccess")
    data object UserInfo : Screens("userinfo")
}