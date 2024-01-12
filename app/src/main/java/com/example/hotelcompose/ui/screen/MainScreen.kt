package com.example.hotelcompose.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.example.hotelcompose.data.HotelData
import com.example.hotelcompose.ui.item.HotelItem

@Composable
fun MainScreen(navigateToDetail: (Int) -> Unit) {
    val hotels = remember{HotelData.hotelDummy}
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(
            items = hotels,
            itemContent = { hotel ->
                HotelItem(
                    hotel = hotel,
                    onItemClick = { hotelId ->
                        navigateToDetail(hotelId)
                    }
                )
            }
        )
    }
}


