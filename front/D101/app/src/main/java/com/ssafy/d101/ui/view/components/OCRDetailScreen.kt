package com.ssafy.d101.ui.view.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ssafy.d101.R
import com.ssafy.d101.model.DietInfo
import com.ssafy.d101.model.OCRInfo

@Composable
fun OCRDetailScreen(dietInfo: OCRInfo) {
    var totalCarbsCalorie = 0.0
    var totalProteinCalorie = 0.0
    var totalFatCalorie = 0.0
    var totalCalorie = 0.0

    totalCarbsCalorie = (dietInfo.carbohydrate * 4)
    totalProteinCalorie = (dietInfo.protein * 4)
    totalFatCalorie = (dietInfo.fat * 9)
    totalCalorie = dietInfo.calorie.toDouble()

    // 각 성분별 칼로리 비율 계산
    val carbsPer = if (totalCalorie > 0) (totalCarbsCalorie / totalCalorie) * 100 else 0.0
    val protePer = if (totalCalorie > 0) (totalProteinCalorie / totalCalorie) * 100 else 0.0
    val fatPer = if (totalCalorie > 0) (totalFatCalorie / totalCalorie) * 100 else 0.0

    val formattedCarbsPer = String.format("%.1f", carbsPer)
    val formattedProtePer = String.format("%.1f", protePer)
    val formattedFatPer = String.format("%.1f", fatPer)

    val firstIntakeInfo = dietInfo.name.firstOrNull()

    val foodName = dietInfo.name ?: "음식 정보 없음"
    val foodCarbs = dietInfo.carbohydrate ?: 0.0
    val foodProtein = dietInfo.protein ?: 0.0
    val foodFat = dietInfo.fat ?: 0.0

    val carbcalories = String.format("%.1f", foodCarbs * 4)
    val proteinCalories = String.format("%.1f", foodProtein * 4)
    val fatCalories = String.format("%.1f", foodFat * 9)

    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .width(500.dp)
            .height(250.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xffEBE9D5),
        shadowElevation = 4.dp
    ) {
        Column {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 20.dp)
            ) {
                Text(
                    text = "${dietInfo.type}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "${foodName}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                // DailyHorizontalBar 그래프
                DailyHorizontalBar(
                    carbsPercentage = formattedCarbsPer.toFloat(),
                    proteinPercentage = formattedProtePer.toFloat(),
                    fatsPercentage = formattedFatPer.toFloat()
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .weight(8f)
                        .padding(16.dp)
                        .width(150.dp)
                        .height(110.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {


                    // 탄단지 정보표
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shadowElevation = 4.dp,
                        shape = RoundedCornerShape(12.dp)

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            // 탄수화물
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "탄수화물",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "${foodCarbs}g",
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                                Text(
                                    "${carbcalories}kcal",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 12.sp
                                )
                            }
                            Divider(
                                color = Color.Black, modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )

                            // 단백질
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "단백질",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "${foodProtein}g",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    "${proteinCalories}cal",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 12.sp
                                )
                            }
                            Divider(
                                color = Color.Black, modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )

                            // 지방
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "지방",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "${foodFat}g",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    "${fatCalories}kcal",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}