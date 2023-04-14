package com.example.sportapp.feature.add_session.ui.model.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import java.lang.reflect.Modifier

@Composable
fun LoadingScreen() {

        CircularProgressIndicator(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        )

}