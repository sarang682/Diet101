package com.ssafy.d101.ui.view.components


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ssafy.d101.model.FoodInfo


@Composable
fun FoodList(foodInfos: List<FoodInfo>) {
    LazyColumn {
        items(foodInfos) { foodInfo ->
            FoodItemCard(foodInfo = foodInfo)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewFoodList() {
    FoodList(foodInfos = listOf(
//        FoodInfo("등갈비 김치찜", 200, 25.5f, 32.0f,21.9f,427.1f),
//        FoodInfo("공깃밥",1,31.7f,2.7f,0.3f,140.3f),
//        FoodInfo("모코코",10,31.7f,2.7f,0.3f,140.3f)
        ))
}