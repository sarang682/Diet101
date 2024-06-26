package com.ssafy.d101.ui.view.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.navigation.Screens
import com.ssafy.d101.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpActivityLevelScreen(navController: NavHostController) {
    var activityLevel by remember { mutableIntStateOf(-1) }
    var showMenu by remember { mutableStateOf(false) }

    val userViewModel: UserViewModel = hiltViewModel()

    var startUserSubInfoUpdate by remember { mutableStateOf(false) }
    val saveState: Boolean by userViewModel.saveSuccess.collectAsState()

    val activityLevelList = listOf(
        "운동을 하지 않음",
        "가벼운 활동 및 운동(1 ~ 3일 / 주)",
        "보통의 활동 및 운동(3 ~ 5일 / 주)",
        "적극적인 활동 및 운동(6 ~ 7일 / 주)",
        "매우 적극적인 활동 및 운동"
    )
    val selectedText = if (activityLevel >= 0) activityLevelList[activityLevel] else "운동량 선택"

    LaunchedEffect(startUserSubInfoUpdate) {
        if (startUserSubInfoUpdate) {
            Log.i("SignUpActivityLevelScreen", "startUserSubInfoUpdate: $startUserSubInfoUpdate")
            userViewModel.setUserSubInfo()
        }
    }

    LaunchedEffect(saveState) {
        if (saveState) {
            navController.navigate(Screens.Home.route)
        }
    }
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
            ExposedDropdownMenuBox(expanded = showMenu, onExpandedChange = { showMenu = !showMenu}) {
                TextField(
                    readOnly = true,
                    value = selectedText,
                    onValueChange = {
//                        newValue ->
//                        activityLevel = newValue
                    },
                    label = { Text("운동량") },
                    singleLine = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = showMenu)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = {
                        showMenu = false
                    }
                ) {
                    activityLevelList.forEachIndexed { index, level ->
                        DropdownMenuItem(text = { Text(level) },
                            onClick = {
                                activityLevel = index
                                showMenu = false
                            })
                    }
                }
            }
        }

        Button(
            onClick = {
                userViewModel.setActivityLevel(activityLevel)
                startUserSubInfoUpdate = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "시작하기")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpActivityLevelScreen() {
    SignUpActivityLevelScreen(navController = rememberNavController())
}