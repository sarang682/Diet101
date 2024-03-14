package com.ssafy.d101.ui.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.theme.White

@Composable
fun MyPageScreen() {

    Column( modifier = Modifier // 백그라운드
        .fillMaxSize()
        .background(Ivory)
    ) {
        MyPageHeader()
        MyProfile()
    }

}
@Composable
fun MyPageHeader() {
    Row ( modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) { // 헤더 박스
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center // Box 내부의 내용을 시작 지점에 맞춤
        ) {
            Text(
                text = "마이페이지",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
            )
        }
        Box(
            contentAlignment = Alignment.CenterEnd // Box 내부의 내용을 끝 지점에 맞춤
        ) {
            IconButton(onClick = { /* 할 일 */ }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User Page"
                )
            }
        }

    }
}

@Composable
fun MyProfile() {
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp, vertical = 5.dp)
        .background(White, shape = RoundedCornerShape(12.dp))
    ) { // 프로필 칸 @!
        Column () {
            Row ( modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {// 첫번째 칸
                Box (modifier = Modifier
                    .weight(1f)
                    .padding(20.dp, 0.dp, 0.dp, 0.dp),
                    contentAlignment = Alignment.CenterStart
                ){ // 닉네임 @!
                    Text(
                        text = "보근"+"님,",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Box (
                    modifier = Modifier.padding(0.dp,0.dp,20.dp,0.dp),
                    contentAlignment = Alignment.CenterEnd
                ){ // 프로필 이미지 @!
                    AsyncImage(
                        model = "https://d101-bucket.s3.ap-northeast-2.amazonaws.com/diet/%EB%96%A1%EB%B3%B6%EC%9D%B4.jpg",
                        contentDescription = "profileImage",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
            Row ( modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically) { // 주간 기록 현황
                Column {
                    Text(text = "주간 기록 현황")
                }
                Column {
                    Slider(
                        value = 0.5f,
                        onValueChange = { /* 슬라이더 값 변화에 대한 핸들러 */ },
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFFFFA500),
                            activeTrackColor = Color(0xFFFFA500)
                        )
                    )
                }
            }
            Row ( modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically) { // 주간 기록 현황
                Column {
                    Text(text = "월간 기록 현황")
                }
                Column {
                    Slider(
                        value = 0.3f,
                        onValueChange = { /* 슬라이더 값 변화에 대한 핸들러 */ },
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF64AEF2),
                            activeTrackColor = Color(0xFF64AEF2)
                        )
                    )
                }
            }
        }

    }
    
}

@Composable
fun MyMenu() {
    // 설정 목록을 나열하는 부분
    Column {
        SettingItem("BMI 계산하기")
        SettingItem("알림설정")
        Divider(color = Color.Gray, thickness = 1.dp)
        Text("시타 관리", modifier = Modifier.padding(8.dp))
        SettingItem("옷 사이즈")
        SettingItem("해외 사이즈 비교")
        SettingItem("사이즈 측정")
    }
}

@Composable
fun SettingItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) { // > 아이콘
        Text(text)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.ArrowForward, contentDescription = null)
    }
}
