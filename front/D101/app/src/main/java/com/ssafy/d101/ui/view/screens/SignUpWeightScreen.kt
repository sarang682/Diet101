package com.ssafy.d101.ui.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.navigation.Screens
import com.ssafy.d101.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpWeightScreen(navController: NavHostController) {

    val userViewModel: UserViewModel = hiltViewModel()

    val weight = remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                }
            )
            Text(
                text = "키, 몸무게 저희만의 비밀이에요",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.padding(8.dp))
            Text(
                text = "대사량을 계산하기 위해서 필요하며,\n" +
                        "다른 사람에게 공개되지 않아요"
            )

            Spacer(Modifier.padding(16.dp))
            TextField(
                value = if (weight.intValue != 0) weight.intValue.toString() else "",
                onValueChange = { newValue ->
                    // 입력 값이 변경될 때마다 height 상태를 업데이트합니다.
                    weight.intValue = newValue.toInt()
                },
                label = { Text("몸무게") },
                suffix = { Text("kg") },

                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Button(
            onClick = {
                userViewModel.setWeight(weight.intValue)
                navController.navigate(Screens.ActivityLevel.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "다음")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSignUpWeightScreen() {
    val navController = rememberNavController()
    SignUpWeightScreen(navController = navController)
}