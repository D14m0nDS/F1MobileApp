package com.antoan.f1app.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.antoan.f1app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(
    onBack: () -> Unit,
    screenTitle: String
) {
    TopAppBar(
        title = { Text(screenTitle, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        windowInsets = WindowInsets(
            top = dimensionResource(id = R.dimen.size_0dp),
            bottom = dimensionResource(id = R.dimen.size_0dp)
        ),
        modifier = Modifier
            .fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)

    )
}
